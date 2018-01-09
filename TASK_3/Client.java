package TASK_3;

public class Client
{
    @Id
    private int client_id;

    private String name;

    public Client(String name) {
        this.name = name;
    }

    public Client(int client_id) {
        this.client_id = client_id;
    }

    public Client() { }

    @Override
    public String toString() {
        return "Client " + client_id + ", name = " + name;
    }
}