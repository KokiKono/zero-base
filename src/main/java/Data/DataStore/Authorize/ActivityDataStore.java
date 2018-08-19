package Data.DataStore.Authorize;

import com.google.api.services.appsactivity.model.Activity;

import java.util.List;

public interface ActivityDataStore {
    List<Activity> getNewActivityLog();
    List<Activity> getSavesActivityLog();
}
