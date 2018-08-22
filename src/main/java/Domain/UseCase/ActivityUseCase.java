package Domain.UseCase;

import com.google.api.services.appsactivity.model.Activity;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.List;

public interface ActivityUseCase {
    /**
     * アクティビティ情報を取得し保存する。
     * @throws IOException 資格情報が読み込めなかった場合に発生
     * @throws GeneralSecurityException ログインユーザーに十分な資格がない場合に発生
     * @throws SQLException すでに保存しているアクティビティ情報取得時やログの保存時にエラーがあれば発生
     */
    void loadActivityLog() throws IOException, GeneralSecurityException, SQLException;

    /**
     * 保存しているアクティビティ情報を一覧取得する。
     * @return アクティビティリスト
     * @throws SQLException 保存しているログが取得できない場合に発生
     */
    List<Activity> getActivityLogs() throws SQLException;
}
