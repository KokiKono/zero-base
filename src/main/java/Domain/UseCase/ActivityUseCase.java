package Domain.UseCase;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.appsactivity.Appsactivity;
import com.google.api.services.appsactivity.model.Activity;

import java.io.IOException;
import java.util.List;

public interface ActivityUseCase {
    /**
     * アクティビティ情報を取得し保存する。
     * @return
     */
    void loadActivityLog() throws IOException;

    /**
     * 保存しているアクティビティ情報を一覧取得する。
     * @return
     */
    List<Activity> getActivityLogs();
}
