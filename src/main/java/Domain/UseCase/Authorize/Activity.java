package Domain.UseCase.Authorize;

import com.google.api.services.appsactivity.AppsactivityScopes;

import java.util.Collections;
import java.util.List;

public class Activity extends AutorizeBase{
    @Override
    public List<String> getScopes() {
        return Collections.singletonList(AppsactivityScopes.ACTIVITY);
    }

    @Override
    public String getCredentialsPath() {
        return ACTIVITY_PATH + ACTIVITY_CREDENTIAL;
    }
}
