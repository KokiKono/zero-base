package Integration;

import Data.DataStore.Authorize.AuthorizeDataStore;
import com.google.api.client.auth.oauth2.Credential;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import mockit.Deencapsulation;
import mockit.Mocked;

public class AuthorizeDataStoreMocked implements AuthorizeDataStore{

    @Mocked
    private Credential credentialMocked;
    @Mocked
    private File tokenDirectory;

    @Override
    public Credential login() throws IOException, GeneralSecurityException {
        return this.credentialMocked;
    }

    @Override
    public List<String> getScopes() {
        ArrayList<String> list = new ArrayList<>();
        list.add("testScope");
        return list;
    }

    @Override
    public String getCredentialsPath() {
        return "test/credentials_path";
    }

    @Override
    public File getTokensDirectory() {
        return this.tokenDirectory;
    }
}
