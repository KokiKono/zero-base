package Util;

import com.google.api.services.appsactivity.model.Activity;
import com.google.api.services.appsactivity.model.Event;
import com.google.api.services.appsactivity.model.Rename;
import com.google.api.services.appsactivity.model.Target;
import com.google.api.services.appsactivity.model.User;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonConverter {
    public static String convertInputStreamToString(InputStream is) throws IOException {
        InputStreamReader reader = new InputStreamReader(is);
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[512];
        int read;
        while (0 <= (read = reader.read(buffer))) {
            builder.append(buffer, 0, read);
        }
        return builder.toString();
    }


    public static Activity jsonActivityConverter(String json) {
        Activity activity = new Activity();

        JSONObject jsonRoot = new JSONObject(json);

        String jsonCombinedEvent = jsonRoot.getJSONObject("combinedEvent").toString();
        JSONArray singleEventsArray = jsonRoot.getJSONArray("singleEvents");
        ArrayList<Event> events = new ArrayList<>();

        for(Object o: singleEventsArray) {
            JSONObject jsonObject = (JSONObject)o;
            events.add(jsonEventConverter((jsonObject.toString())));
        }

        activity.setCombinedEvent(jsonEventConverter(jsonCombinedEvent));
        activity.setSingleEvents(events);

        return activity;
    }

    public static Event jsonEventConverter(String json) {
        Event event = new Event();

        JSONObject jsonRoot = new JSONObject(json);

        JSONArray jsonAdditionalEventTypes = jsonRoot.getJSONArray("additionalEventTypes");
        ArrayList<String> additionalEventTypes = new ArrayList<>();
        for(Object object: jsonAdditionalEventTypes) {
            additionalEventTypes.add((String)object);
        }

        String renameJson = jsonRoot.getJSONObject("rename").toString();
        String targetJson = jsonRoot.getJSONObject("target").toString();
        String userJson = jsonRoot.getJSONObject("user").toString();

        event.setAdditionalEventTypes(additionalEventTypes);
        event.setEventTimeMillis(jsonRoot.getBigInteger("eventTimeMillis"));
        event.setFromUserDeletion(jsonRoot.getBoolean("fromUserDeletion"));
        event.setPrimaryEventType(jsonRoot.getString("primaryEventType"));
        event.setRename(jsonRenameConverter(renameJson));
        event.setTarget(jsonTargetConverter(targetJson));
        event.setUser(jsonUserConverter(userJson));

        return event;
    }

    public static Rename jsonRenameConverter(String json) {
        JSONObject jsonRoot = new JSONObject(json);

        Rename rename = new Rename();
        rename.setNewTitle(jsonRoot.getString("newTitle"));
        rename.setOldTitle(jsonRoot.getString("oldTitle"));

        return rename;
    }

    public static Target jsonTargetConverter(String json) {
        JSONObject jsonRoot = new JSONObject(json);

        Target target = new Target();
        target.setId(jsonRoot.getString("id"));
        target.setMimeType(jsonRoot.getString("mimeType"));
        target.setName(jsonRoot.getString("name"));

        return target;
    }

    public static User jsonUserConverter(String json) {
        JSONObject jsonRoot = new JSONObject(json);

        User user = new User();
        user.setIsDeleted(jsonRoot.getBoolean("isMe"));
        user.setName(jsonRoot.getString("name"));
        user.setPermissionId(jsonRoot.getString("permissionId"));

        return user;
    }


}
