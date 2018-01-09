package TASK_2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main
{
    static private final String DB_CONNECTION = "jdbc:mysql://192.168.0.101:3306/TASK_5_2";
    static private final String DB_USER = "root";
    static private final String DB_PASSWORD = "root";

    static private String table = "Flats";
    private static ClientDAOEx clientDAOEx;

    public static void main(String[] args) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
             Scanner scanner = new Scanner(System.in)
        ) {
            clientDAOEx = new ClientDAOEx(connection, table);
            clientDAOEx.init(Flat.class);

            while (true) {
                System.out.println("1: Add Flat");
                System.out.println("2: Select flats");
                System.out.println("3: Delete Flat");
                System.out.println("5: View list");
                System.out.println("6: Change flat");
                System.out.println("0: Exit");
                System.out.print("Input command: ");

                String s = scanner.nextLine();
                switch (s) {
                    case "1":
                        addFlat(scanner);
                        break;
                    case "2":
                        selectFlat(scanner);
                        break;
                    case "3":
                        deleteFlat(scanner);
                        break;
                    case "5":
                        viewFlats();
                        break;
                    case "6":
                        changeFlat(scanner);
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

    private static void addFlat(Scanner scanner)
    {
            System.out.print("Enter district: ");
            String district = scanner.nextLine();
            System.out.print("Address: ");
            String address = scanner.nextLine();
            System.out.print("Square: ");
            int square = Integer.parseInt(scanner.nextLine());
            System.out.print("Amount of rooms: ");
            int rooms = Integer.parseInt(scanner.nextLine());
            System.out.print("Price: ");
            int price = Integer.parseInt(scanner.nextLine());

            Flat flat = new Flat(district, address, square, rooms, price);
            clientDAOEx.add(flat);
    }

    private static void deleteFlat(Scanner scanner)
    {
        System.out.print("Enter flat ID: ");
        int iD = Integer.parseInt(scanner.nextLine());

        clientDAOEx.delete(new Flat(iD));
    }

    private static void viewFlats() {
        List<Flat> list = clientDAOEx.getAll(Flat.class);
        System.out.println();
        if (list.isEmpty()) {
            System.out.println("LIST IS EMPTY!\n");
            return;
        }
        for (Flat flat : list)
        {
            System.out.println(flat.toString());
        }
        System.out.println();
    }

    private static void selectFlat(Scanner scanner) {
        System.out.println("Select flat by parameter: 1 = district, 2 = address, 3 = square, 4 = numer of rooms, 5 = price.");
        System.out.println("0 = Exit.");
        System.out.print("Parameter: ");
        int parameter = Integer.parseInt(scanner.nextLine());
        String value = null;
        if (parameter == 0)
            return;
        else if (parameter >= 1 && parameter <= 5) { }
        else {
            System.out.println("Error! Incorrect value!");
            return;
        }
        System.out.print("Parameter value: ");
        value = scanner.nextLine();

        List<Flat> list = new ArrayList<>();
        if (parameter == 1) {
            list = clientDAOEx.findById(Flat.class, "district", value);
        }
        else if (parameter == 2) {
            list = clientDAOEx.findById(Flat.class, "address", value);
        }
        else if (parameter == 3) {
            list = clientDAOEx.findById(Flat.class, "square", value);
        }
        else if (parameter == 4) {
            list = clientDAOEx.findById(Flat.class, "rooms", value);
        }
        else if (parameter == 5) {
            list = clientDAOEx.findById(Flat.class, "price", value);
        }
        if (list.isEmpty()) {
            System.out.println("LIST IS EMPTY!\n");
            return;
        }
        for (Flat flat : list)
        {
            System.out.println(flat.toString());
        }
        System.out.println();
    }

    private static void changeFlat(Scanner scanner)
    {
        System.out.print("Enter flat ID: ");
        int iD = Integer.parseInt(scanner.nextLine());
        System.out.println("Select parameter to change by: 1 = district, 2 = address, 3 = square, 4 = numer of rooms, 5 = price.");
        System.out.println("0 = Exit.");
        System.out.print("Parameter: ");
        int parameter = Integer.parseInt(scanner.nextLine());
        String value = null;
        if (parameter == 0)
            return;
        else if (parameter >= 1 && parameter <= 5) { }
        else {
            System.out.println("Error! Incorrect value!");
            return;
        }

        System.out.print("Parameter value: ");
        value = scanner.nextLine();

        if (parameter == 1) {
            clientDAOEx.update(new Flat(iD), "district", value);
        }
        else if (parameter == 2) {
            clientDAOEx.update(new Flat(iD), "address", value);
        }
        else if (parameter == 3) {
            clientDAOEx.update(new Flat(iD), "square", value);
        }
        else if (parameter == 4) {
            clientDAOEx.update(new Flat(iD), "rooms", value);
        }
        else if (parameter == 5) {
            clientDAOEx.update(new Flat(iD), "price", value);
        }
    }

}