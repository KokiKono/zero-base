package Data.DataStore.Activity;

import Data.DataStore.Dao.ActivityDao;
import Data.Entity.ActivityLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.appsactivity.Appsactivity;
import com.google.api.services.appsactivity.model.Activity;
import com.google.api.services.appsactivity.model.Event;
import com.google.api.services.appsactivity.model.ListActivitiesResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityDataStoreImpl implements ActivityDataStore {

    private Appsactivity service;

    public ActivityDataStoreImpl(Appsactivity service) {
        this.service = service;
    }

    @Override
    public void saveActivities(List<Activity> activities) throws SQLException{
        for (Activity activity : activities) {
            ActivityDao activityDao = new ActivityDao(ActivityDao.newInstance());
            activityDao.saveActivity(activity);
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
    public List<Activity> getSavesActivityLog() throws SQLException{
        ActivityDao activityDao = new ActivityDao(ActivityDao.newInstance());
        List<ActivityLog> activityLogs = activityDao.getAllLogs();
        if (activityLogs.size() == 0) {
            return new ArrayList<>();
        }

        ObjectMapper mapper = new ObjectMapper();

        List<Activity> activities = new ArrayList<>();
        try {
            for (ActivityLog activityLog : activityLogs) {
                Activity activity = mapper.readValue(activityLog.getJson(), Activity.class);
                activities.add(activity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return activities;
    }
}
