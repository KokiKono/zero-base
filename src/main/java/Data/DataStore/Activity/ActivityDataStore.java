package Data.DataStore.Activity;

import com.google.api.services.appsactivity.Appsactivity;
import com.google.api.services.appsactivity.model.Activity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ActivityDataStore {
    void saveActivities(List<Activity> activities) throws SQLException;
    List<Activity> getNewActivityLog(Appsactivity service) throws SQLException;
    List<Activity> getSavesActivityLog() throws SQLException;
}