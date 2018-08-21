package Domain.Repository.Activity;

import Data.DataStore.Activity.ActivityDataStore;
import com.google.api.services.appsactivity.model.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityRepositoryImpl implements ActivityRepository {

    ActivityDataStore activityDataStore;

    public ActivityRepositoryImpl(ActivityDataStore activityDataStore) {
        this.activityDataStore = activityDataStore;
    }

    @Override
    public void saveActivityLogs(List<Activity> activities) {
        this.activityDataStore.saveActivities(activities);
    }

    @Override
    public List<Activity> getActivityLogsFromSave() {

        List<Activity> activities = new ArrayList<>();

        List oldActivity = this.activityDataStore.getSavesActivityLog();
        List newActivity = this.activityDataStore.getNewActivityLog();

        activities.addAll(oldActivity);
        activities.addAll(newActivity);

        return activities;
    }
}
