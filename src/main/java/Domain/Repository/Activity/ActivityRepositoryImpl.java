package Domain.Repository.Activity;

import Data.DataStore.Activity.ActivityDataStore;
import com.google.api.services.appsactivity.Appsactivity;
import com.google.api.services.appsactivity.model.Activity;

import java.sql.SQLException;
import java.util.List;

public class ActivityRepositoryImpl implements ActivityRepository {

    ActivityDataStore activityDataStore;

    public ActivityRepositoryImpl(ActivityDataStore activityDataStore) {
        this.activityDataStore = activityDataStore;
    }

    @Override
    public void saveActivityLogs(List<Activity> activities) throws SQLException {
        this.activityDataStore.saveActivities(activities);
    }

    @Override
    public List<Activity> getActivityLogsFromSave() throws SQLException {

        return this.activityDataStore.getSavesActivityLog();

    }

    @Override
    public List<Activity> getActivityLogsFromAPI(Appsactivity service) throws SQLException {
        return this.activityDataStore.getNewActivityLog(service);
    }
}
