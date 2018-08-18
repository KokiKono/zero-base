package Domain.UseCase.Authorize.Interface;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public interface AutorizeImpl {

    /**
     * ログイン後のトークン情報を格納するディレクトリ
     */
     String TOKENS_DIRECTORY_PATH = "tokens";
    /**
     * GoogleDriveActivityLogにアクセスするトークンを格納するディレクトリ
     */
     String ACTIVITY_PATH = "activities";
    /**
     * 資格情報を格納するディレクトリ
     */
     String CREDENTIALS_DIRECTORY_PATH = "credentials";
    /**
     * GoogleDriveActivityの資格情報ファイル名
     */
     String ACTIVITY_CREDENTIAL = "activity.json";
     JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * ログインをトライする
     * @param netHttpTransport
     * @return Credentialオブジェクト
     */
    Credential login(final NetHttpTransport HTTP_TRANSPORT) throws IOException;

    /**
     * ログイントライ時のスコープ範囲を取得する。
     * @return
     */
    List<String> getScopes();

    String getCredentialsPath();
    File getTokensDirectory();
}
