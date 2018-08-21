package Domain.UseCase;

import com.google.api.services.appsactivity.model.Activity;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.List;

public interface ActivityUseCase {
    /**
     * アクティビティ情報を取得し保存する。
     * @return
     */
    void loadActivityLog() throws IOException, GeneralSecurityException, SQLException;

    /**
     * 保存しているアクティビティ情報を一覧取得する。
     * @return
     */
    List<Activity> getActivityLogs() throws SQLException;
}
