import Apps.ActivityLogBuilder;
import Data.DataStore.Activity.ActivityDataStore;
import Data.DataStore.Activity.ActivityDataStoreImpl;
import Data.DataStore.Authorize.ActivityAuthorizeDataStoreImpl;
import Data.DataStore.Authorize.AuthorizeDataStore;
import Domain.Repository.Activity.ActivityRepository;
import Domain.Repository.Activity.ActivityRepositoryImpl;
import Domain.Repository.Activity.AuthorizeRepository;
import Domain.Repository.Activity.AuthorizeRepositoryImpl;
import Domain.UseCase.ActivityUseCase;
import Domain.UseCase.ActivityUseCaseImpl;
import com.google.api.services.appsactivity.Appsactivity;

public class Main {
    public static void main(String[] args) {
        ActivityLogBuilder builder = new ActivityLogBuilder() {
            @Override
            public AuthorizeDataStore getAuthorizeDataStore() {
                return new ActivityAuthorizeDataStoreImpl();
            }

            @Override
            public AuthorizeRepository getAuthorizeRepository(
                AuthorizeDataStore authorizeDataStore) {
                return new AuthorizeRepositoryImpl(authorizeDataStore);
            }

            @Override
            public ActivityDataStore getActivityDataStore(Object serviceObj) {
                return new ActivityDataStoreImpl((Appsactivity) serviceObj);
            }

            @Override
            public ActivityRepository getActivityRepository(ActivityDataStore activityDataStore) {
                return new ActivityRepositoryImpl(activityDataStore);
            }

            @Override
            public ActivityUseCase getActivityUseCase(AuthorizeRepository authorizeRepository,
                ActivityRepository activityRepository) {
                return new ActivityUseCaseImpl(authorizeRepository, activityRepository);
            }
        };

        builder.build();
    }
}
