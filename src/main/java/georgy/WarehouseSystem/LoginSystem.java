package georgy.WarehouseSystem;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class LoginSystem {
    static public Boolean login (SQlConnector connector, String login, String password) throws NoSuchAlgorithmException, SQLException {
        return connector.userExists(login, crypt(password));
    }
    static public byte[] crypt(String password) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] pass_bytes = password.getBytes(StandardCharsets.UTF_8);
        pass_bytes = md5.digest(pass_bytes);
        return pass_bytes;
    }

}
