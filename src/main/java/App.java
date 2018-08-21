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

public class App {
    public static void main(String[] args) {
        try {
            AuthorizeDataStore authorizeDataStore
                    = new ActivityAuthorizeDataStoreImpl();
            AuthorizeRepository<Appsactivity> authorizeRepository
                    = new AuthorizeRepositoryImpl(authorizeDataStore);

            ActivityDataStore activityDataStore
                    = new ActivityDataStoreImpl(authorizeRepository.getService());
            ActivityRepository activityRepository
                    = new ActivityRepositoryImpl(activityDataStore);

            ActivityUseCase activityUseCase
                    = new ActivityUseCaseImpl(authorizeRepository, activityRepository);

            activityUseCase.loadActivityLog();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}