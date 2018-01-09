package TASK_3;

public class Item {

    @Id
    private int item_id;

    private String name;

    private int order_id;

    public Item() { }

    public Item(int item_id) {
        this.item_id = item_id;
    }

    public Item(String name, int order_id) {
        this.name = name;
        this.order_id = order_id;
    }

    @Override
    public String toString() {
        return "Item ID: " + item_id + ", name: " + name + ", order ID = " + order_id;
    }
}
