package TASK_1.case2;

import TASK_1.shared.Client;

import java.sql.Connection;

/**
 * Created by Bios on 29.11.2017.
 */
public class ClientDAOEx extends AbstractDAO<Integer, Client>
{
    public ClientDAOEx(Connection conn, String table)
    {
        super(conn, table);
    }
}
