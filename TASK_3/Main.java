package TASK_3;

import java.sql.*;
import java.util.List;
import java.util.Scanner;
public class Main
{
    private static final String DB_CONNECTION = "jdbc:mysql://192.168.0.101:3306/TASK_5_2";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private static String CLIENT_TABLE = "CLIENTS";
    private static String ORDER_TABLE = "ORDERS";
    private static String ITEM_TABLE = "ITEMS";

    private static ClientDAOEx<Integer, Client> clientDAO;
    private static ClientDAOEx<Integer, Order> orderDAO;
    private static ClientDAOEx<Integer, Item> itemDAO;

    public static void main (String[] args0) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
             Scanner scanner = new Scanner(System.in)
        )
        {
            clientDAO =  new ClientDAOEx(connection, CLIENT_TABLE);
            orderDAO = new ClientDAOEx(connection, ORDER_TABLE);
            itemDAO = new ClientDAOEx(connection, ITEM_TABLE);

            clientDAO.init(Client.class);
            orderDAO.init(Order.class);
            itemDAO.init(Item.class);

            while (true) {
                System.out.println("1: Add client");
                System.out.println("2: View clients");
                System.out.println("3: Delete clients");
                System.out.println("4: Add order");
                System.out.println("5: Delete order");
                System.out.println("6: View orders and Items");
//                System.out.println("7: View items");
                System.out.println("8: Rename item");
                System.out.println("0: Exit");
                System.out.print("Input command: ");

                String s = scanner.nextLine();
                switch (s) {
                    case "1":
                        addClient(scanner);
                        break;
                    case "2":
                        viewClients();
                        break;
                    case "3":
                        deleteClient(scanner);
                        break;
                    case "4":
                        addItem(scanner);
                        break;
                    case "5":
                        deleteOrder(scanner);
                        break;
                    case "6":
                        viewOrders();
                        break;
//                    case "7":
//                        viewItems();
//                        break;
                    case "8":
                        renameItem(scanner);
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Command not found!");
                        break;
                }
            }
        }
    }

    private static void addClient(Scanner scanner)
    {
        System.out.print("Enter client name: ");
        String name = scanner.nextLine();

        clientDAO.add(new Client(name));
    }

    private static void deleteClient(Scanner scanner)
    {
        System.out.print("Enter Client ID: ");
        int iD = Integer.parseInt(scanner.nextLine());

        clientDAO.delete(new Client(iD));
    }

    private static void deleteOrder(Scanner scanner) {
        System.out.print("Enter Order ID: ");
        int iD = Integer.parseInt(scanner.nextLine());

        Order order = new Order();
        order.setOrder_id(iD);
        orderDAO.delete(order);
        List<Item> list = itemDAO.findById(Item.class, "order_id", String.valueOf(iD));
        for (Item item : list) {
            itemDAO.delete(item);
        }
    }

    private static void viewClients()
    {
        List<Client> list = clientDAO.getAll(Client.class);
        System.out.println();
        if (list.isEmpty()) {
            System.out.println("LIST IS EMPTY!\n");
            return;
        }
        for (Client client : list) {
            System.out.println(client.toString());
        }
        System.out.println();
    }
    private static void viewOrders()
    {
        List<Order> orderList = orderDAO.getAll(Order.class);
        System.out.println();
        if (orderList.isEmpty()) {
            System.out.println("ORDER LIST IS EMPTY!\n");
        }
        else {
            for (Order order : orderList) {
                System.out.println(order.toString());
            }
        }

        List<Item> itemList = itemDAO.getAll(Item.class);
        System.out.println();
        if (itemList.isEmpty()) {
            System.out.println("ITEM LIST IS EMPTY!\n");
        }
        else {
            for (Item item : itemList) {
                System.out.println(item.toString());
            }
            System.out.println();
        }
    }

    private static void addItem(Scanner scanner) throws SQLException {
        System.out.print("Select Client by ID: ");
        int iD = Integer.parseInt(scanner.nextLine());

        List<Client> list = clientDAO.findById(Client.class, "client_id", String.valueOf(iD));
        if (list.isEmpty()) {
            System.out.println("CLIENT DOES NOT EXIST!");
            return;
        }

        System.out.print("Enter item name: ");
        String name = scanner.nextLine();

        orderDAO.add(new Order(iD));
        int order_id = orderDAO.getGeneratedId();

        itemDAO.add(new Item(name, order_id));
    }

    private static void renameItem(Scanner scanner)
    {
        System.out.print("Enter Item ID: ");
        int iD = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter new item name: ");
        String newName = scanner.nextLine();

        itemDAO.update(new Item(iD), "name", newName);
    }

}