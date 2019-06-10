package ru.ifmo.rain.kamenev.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

/**
 * Implementation of {@link JarImpler} interface
 *
 * @author Yury Kamenev
 * @version 1.0
 */
public class Implementor implements JarImpler {

    /** Class to implement. */
    private Class<?> clazz;

    /** Name of implemented class. */
    private String name;

    /** Stores implementation */
    private StringBuilder implementation;

    /** {@link SimpleFileVisitor} to clean the directories. */
    private static final SimpleFileVisitor<Path> DELETE_VISITOR = new SimpleFileVisitor<>() {
        /**
         * Deletes file represented by file
         *
         * @param file current file in fileTree
         * @param attrs attributes of file
         * @return {@link FileVisitResult#CONTINUE}
         * @throws IOException if error occurred during deleting of file
         */
        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }

        /**
         * Deletes directory represented by dir
         *
         * @param dir current visited directory in fileTree
         * @param exc null if the iteration of the directory completes without an error;
         *           otherwise the I/O exception that caused the iteration of the directory to complete prematurely
         * @return {@link FileVisitResult#CONTINUE}
         * @throws IOException if error occurred during deleting of directory
         */
        @Override
        public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }
    };

    /**
     * @throws ImplerException if the given class cannot be generated for one of such reasons:
     *  <ul>
     *  <li> Some arguments are null</li>
     *  <li> Given class is primitive or array. </li>
     *  <li> Given class is final class or {@link Enum}. </li>
     *  <li> class isn't an interface and contains only private constructors. </li>
     *  <li> The problems with I/O occurred during implementation. </li>
     *  </ul>
     */
    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        implementation = new StringBuilder();
        if (token.isPrimitive() || token.isArray() || Modifier.isFinal(token.getModifiers()) || token == Enum.class) {
            throw new ImplerException("Can't implement this class");
        }
        clazz = token;
        name = token.getSimpleName() + "Impl";
        Path path = getFilePath(root, token);
        Path parent = path.getParent();
        try {
            Files.createDirectories(parent);
        } catch (IOException e) {
            throw new ImplerException("Can't create a directory");
        } catch (NullPointerException e) {
            throw new ImplerException("Invalid path");
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            generateHead();
            if (!clazz.isInterface()) {
                generateConstructors();
            }
            generateMethods();
            writer.write(toUnicode(implementation.append("}").toString()));
        } catch (IOException e) {
            throw new ImplerException("Can't write to file");
        }
    }

    /**
     * Return Unicode representation of the input string.
     *
     * @param input input string
     * @return Unicode representation of the input string
     */
    private String toUnicode(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c >= 128) {
                sb.append(String.format("\\u%04X", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @Override
    /**
     * Creates .jar with implementing class. Implements class using {@link #implement(Class, Path)} with temp
     * directory, compiles it, writes .jar file with it to given directory and {@link #clean(Path)} temp directory.
     *
     * @throws ImplerException if the given class cannot be generated for one of such reasons:
     *  <ul>
     *  <li> Some arguments are null</li>
     *  <li> Error occurs during implementation via {@link #implement(Class, Path)} </li>
     *  <li> {@link JavaCompiler} failed to compile implemented class </li>
     *  <li> The problems with I/O occurred during implementation. </li>
     *  </ul>
     */
    public void implementJar(Class<?> token, Path outputDir) throws ImplerException {
        Path tempDir;
        try {
            tempDir = Files.createTempDirectory(outputDir.toAbsolutePath().getParent(), "temp");
            int begin = tempDir.toString().lastIndexOf(File.separatorChar) + 1;
            tempDir = Paths.get(tempDir.toString().substring(begin));
        } catch (IOException e) {
            throw new ImplerException("Can't create temp dir");
        }
        try {
            implement(token, tempDir);
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            String[] args = new String[]{
                    "-cp",
                    tempDir.toString() + File.pathSeparator + getClassPath(),
                    getFilePath(tempDir, token).toString()
            };
            if (compiler == null || compiler.run(null, null, null, args) != 0) {
                throw new ImplerException("Unable to compile generated files");
            }

            Manifest manifest = new Manifest();
            Attributes attributes = manifest.getMainAttributes();
            attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
            attributes.put(Attributes.Name.IMPLEMENTATION_VENDOR, "Yury Kamenev");
            try (JarOutputStream writer = new JarOutputStream(Files.newOutputStream(outputDir), manifest)) {
                writer.putNextEntry(new ZipEntry(token.getName().replace('.', '/') + "Impl.class"));
                Path src = Paths.get(getFilePath(tempDir, token).toString().replace(".java", ".class"));
                Files.copy(src, writer);
            } catch (IOException e) {
                throw new ImplerException("Can't write to JAR file");
            }
        } finally {
            try {
                clean(tempDir);
            } catch (IOException e) {
                System.out.println("Cant remove temp dir");
            }
        }
    }

    /**
     * Returns full path to token resolving given path.
     *
     * @param path path to resolve
     * @param token class to take name
     *
     * @return full path to token resolving path
     */
    private static Path getFilePath(Path path, Class<?> token) {
        return path.resolve(token.getPackageName().replace('.', File.separatorChar))
                .resolve(token.getSimpleName() + "Impl" + ".java");
    }

    /** Returns path to .jar that contains {@link JarImpler} class.
     *
     * @return {@link String} representation of path to .jar archive.
     */
    private static String getClassPath() {
        return JarImpler.class.getProtectionDomain().getCodeSource().getLocation().toString();
    }

    /**
     * Returns the package header for {@link #clazz}.
     *
     * @return {@link String} representation of the package header.
     */
    private String getPackage() {
        StringBuilder result = new StringBuilder();
        if (!clazz.getPackage().getName().equals("")) {
            result.append("package ")
                    .append(clazz.getPackageName())
                    .append(";")
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
        }
        return result.toString();
    }

    /**
     * Generate import of {@link #clazz}.
     *
     * @return import header.
     */
    private String generateImport() {
        StringBuilder result = new StringBuilder();
        if (!clazz.getPackage().getName().equals("")) {
            result.append("import ").append(clazz.getPackageName())
                    .append(".")
                    .append(clazz.getSimpleName())
                    .append(";")
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
        }
        return result.toString();
    }

    /**
     * Appends beginning declaration of the class, containing its package, import, name, base class or
     * implemented interface to {@link #implementation}.
     */
    private void generateHead() {
        implementation.append(getPackage())
                .append(generateImport())
                .append("public class ")
                .append(clazz.getSimpleName())
                .append("Impl")
                .append(" ")
                .append(clazz.isInterface() ? "implements" : "extends")
                .append(" ")
                .append(clazz.getSimpleName())
                .append(" ")
                .append("{")
                .append(System.lineSeparator())
                .append(System.lineSeparator());
    }

    /**
     * Appends implementation of constructors of {@link #clazz} to {@link #implementation}.
     *
     * @throws ImplerException if class doesn't have any non-private constructors.
     */
    private void generateConstructors() throws ImplerException {
        List<Constructor<?>> declaredConstructors = Arrays.stream(clazz.getDeclaredConstructors())
                .filter(constructor -> !Modifier.isPrivate(constructor.getModifiers())).collect(Collectors.toList());
        if (declaredConstructors.isEmpty()) {
            throw new ImplerException("Can't extend class with only private constructors");
        }
        for (Constructor constructor : declaredConstructors) {
            implementation.append(write(constructor));
        }
    }

    /**
     * Filters methods, leaving only declared as abstract which names never were in names
     * and puts them to set.
     * @param methods array of {@link Method}.
     * @param set {@link HashSet} to store methods
     * @param names {@link HashSet} to collect methods names
     */
    private void getAbstractMethods(Method[] methods, HashSet<Method> set, HashSet<String> names) {
        HashSet<Method> filteredMethods = Arrays.stream(methods)
                .filter(method -> Modifier.isAbstract(method.getModifiers()))
                .filter(method -> !set.contains(method))
                .filter(method -> !names.contains(method.getName())).collect(Collectors.toCollection(HashSet::new));
        set.addAll(filteredMethods);
        filteredMethods.stream().map(Method::getName).collect(Collectors.toCollection(() -> names));
    }

    /**
     * Appends implementation of abstract methods of {@link #clazz} to {@link #implementation}.
     */
    private void generateMethods() {
        HashSet<Method> methods = new HashSet<>();
        HashSet<String> names = new HashSet<>();
        getAbstractMethods(clazz.getMethods(), methods, names);
        while (clazz != null) {
            getAbstractMethods(clazz.getDeclaredMethods(), methods, names);
            clazz = clazz.getSuperclass();
        }
        for (Method method : methods) {
            implementation.append(write(method));
        }
    }

    /**
     * Returns fully constructed {@link Executable}, that calls constructor of super class if
     * exec is instance of {@link Constructor}, otherwise returns default value of return type
     * of such {@link Method}.
     * @param exec given {@link Constructor} or {@link Method}.
     * @return {@link String} representing code of such {@link Executable}.
     */
    private String write(Executable exec) {
        StringBuilder result = new StringBuilder("\t");
        int mods = exec.getModifiers() & ~Modifier.ABSTRACT & ~Modifier.TRANSIENT;
        result.append(Modifier.toString(mods))
                .append(" ")
                .append(getTypeAndName(exec))
                .append(getParams(exec))
                .append(getExceptions(exec))
                .append(" {")
                .append(System.lineSeparator())
                .append("\t\t")
                .append(getBody(exec))
                .append(";")
                .append(System.lineSeparator())
                .append("\t}")
                .append(System.lineSeparator())
                .append(System.lineSeparator());
        return result.toString();
    }

    /**
     * If given {@link Executable} is instance of {@link Constructor} returns name of generated class,
     * otherwise returns return type and name of such {@link Method}
     *
     * @param exec given {@link Constructor} or {@link Method}
     * @return {@link String} representing such return type and name
     */
    private String getTypeAndName(Executable exec) {
        if (exec instanceof Method) {
            return ((Method) exec).getReturnType().getCanonicalName() + " " + exec.getName();
        } else {
            return name;
        }
    }

    /**
     * Returns list of parameters of given {@link Executable}.
     *
     * @param exec {@link Executable}.
     * @return {@link String} representing list of parameters.
     */
    private String getParams(Executable exec) {
        return Arrays.stream(exec.getParameters())
                .map(parameter -> parameter.getType().getCanonicalName() + " " + parameter.getName())
                .collect(Collectors.joining("," + " ", "(", ")"));
    }

    /**
     * Returns list of exceptions, that given {@link Executable} may throw.
     *
     * @param exec {@link Executable} to get exceptions from.
     * @return {@link String} representing list of exceptions.
     */
    private String getExceptions(Executable exec) {
        return (exec.getExceptionTypes().length > 0 ? " throws " : "") +
                Arrays.stream(exec.getExceptionTypes()).map(Class::getCanonicalName)
                        .collect(Collectors.joining("," + " "));
    }

    /**
     * Calls constructor of super class if given {@link Executable} if instance of {@link Constructor},
     * otherwise return default value of return type of such {@link Method}
     * @param exec given {@link Constructor} or {@link Method}
     * @return {@link String} representing body, defined above
     */
    private String getBody(Executable exec) {
        if (exec instanceof Method) {
            return "return" + getDefaultReturn(exec);
        } else {
            return "super" + Arrays.stream(exec.getParameters()).map(Parameter::getName)
                    .collect(Collectors.joining("," + " ", "(", ")"));
        }
    }

    /**
     * Returns default return value of given {@link Executable}.
     * @param exec given {@link Executable}
     * @return default return value
     */
    private String getDefaultReturn(Executable exec) {
        Class<?> returnType = ((Method) exec).getReturnType();
        if (returnType.equals(boolean.class)) {
            return " false";
        } else if (returnType.equals(void.class)) {
            return "";
        } else if (returnType.isPrimitive()) {
            return " 0";
        } else {
            return " null";
        }
    }

    /**
     * Recursively deletes directory represented by root
     *
     * @param root directory to be recursively deleted
     * @throws IOException if error occurred during deleting
     */
    private static void clean(final Path root) throws IOException {
        if (Files.exists(root)) {
            Files.walkFileTree(root, DELETE_VISITOR);
        }
    }

    /**
     * Runs {@link Implementor} in two possible ways:
     * <ul>
     *  <li> 1 argument: className - runs {@link #implement(Class, Path)} with given className and temp directory.</li>
     *  <li> 3 arguments: -jar className jarPath - runs {@link #implementJar(Class, Path)} with two last arguments</li>
     * </ul>
     * @param args arguments to choose running way.
     */
    public static void main(String[] args) {
        if (args == null || args.length < 2 || args.length > 3 || args[0] == null || args[1] == null || args.length > 2 && args[2] == null) {
            System.err.println("Usage: Implementor [-jar] <Class name> <Target path>");
            return;
        }
        Implementor implementor = new Implementor();
        try {
            if (args.length == 2) {
                    implementor.implement(Class.forName(args[0]), Paths.get(args[1]));
            } else {
                implementor.implementJar(Class.forName(args[1]), Paths.get(args[2]));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Incorrect class name");
        } catch (ImplerException e) {
            System.out.println("Can't implement class");
        }
    }
}
