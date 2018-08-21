package Data.DataStore.Activity;

import com.google.api.services.appsactivity.model.Activity;

import java.io.IOException;
import java.util.List;

public interface ActivityDataStore {
    void saveActivities(List<Activity> activities);
    List<Activity> getNewActivityLog();
    List<Activity> getSavesActivityLog();
}