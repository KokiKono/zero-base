package Domain.Repository.Activity;

import Data.DataStore.Authorize.AuthorizeDataStore;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.appsactivity.Appsactivity;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class AuthorizeRepositoryImpl implements AuthorizeRepository<Appsactivity>{
    private AuthorizeDataStore authorizeDataStore;

    public AuthorizeRepositoryImpl(AuthorizeDataStore authorizeDataStore){
        this.authorizeDataStore = authorizeDataStore;
    }

    @Override
    public Appsactivity getService() throws IOException, GeneralSecurityException{
        Credential credential = this.authorizeDataStore.login();

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Appsactivity.Builder(
                HTTP_TRANSPORT,
                this.authorizeDataStore.JSON_FACTORY,
                credential
                ).setApplicationName("zero-base")
                .build();
    }
}
