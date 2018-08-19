package Domain.Repository.Activity;

import com.google.api.client.auth.oauth2.Credential;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface AuthorizeRepository<Service> {
    Service getService() throws IOException, GeneralSecurityException;
}
