package TASK_3;

public class Order
{
    @Id
    private int order_id;

    private int client_id;

    public Order(int client_id) {
        this.client_id = client_id;
    }

    public Order(int order_id, int client_id) {
        this.order_id = order_id;
        this.client_id = client_id;
    }

    public Order() { }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    @Override
    public String toString() {
        return "Order ID: " + order_id + ", Client ID = " + client_id;
    }
}
