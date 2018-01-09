package TASK_1.case2;

import TASK_1.shared.Client;
import TASK_1.shared.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    static final String DB_CONNECTION = "jdbc:mysql://192.168.0.101:3306/TASK_6_1";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "root";

    public static void main(String[] args) throws SQLException
    {
        Scanner sc = new Scanner(System.in);

        ConnectionFactory factory = new ConnectionFactory(DB_CONNECTION, DB_USER, DB_PASSWORD);

        Connection conn = factory.getConnection();
        try {
            ClientDAOEx clientDAOEx = new ClientDAOEx(conn, "Clients");

            clientDAOEx.init();
            clientDAOEx.add(new Client("test", 1));
            clientDAOEx.add(new Client("Roman", 13));
            clientDAOEx.add(new Client("Evgen", 12));
            clientDAOEx.add(new Client("Vovan", 23));
            clientDAOEx.add(new Client("test2", 34));
            clientDAOEx.add(new Client("test3", 45));

            List<Client> list = clientDAOEx.getAll(Client.class);
            for (Client cli : list)
                System.out.println(cli);

            clientDAOEx.delete(list.get(0));
            System.out.println();

            list = clientDAOEx.getAll(Client.class);
            for (Client cli : list)
                System.out.println(cli);

            System.out.print("Selected Client is: ");
            System.out.println(clientDAOEx.findById(Client.class, "3"));

            Client client1 = list.get(3);
            clientDAOEx.update(client1, "name", "Ivan");

            System.out.print("Selected Client is: ");
            System.out.println(clientDAOEx.findById(Client.class, String.valueOf(client1.getId())));

        }
        finally {
            sc.close();
            if (conn != null) conn.close();
        }
    }
}
