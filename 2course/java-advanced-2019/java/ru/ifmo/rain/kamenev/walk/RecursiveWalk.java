package ru.ifmo.rain.kamenev.walk;

import java.io.*;
import java.nio.file.*;
import java.util.stream.Stream;

public class RecursiveWalk {

    private static final int HASH_LENGTH = 8;
    private static final int FNV_32_PRIME = 0x01000193;
    private static final int BYTES_BLOCK_LENGTH = 4098;

    private static int fnvHash(Path path) {
        int hash = 0x811c9dc5;
        try (InputStream reader = Files.newInputStream(path)) {
            while (true) {
                byte[] bytes = reader.readNBytes(BYTES_BLOCK_LENGTH);
                if (bytes.length == 0) {
                    return hash;
                } else {
                    for (byte b : bytes) {
                        hash = (hash * FNV_32_PRIME) ^ (b & 0xff);
                    }
                }
            }
        } catch (IOException e) {
            return 0;
        }
    }

    private static String concatAns(String hash, String path) {
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < HASH_LENGTH - hash.length(); i++) {
            answer.append("0");
        }
        answer.append(hash).append(" ").append(path).append(System.lineSeparator());
        return answer.toString();
    }

    private static void printError(String message) {
        System.out.println("-------ERROR: " + message + " -------------------");
    }

    private static void walk(String inputFile, String outputFile) {
        try {
            int fileIndex = outputFile.lastIndexOf("/");
            if (fileIndex < 0) {
                fileIndex = 0;
            }
            String directory = outputFile.substring(0, fileIndex);
            Files.createDirectories(Paths.get(directory));
            File file = new File(outputFile);
            file.createNewFile();
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile));
                 BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile))) {
                String dir;
                while ((dir = reader.readLine()) != null) {
                    try {
                        Path begin = Paths.get(dir);
                        if (begin.toFile().exists()) {
                            try {
                                Stream<Path> paths = Files.walk(Paths.get(dir)).filter(path -> path.toFile().isFile());
                                paths.forEach(path -> {
                                    try {
                                        writer.write(concatAns(Integer.toHexString(fnvHash(path)), path.toString()));
                                    } catch (IOException error) {
                                        printError("Can't write to output file");
                                    }
                                });
                            } catch (IOException error) {
                                printError("Can't get access to file");
                            }
                        } else {
                            writer.write(concatAns("0", dir));
                        }
                    } catch (InvalidPathException error) {
                        writer.write(concatAns("0", dir));
                    }
                }
            } catch (InvalidPathException error) {
                printError("Invalid input/output filenames");
            } catch (IOException error) {
                printError("Can't open input/output files");
            }
        } catch (InvalidPathException e) {
            printError("Invalid output filenames");
        } catch (IOException e) {
            printError("Can't create output file");
        }
    }

    public static void main(String[] args) {
        if (args != null && args.length >= 2) {
            if (args[0] == null || args[1] == null) {
                printError("Input/output can't be null");
            } else {
                walk(args[0], args[1]);
            }
        } else {
            printError("Need input and output filenames");
        }
    }
}
