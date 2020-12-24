package georgy.WarehouseSystem;

import java.util.List;

public class Account {
    private String name;
    private String description;
    private Order order;
    private List<User> users;
    Account(String name, String description, Order order){
        this.name = name;
        this.description = description;
        this.order = order;
    }
    Account(String name, String description, Order order, List<User> users){
        this.name = name;
        this.description = description;
        this.order = order;
        this.users = users;
    }
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
