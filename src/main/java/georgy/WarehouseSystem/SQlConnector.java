
package georgy.WarehouseSystem;

import java.sql.*;
import java.util.LinkedList;

public class SQlConnector {
    private String login;
    private Connection conn;
    private String position;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    Integer getUserId(String login) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT ID FROM Warehouse.Users WHERE login=?");
        statement.setString(1, login);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt("ID");
    }

    void addItem(String name, String description) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO Warehouse.Items (name, description)VALUES (?, ?)");
        statement.setString(1, name);
        statement.setString(2, description);
        statement.execute();
    }

    void getUserInfo(String login) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT ID ,name, surname, login, position  FROM Warehouse.Users where login=?");
        statement.setString(1, login);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        System.out.println("ID: " + resultSet.getInt("ID")
                + "   Name: "
                + resultSet.getString("name") +
                "   Surname: "
                + resultSet.getString("surname") +
                "   login: " +
                resultSet.getString("login") +
                "   Position: " +
                resultSet.getString("position"));

    }

    void getUserInfo(Integer ID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT ID ,name, surname, login, position  FROM Warehouse.Users where ID=?");
        statement.setInt(1, ID);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        System.out.println("ID: " + resultSet.getInt("ID")
                + "   Name: "
                + resultSet.getString("name") +
                "   Surname: "
                + resultSet.getString("surname") +
                "   login: " +
                resultSet.getString("login") +
                "   Position: " +
                resultSet.getString("position"));

    }

    void getAllUsers() throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT login FROM Warehouse.Users");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            getUserInfo(resultSet.getString("login"));
        }

    }

    void createNewUser(LinkedList<String> user) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO Warehouse.Users (name, surname, login, password, position)VALUES (?, ?, ?, ?, ?)");
        statement.setString(1, user.get(0));
        statement.setString(2, user.get(1));
        statement.setString(3, user.get(2));
        statement.setString(4, user.get(3));
        statement.setString(5, user.get(4));
        statement.execute();

    }

    void setCurrentUserPrivileges() throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT position FROM Warehouse.Users WHERE login=?");
        statement.setString(1, this.login);
        ResultSet resultSet = statement.executeQuery();
        //int value = .getInt(0);
        resultSet.next();
        this.position = resultSet.getString("position");
    }

    public Boolean userExists(String login, byte[] password) throws SQLException {
        String md = org.apache.commons.codec.digest.DigestUtils.md5Hex(password);
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM Warehouse.Users WHERE password=? AND login=?");
        statement.setString(1, md);
        statement.setString(2, login);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.isBeforeFirst();
    }

    SQlConnector(String user, String password) throws SQLException {
        String connection_letter = "jdbc:mysql://localhost/test?user=" + user + "&password=" + password + "&useLegacyDatetimeCode=false&serverTimezone=UTC";
        conn = DriverManager.getConnection(connection_letter);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPosition() {
        return position;
    }

    public Integer getItemID(String name) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT ID FROM Warehouse.Items WHERE name=?");
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt("ID");
    }

    public void getItemInfo(Integer ID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM Warehouse.Items where ID=?");
        statement.setInt(1, ID);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        System.out.println("ID: " + resultSet.getInt("ID")
                + "   Name: "
                + resultSet.getString("name") +
                "   Surname: "
                + resultSet.getString("description"));
    }

    public void getAllItems() throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT ID FROM Warehouse.Items");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            getItemInfo(resultSet.getInt("ID"));
        }


    }

    public int getLastAccountID() throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Select MAX(AccountID) from Warehouse.Accounts");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            System.out.println(resultSet.getFetchSize());
            return resultSet.getInt(1);
        } else {
            return 1;
        }
    }

    public void createOrderToWorkerLink(int ID, String worker) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO Warehouse.UsersToOrders (UserID, OrderID)VALUES (?, ?)");
        statement.setInt(1, Integer.parseInt(worker));
        statement.setInt(2, ID);
        statement.execute();
    }

    public void createOrderToItemLink(Integer ID, String item, String amount) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO Warehouse.ItemsToOrders (OrderID, ItemID, amount)VALUES (?, ?, ?)");
        statement.setInt(1, ID);
        statement.setInt(2, Integer.parseInt(item));
        statement.setInt(3, Integer.parseInt(amount));
        statement.execute();
    }

    public void createAccount(Integer ID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Insert INTO Warehouse.Accounts (OrderID)Values (?)");
        statement.setInt(1, ID);
        statement.executeUpdate();
        getLastAccountID();

    }

    public int getLastID() throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Select MAX(OrderID) from Warehouse.Orders");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            System.out.println(resultSet.getFetchSize());
            return resultSet.getInt(1);
        } else {
            return 1;
        }
    }

    public void showAssignedOrders(Integer ID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Select OrderID from Warehouse.UsersToOrders where UserID=?");
        statement.setInt(1, ID);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            showOrderInfo(resultSet.getInt("UserID"));
        }
    }

    public void showAffiliatedWorkers(Integer ID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Select UserID from Warehouse.UsersToOrders where OrderID=?");
        statement.setInt(1, ID);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            getUserInfo(resultSet.getInt("UserID"));
        }
    }

    public void showOrderInfo(Integer ID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Select * from Warehouse.Orders where OrderID = ?");
        statement.setInt(1, ID);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        System.out.println("ID: " + resultSet.getInt("OrderID") + "  type: " + (resultSet.getBoolean("type") ? "Delivery" : "Production") + "    Status: " + (resultSet.getString("status")));
    }

    public void assignToOrder(int OrderID, int UserID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO Warehouse.UsersToOrders (OrderID, UserID) VALUES (?,?)");
        statement.setInt(1, OrderID);
        statement.setInt(2, UserID);
        statement.execute();
    }

    public void removeFromOrder(Integer OrderID, Integer UserID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("DELETE FROM Warehouse.UsersToOrders WHERE OrderID=? and UserID=?");
        statement.setInt(1, OrderID);
        statement.setInt(2, UserID);
        statement.execute();
    }

    public void showAllOrders() throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT OrderID from Warehouse.Orders");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            showOrderInfo(resultSet.getInt("OrderID"));
        }

    }

    public void createOrder(Boolean type, String status, String[] workerList, String[] items_list, String[] amounts_list) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("INSERT INTO Warehouse.Orders (type, status)VALUES (?, ?)");
        statement.setBoolean(1, type);
        statement.setString(2, status);
        statement.executeUpdate();
        for (String worker_id : workerList) createOrderToWorkerLink(getLastID(), worker_id);
        for (int i = 0; i < items_list.length; i++) {
            createOrderToItemLink(getLastID(), items_list[i], amounts_list[i]);
        }
        createAccount(getLastID());
        System.out.println("Account is: " + getLastAccountID());
        System.out.println(createAccountLink(getLastID(), getLastAccountID()));
    }

    private int createAccountLink(int id, int account) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO Warehouse.AccountsToOrders (OrderID, AccountID) VALUES (?,?)");
        statement.setInt(1, id);
        statement.setInt(2, account);
        return statement.executeUpdate();
    }
}
