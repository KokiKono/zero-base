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
    public List<Activity> getNewActivityLog(Appsactivity service) throws SQLException{

        ArrayList<Activity> resultActivities = new ArrayList<>();
        ActivityDao activityDao = new ActivityDao(ActivityDao.newInstance());
        Date latestLogDateTime = activityDao.latestLogDateTime();
//        if (latestLogDateTime == null) latestLogDateTime = new Date();

        String nextPageToken = null;
        try {
            do {
                Appsactivity.Activities.List list = service.activities().list()
                    .setSource("drive.google.com")
                    .setDriveAncestorId("root")
                    .setPageSize(10);
                if (nextPageToken != null) {
                    list.setPageToken(nextPageToken);
                }

                ListActivitiesResponse activitiesResponse = list.execute();
                List<Activity> activities = activitiesResponse.getActivities();

                for (Activity activity : activities) {
                    Event event = activity.getCombinedEvent();
                    if (latestLogDateTime == null) {
                        resultActivities.add(activity);
                        continue;
                    }
                    Date date = new Date(event.getEventTimeMillis().longValue());
                    int diff = latestLogDateTime.compareTo(date);

                    if (diff > 0) {
                        resultActivities.add(activity);
                    }
                }
                nextPageToken = activitiesResponse.getNextPageToken();

            } while (nextPageToken == null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultActivities;
    }

    @Override
    public List<Activity> getSavesActivityLog() {
        return null;
    }
}
