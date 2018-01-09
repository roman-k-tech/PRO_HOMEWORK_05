package TASK_2;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<K, T>
{
    private final Connection conn;
    private final String table;

    public AbstractDAO(Connection conn, String table) {
        this.conn = conn;
        this.table = table;
    }

    public void init(Class<T> tClass)  {

        try (PreparedStatement preparedStatement = conn.prepareStatement("SHOW TABLES;"))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                if (resultSet.getString(1).equals(table))
                    return;
            }
        }
        catch (SQLException e) { e.printStackTrace(); }

        try (Statement st = conn.createStatement())
        {
//            st.execute("DROP TABLE IF EXISTS "+ table);

            Field[] fields = tClass.getDeclaredFields();
            String fieldName = null;
            String columns = null;
            String oneColumn = "";
            for (Field field : fields)
            {
                oneColumn = field.getName();
                if (field.getType() == int.class) {
                    oneColumn += " INT NOT NULL";
                }
                else if (field.getType() == String.class) {
                    oneColumn += " VARCHAR(20)";
                }
                else { throw new RuntimeException("\nPARSING ERROR!!!\n"); }
                if (field.isAnnotationPresent(Id.class)) {
                    oneColumn += " AUTO_INCREMENT PRIMARY KEY";
                }
                if (columns == null){
                    columns = oneColumn;
                }
                else {
                    columns = columns + ", " + oneColumn;
                }
            }
            String sqlFull = "CREATE TABLE " + table + " (" + columns + ")";
            st.execute(sqlFull);
        }
        catch (SQLException ex) { throw new RuntimeException(ex); }
    }

    public void add(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();

            StringBuilder names = new StringBuilder();
            StringBuilder values = new StringBuilder();

            for (Field f : fields) {
                f.setAccessible(true);

                names.append(f.getName()).append(',');
                values.append('"').append(f.get(t)).append("\",");
            }
            names.deleteCharAt(names.length() - 1); // last ','
            values.deleteCharAt(values.length() - 1); // last ','

            String sql = "INSERT INTO " + table + "(" + names.toString() + ") VALUES(" + values.toString() + ")";

            try (Statement st = conn.createStatement()) {
                st.execute(sql.toString());
            }
        }
        catch (Exception ex) { throw new RuntimeException(ex); }
    }

    public void delete(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            Field id = null;

            for (Field f : fields) {
                if (f.isAnnotationPresent(Id.class)) {
                    id = f;
                    id.setAccessible(true);
                    break;
                }
            }
            if (id == null)
                throw new RuntimeException("No Id field");

            String sql = "DELETE FROM " + table + " WHERE " + id.getName() + " = \"" + id.get(t) + "\"";

            try (Statement st = conn.createStatement()) {
                st.execute(sql.toString());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void update(T t, String fieldName, String value) {
        Class<?> tClass = t.getClass();
        Field[] fields = tClass.getDeclaredFields();
        String searchName = null;
        String searchValue = null;

        try (Statement statement = conn.createStatement())
        {
            for (Field field : fields) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    searchName = field.getName();
                    searchValue = field.get(t).toString();
                    break;
                }
            }

            String sql = "UPDATE " + table + " SET " + fieldName + "='" + value + "' WHERE " + searchName + "='" + searchValue + "'";
            statement.execute(sql);
        }
        catch (SQLException | IllegalAccessException e) {e.printStackTrace();}
    }

    public List<T> findById(Class<T> tClass, String fieldName, String parameter) {

        List<T> list = new ArrayList<>();
        try (Statement statement = conn.createStatement())
        {
            String sql = "SELECT * FROM " + table + " WHERE " + fieldName + "='" + parameter + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            while (resultSet.next()) {
                T newInstance = (T) tClass.newInstance();

                String columnName = null;
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    columnName = resultSetMetaData.getColumnName(i);
                    Field field = tClass.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(newInstance, resultSet.getObject(columnName));
                }
                list.add(newInstance);
            }
            return list;
        }
        catch (SQLException | InstantiationException | IllegalAccessException | NoSuchFieldException e)
        { e.printStackTrace(); }

        return null;
    }

    public List<T> getAll(Class<T> cls) {
        List<T> res = new ArrayList<>();

        try {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT * FROM " + table)) {
                    ResultSetMetaData md = rs.getMetaData();

                    while (rs.next())
                    {
                        T client = (T) cls.newInstance();

                        for (int i = 1; i <= md.getColumnCount(); i++) {
                            String columnName = md.getColumnName(i);

                            Field field = cls.getDeclaredField(columnName);
                            field.setAccessible(true);

                            field.set(client, rs.getObject(columnName));
                        }

                        res.add(client);
                    }
                }
            }
            return res;
        }
        catch (Exception ex) { throw new RuntimeException(ex); }
    }
}
