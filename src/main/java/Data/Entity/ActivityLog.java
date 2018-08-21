package Data.Entity;

import java.util.Date;

public class ActivityLog {

    private int id;
    private String json;
    private Date createAt;

    public ActivityLog(int id, String json, Date createAt) {
        this.id = id;
        this.json = json;
        this.createAt = createAt;
    }

    public int getId() {
        return id;
    }

    public String getJson() {
        return json;
    }


    public Date getCreateAt() {
        return createAt;
    }
}
