package georgy.WarehouseSystem;
public class Item {
    int ID;
    int amount;
    Order associated_order;

    public Item(int ID, int amount, Order associated_order) {
        this.ID = ID;
        this.amount = amount;
        this.associated_order = associated_order;
    }
}
