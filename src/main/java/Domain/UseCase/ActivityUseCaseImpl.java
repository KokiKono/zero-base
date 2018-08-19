package Domain.UseCase;

import Domain.Repository.Activity.ActivityRepository;
import Domain.Repository.Activity.AuthorizeRepository;
import com.google.api.services.appsactivity.Appsactivity;
import com.google.api.services.appsactivity.model.Activity;
import com.google.api.services.appsactivity.model.ListActivitiesResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class ActivityUseCaseImpl implements ActivityUseCase{

    private AuthorizeRepository<Appsactivity> authorizeRepository;
    private ActivityRepository activityRepository;

    public ActivityUseCaseImpl(AuthorizeRepository<Appsactivity> authorizeRepository, ActivityRepository activityRepository) {
        this.authorizeRepository = authorizeRepository;
        this.activityRepository = activityRepository;
    }

    @Override
    public void loadActivityLog() throws IOException, GeneralSecurityException{
        Appsactivity service = this.authorizeRepository.getService();

        ListActivitiesResponse response = service.activities().list()
                .setSource("drive.google.com")
                .setDriveAncestorId("root")
                .setPageSize(10)
                .execute();

        List<Activity> activities = response.getActivities();

        if (activities == null || activities.size() == 0) return;

        this.activityRepository.saveActivityLogs(activities);
    }

    @Override
    public List<Activity> getActivityLogs() {
        return this.activityRepository.getActivityLogsFromSave();
    }
}
