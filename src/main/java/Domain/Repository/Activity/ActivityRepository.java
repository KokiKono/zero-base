package Domain.Repository.Activity;

import com.google.api.services.appsactivity.Appsactivity;
import com.google.api.services.appsactivity.model.Activity;

import java.sql.SQLException;
import java.util.List;

public interface ActivityRepository {
    void saveActivityLogs(List<Activity> activities) throws SQLException;
    List<Activity> getActivityLogsFromAPI(Appsactivity service) throws SQLException;
    List<Activity> getActivityLogsFromSave() throws SQLException;
}
