package Data.DataStore.Authorize;

import com.google.api.services.appsactivity.AppsactivityScopes;

import java.util.Collections;
import java.util.List;

public class ActivityAuthorizeDataStoreImpl extends AuthorizeDataStoreBaseImpl {
    @Override
    public List<String> getScopes() {
        return Collections.singletonList(AppsactivityScopes.ACTIVITY);
    }

    @Override
    public String getCredentialsPath() {
        return "/"+ACTIVITY_CREDENTIAL;
    }
}
