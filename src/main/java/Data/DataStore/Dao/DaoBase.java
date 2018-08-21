package Data.DataStore.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoBase {
    protected Connection con;
    private boolean isProgressTransaction = false;

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

    public void beginTransaction() throws SQLException{
        this.isProgressTransaction = true;
        this.con.setAutoCommit(false);
    }

    public void commit() throws SQLException{
        this.isProgressTransaction = false;
        this.con.commit();
        this.con.setAutoCommit(true);
    }

    public void rollback() throws SQLException{

        if(this.isProgressTransaction == false) return;

        this.isProgressTransaction = false;
        this.con.rollback();
        this.con.setAutoCommit(true);
    }



}
