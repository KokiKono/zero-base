package Data.DataStore.Activity;

import Data.DataStore.Dao.ActivityDao;
import com.google.api.services.appsactivity.Appsactivity;
import com.google.api.services.appsactivity.model.Activity;
import com.google.api.services.appsactivity.model.ListActivitiesResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ActivityDataStoreImpl implements ActivityDataStore{

    private ActivityDao activityDao;
    private Appsactivity service;

    public ActivityDataStoreImpl(Appsactivity service) throws SQLException{
        this.service = service;
        this.activityDao = new ActivityDao(ActivityDao.newInstance());
    }

    @Override
    public void saveActivities(List<Activity> activities) {
        for(Activity activity: activities) {
            this.activityDao.saveActivity(activity);
        }
    }

    @Override
    public List<Activity> getNewActivityLog(){
        return null;
    }

    @Override
    public List<Activity> getSavesActivityLog() {
        return null;
    }
}
