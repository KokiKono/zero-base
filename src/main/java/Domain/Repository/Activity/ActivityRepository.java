package Domain.Repository.Activity;

import com.google.api.services.appsactivity.Appsactivity;
import com.google.api.services.appsactivity.model.Activity;

import java.util.List;

public interface ActivityRepository {
    void saveActivityLogs(List<Activity> activities);
    List<Activity> getActivityLogsFromSave();
    List<Activity> getActivityLogsFromAPI(Appsactivity service) throws SQLException;
}
