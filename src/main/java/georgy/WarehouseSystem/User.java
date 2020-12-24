package georgy.WarehouseSystem;

import java.util.List;

public class User {
    private Integer ID;
    private String name;
    private String position;
    private List<Order> assigned_Orders;
    User(Integer ID, String name, String position){
        this.ID = ID;
        this.name = name;
        this.position = position;
    }
    User(Integer ID, String name, String position, List<Order> orders ){
        this.ID = ID;
        this.name = name;
        this.position = position;
        this.assigned_Orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public List<Order> getAssigned_Orders() {
        return assigned_Orders;
    }

    public void setAssigned_Orders(List<Order> assigned_Orders) {
        this.assigned_Orders = assigned_Orders;
    }
}
