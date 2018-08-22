package Domain.UseCase;

import com.google.api.services.drive.model.File;
import java.util.List;

public interface DriveUseCase {
    void loadDriveBackup();
    void restoreFile();
    List<File> getAllBackupFiles();
}
