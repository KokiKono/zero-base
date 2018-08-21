package Data.DataStore.Dao;

import Data.Entity.ActivityLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.appsactivity.model.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityDao extends DaoBase {
    public ActivityDao(Connection con) {
        super(con);
    }

    public void saveActivity(Activity activity) {
        ObjectMapper mapper = new ObjectMapper();
        PreparedStatement statement = null;
        try {
            String json = mapper.writeValueAsString(activity);

            String sql = "INSERT INTO activity_log(json_log) VALUES(?)";
            statement = super.con.prepareStatement(sql);
            statement.setString(1, json);

            super.beginTransaction();
            int updateRowCount = statement.executeUpdate();
            if (updateRowCount == 0) {
                throw new SQLException("ログをインサートできませんでした。");
            }
            super.commit();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                super.rollback();
                super.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                super.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Date latestLogDateTime() {
        String sql = "SELECT create_at FROM activity_log ORDER BY create_at DESC LIMIT 1";
        Date createAt = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = super.con.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                createAt = resultSet.getDate("create_at");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                super.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return createAt;
    }

    public List<ActivityLog> getAllLogs() {
        String sql = "SELECT * FROM activity_log ORDER BY create_at ASC";

        Statement statement = null;
        ResultSet resultSet = null;

        ArrayList<ActivityLog> activityLogs = new ArrayList<>();
        try {
            statement = super.con.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                ActivityLog activityLog = new ActivityLog(
                    resultSet.getInt("id"),
                    resultSet.getString("json_log"),
                    resultSet.getDate("create_at")
                );
                activityLogs.add(activityLog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(statement != null) statement.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                super.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return activityLogs;
    }
}
