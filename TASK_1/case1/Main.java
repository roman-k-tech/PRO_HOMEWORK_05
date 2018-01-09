package TASK_1.case1;

import TASK_1.shared.Client;
import TASK_1.shared.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main
{
    static final String DB_CONNECTION = "jdbc:mysql://192.168.0.101:3306/TASK_6_1";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "root";

    public static void main(String[] args) throws SQLException
    {
        Scanner sc = new Scanner(System.in);

        ConnectionFactory factory = new ConnectionFactory(DB_CONNECTION, DB_USER, DB_PASSWORD);

        Connection conn = factory.getConnection();
        try {
            ClientDAO clientDAO = new ClientDAOImpl(conn);
            clientDAO.init();

            while (true) {
                System.out.println("1: add client");
                System.out.println("2: view clients");
                System.out.println("3: view count");
                System.out.println("4: delete client");
                System.out.println("0: Exit");
                System.out.print("-> ");

                String s = sc.nextLine();
                switch (s) {
                    case "1":
                        System.out.print("Enter client name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter client age: ");
                        String sAge = sc.nextLine();
                        int age = Integer.parseInt(sAge);

                        clientDAO.addClient(name, age);
                        break;
                    case "2":
                        List<Client> list = clientDAO.getAll();
                        for (Client client : list) {
                            System.out.println(client);
                        }
                        break;
                    case "3":
                        System.out.println(clientDAO.count());
                        break;
                    case "4":
                        System.out.print("Enter client ID: ");
                        int iD = Integer.parseInt(sc.nextLine());
                        clientDAO.deleteClient(iD);
                        break;
                    case "0":
                        return;
                    default:
                        return;
                }
            }
        } finally {
            sc.close();
            if (conn != null) conn.close();
        }
    }
}
