package TASK_2;

import java.sql.Connection;

/**
 * Created by Bios on 29.11.2017.
 */
public class ClientDAOEx extends AbstractDAO<Integer, Flat>
{
    public ClientDAOEx(Connection conn, String table)
    {
        super(conn, table);
    }
}
