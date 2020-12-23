
package georgy.WarehouseSystem;
import java.sql.*;
import java.util.Arrays;

public class SQlConnector {
    private Connection conn = null;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
    public Boolean userExists(String login, byte[] password) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM WAREHOUSE.USERS WHERE password="+ Arrays.toString(password) +"AND login="+ login);
        return !resultSet.isBeforeFirst();
    }
    SQlConnector(String user, String password) throws SQLException {
        String connection_letter = "jdbc:mysql://localhost/test?user=" + user + "&password=" + password + "&useLegacyDatetimeCode=false&serverTimezone=UTC";
        conn = DriverManager.getConnection(connection_letter);
    }
}
