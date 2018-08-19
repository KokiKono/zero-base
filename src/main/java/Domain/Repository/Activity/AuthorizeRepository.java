package Domain.Repository.Activity;

import com.google.api.client.auth.oauth2.Credential;

public interface AuthorizeRepository<Service> {
    Service getService();
}
