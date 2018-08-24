package Integration;

import Domain.Repository.Activity.AuthorizeRepository;
import com.google.api.services.appsactivity.Appsactivity;
import java.io.IOException;
import java.security.GeneralSecurityException;
import mockit.Mocked;

public class AuthorizeRepositoryMocked implements AuthorizeRepository{

    @Mocked
    Appsactivity service;

    @Override
    public Appsactivity getService() throws IOException, GeneralSecurityException {
        return service;
    }
}
