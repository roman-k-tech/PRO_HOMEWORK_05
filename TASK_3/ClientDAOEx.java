package TASK_3;

import TASK_2.Flat;

import java.sql.Connection;

/**
 * Created by Bios on 29.11.2017.
 */
public class ClientDAOEx<K, T> extends AbstractDAO<K, T>
{
    public ClientDAOEx(Connection conn, String table)
    {
        super(conn, table);
    }
}
