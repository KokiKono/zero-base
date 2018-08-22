package Apps;

import Data.DataStore.Activity.ActivityDataStore;
import Data.DataStore.Authorize.AuthorizeDataStore;
import Domain.Repository.Activity.ActivityRepository;
import Domain.Repository.Activity.AuthorizeRepository;
import Domain.UseCase.ActivityUseCase;

//TODO この実装で正しいのかテストコードを書いたあとに再考する

interface ActivityLogBuilderInterface {
    AuthorizeDataStore getAuthorizeDataStore();
    AuthorizeRepository getAuthorizeRepository(AuthorizeDataStore authorizeDataStore);
    ActivityDataStore getActivityDataStore(Object serviceObj);
    ActivityRepository getActivityRepository(ActivityDataStore activityDataStore);
    ActivityUseCase getActivityUseCase(AuthorizeRepository authorizeRepository, ActivityRepository activityRepository);
}

public abstract class ActivityLogBuilder implements ActivityLogBuilderInterface{

    public  void build() {
        try {

            AuthorizeDataStore authorizeDataStore
                = getAuthorizeDataStore();
            AuthorizeRepository authorizeRepository
                = getAuthorizeRepository(authorizeDataStore);

            ActivityDataStore activityDataStore
                = getActivityDataStore(authorizeRepository.getService());
            ActivityRepository activityRepository
                = getActivityRepository(activityDataStore);

            ActivityUseCase activityUseCase
                = getActivityUseCase(authorizeRepository, activityRepository);

            activityUseCase.loadActivityLog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}