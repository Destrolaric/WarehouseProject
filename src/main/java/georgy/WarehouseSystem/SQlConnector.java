
package georgy.WarehouseSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    User getUser(Integer ID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Select * from Warehouse.Users where ID=?");
        statement.setInt(1, ID);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.isBeforeFirst()) {
            resultSet.next();
            return (new User(ID, resultSet.getString("login"), position = resultSet.getString("position"), getUserOrders(resultSet.getInt("ID"))));
        }
        else
            return null;
    }
    ArrayList<Order>getUserOrders(Integer ID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Select OrderID from Warehouse.UsersToOrdersWHERE UserID = ?");
        statement.setInt(1, ID);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Order> orders = new ArrayList<>();
        while (resultSet.next()){
            orders.add(getOrder(resultSet.getInt("OrderID"), true));
        }
        return orders;
    }
    ArrayList<User>getOrderUsers(Integer ID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Select UserID from Warehouse.UsersToOrders WHERE OrderID = ?");
        statement.setInt(1, ID);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<User> users = new ArrayList<>();
        while (resultSet.next()){
            User.add(getUser(resultSet.getInt("OrderID"), true));
        }
        return orders;
    }
    User getUser(String login,Boolean withoutUserOrders ) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Select * from Warehouse.Users where login=?");
        statement.setString(1, login);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.isBeforeFirst()) {
            resultSet.next();
            return !withoutUserOrders ? new  User(resultSet.getInt("ID"), login, resultSet.getString("position"), getUserOrders(resultSet.getInt("ID"))) : new  User(resultSet.getInt("ID"), login, resultSet.getString("position"));
        }

    }
    Order getOrder(int orderID, boolean withoutUsers) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Select * WHERE OrderID = ?");
        statement.setInt(1, orderID);
        ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                return !withoutUsers ? new Order(orderID, resultSet.getBoolean("type"),resultSet.getString("status")) : new Order(orderID, resultSet.getBoolean("type"),resultSet.getString("status"), getOrderUsers(orderID), getOrderItems(orderID));
            }
        }

    private List<Item> getOrderItems(int orderID) {

    }
    Account getAccount(){

    }
    Item getItem(){

    }

}
