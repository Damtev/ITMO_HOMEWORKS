import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Sum {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        try {
            if (args.length == 0) {
                byte[] buffer = new byte[System.in.available()];
                System.in.read(buffer);
                byte[] answer = MessageDigest.getInstance("SHA-256").digest(buffer);
                for (byte temp : answer) {
                    System.out.printf("%02x", temp);
                }
                System.out.println(" *-");
            } else {
                for (String fileName : args) {
                    System.out.printf("%064x" + " *" + "%s" + "\n", new BigInteger(1, MessageDigest.getInstance("SHA-256")
                            .digest(new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8")
                                    .getBytes("UTF-8"))), fileName);
                }
            }
        } catch (NoSuchAlgorithmException error) {
            System.out.println("Wrong algorithm");
        } catch (IOException error) {
            System.out.println("Files weren't fond");
        }
    }
}
