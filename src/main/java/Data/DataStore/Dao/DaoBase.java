package Data.DataStore.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoBase {
    private Connection con;

    public DaoBase(Connection connection) {
        this.con = connection;
    }

    public static Connection newInstance() throws SQLException{
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/gda_sample?user=root&password=");
    }

    public void close() throws SQLException{
        this.con.close();
    }

}
