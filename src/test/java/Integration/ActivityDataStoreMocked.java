package Integration;

import Data.DataStore.Activity.ActivityDataStoreImpl;
import Util.JsonConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.appsactivity.Appsactivity;
import com.google.api.services.appsactivity.model.Activity;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityDataStoreMocked extends ActivityDataStoreImpl {

    public ActivityDataStoreMocked(Appsactivity service) {
        super(service);
    }

    @Override
    public List<Activity> getNewActivityLog(Appsactivity service) throws SQLException {
        ObjectMapper mapper = new ObjectMapper();

        ArrayList<Activity> activities = new ArrayList<>();
        try {
            for (int count = 0; count < 10; count++) {
                Activity activity = JsonConverter.jsonActivityConverter(
                    JsonConverter.convertInputStreamToString(
                        this.getClass().getResourceAsStream("/activity_log_data.json")
                    )
                );
                activities.add(activity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return activities;
    }
}
