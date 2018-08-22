package Domain.Repository.Activity;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface AuthorizeRepository {
    <Service> Service getService() throws IOException, GeneralSecurityException;
}
