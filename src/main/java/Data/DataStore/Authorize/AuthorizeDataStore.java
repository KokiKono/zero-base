package Data.DataStore.Authorize;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface AuthorizeDataStore {

    /**
     * ログイン後のトークン情報を格納するディレクトリ
     */
     String TOKENS_DIRECTORY_PATH = "tokens";
    /**
     * GoogleDriveActivityの資格情報ファイル名
     */
     String ACTIVITY_CREDENTIAL = "activity.json";

     JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * ログインをトライする
     * @return Credentialオブジェクト
     * @throws IOException 資格情報ファイルが読み込めなかった場合に発生
     * @throws GeneralSecurityException ログインユーザーに十分な資格がない
     */
    Credential login() throws IOException,GeneralSecurityException;

    /**
     * ログイントライ時のスコープ範囲を取得する。
     * @return 詳細は{@link com.google.api.services.appsactivity.AppsactivityScopes#ACTIVITY}
     */
    List<String> getScopes();

    String getCredentialsPath();
    File getTokensDirectory();

}
