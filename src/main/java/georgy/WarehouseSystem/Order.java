package georgy.WarehouseSystem;

import java.util.List;

public class Order {
    private Integer ID;
    private Boolean type;
    private String description;
    private List<User> users;
    private List<Item> items;

    Order (int ID, Boolean type, String description){
        this.ID = ID;
        this.type = type;
        this.description  = description;
    }

    Order (int ID, Boolean type, String description, List<User> users, List<Item> items){
        this.ID = ID;
        this.type = type;
        this.description  = description;
        this.users = users;
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}
