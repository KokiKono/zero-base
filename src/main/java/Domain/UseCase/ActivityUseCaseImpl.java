package Domain.UseCase;

import Domain.Repository.Activity.ActivityRepository;
import Domain.Repository.Activity.AuthorizeRepository;
import com.google.api.services.appsactivity.Appsactivity;
import com.google.api.services.appsactivity.model.Activity;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.List;

public class ActivityUseCaseImpl implements ActivityUseCase{

    private AuthorizeRepository<Appsactivity> authorizeRepository;
    private ActivityRepository activityRepository;

    public ActivityUseCaseImpl(AuthorizeRepository<Appsactivity> authorizeRepository, ActivityRepository activityRepository) {
        this.authorizeRepository = authorizeRepository;
        this.activityRepository = activityRepository;
    }

    @Override
    public void loadActivityLog() throws IOException, GeneralSecurityException, SQLException{
        Appsactivity service = this.authorizeRepository.getService();

        List<Activity> activities = this.activityRepository.getActivityLogsFromAPI(service);

        this.activityRepository.saveActivityLogs(activities);

    }

    @Override
    public List<Activity> getActivityLogs()throws SQLException {
        return this.activityRepository.getActivityLogsFromSave();
    }
}
