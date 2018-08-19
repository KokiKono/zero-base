package Domain.Repository.Activity;

import com.google.api.services.appsactivity.model.Activity;

import java.util.List;

public interface ActivityRepository {
    void saveActivityLogs(List<Activity> activities);
    List<Activity> getActivityLogsFromSave();
}
