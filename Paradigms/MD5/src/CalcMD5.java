import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CalcMD5 {

    public static String getHash(String str) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        MessageDigest m = MessageDigest.getInstance("MD5");
//        m.reset();
        // передаем в MessageDigest байт-код строки
        m.update(str.getBytes("UTF-8"));
        // получаем MD5-хеш строки без лидирующих нулей
        String s2 = new BigInteger(1, m.digest()).toString(16);
        /*StringBuilder sb = new StringBuilder(32);
        // дополняем нулями до 32 символов, в случае необходимости
        for (int i = 0, count = 32 - s2.length(); i < count; i++) {
            sb.append("0");
        }
        // возвращаем MD5-хеш
        return sb.append(s2).toString();*/
        return (s2);
        /*m.update(str.getBytes("UTF-8"));
        byte[] data = m.digest();
        StringBuilder sb = new StringBuilder();
        for (byte temp: data) {
            sb.append(Integer.toString((temp & 0xFF) + 0x100, 16).substring(1));
        }
        return sb.toString();*/
        /*m.update(str.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte temp: m.digest()) {
            sb.append(Integer.toHexString(0xFF & temp));
        }
        StringBuilder begin = new StringBuilder(32);
        for (int i = 0, count = 32 - sb.length(); i < count; i++) {
            begin.append(0x0);
        }
        return begin.append(sb).toString();
        return (String.format("%32s", sb.toString()));
        return sb.toString();*/
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException{
        if (args.length == 0) {
            try {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int temp;
                while ((temp = System.in.read()) != -1) {
                    buffer.write(temp);
                }
            }catch (IOException error) {
                System.out.println("No input");
            }
        } else {
            try {
                for (String fileName : args) {
//                System.out.printf("032x%s", getHash(new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8")).toUpperCase());
                    System.out.printf("%064x" + " *" + "%s" + "\n", new BigInteger(1, MessageDigest.getInstance("SHA256")
                            .digest(new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8")
                                    .getBytes("UTF-8"))));
//
//                }catch (IOException error) {
//                    System.out.println("Файл " + System.getProperty("user.dir") + args[0] + " не найден");
//                }
                }
            } catch (IOException error) {

            }
        }
    }
}
