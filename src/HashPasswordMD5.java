import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPasswordMD5 {
    private String password;
    private String hashPassword;

    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.password = password;
        hashPassword();
    }

    private void hashPassword() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // Convert the byte array to a hexadecimal string representation
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }

        hashPassword = sb.toString();

    }

    public String getHashPassword() {
        return hashPassword;
    }
}
