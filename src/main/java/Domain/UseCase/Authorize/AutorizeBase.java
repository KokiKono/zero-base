package Domain.UseCase.Authorize;

import Domain.UseCase.Authorize.Interface.AutorizeImpl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class AutorizeBase implements AutorizeImpl{
    @Override
    public Credential login(NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // 資格情報ファイルの取得
        InputStream inputStream
                = Activity.class.getResourceAsStream(getCredentialsPath());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(inputStream));

        // フローを構築し、ユーザー認証要求をトリガーする。
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, getScopes())
                .setDataStoreFactory(new FileDataStoreFactory(getTokensDirectory()))
                .setAccessType("offline")
                .build();
        Credential credential
                = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
                .authorize("user");
        return credential;
    }

    @Override
    public File getTokensDirectory() {
        return new File(TOKENS_DIRECTORY_PATH);
    }
}
