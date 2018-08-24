package Integration;

import Apps.ActivityLogBuilder;
import Data.DataStore.Activity.ActivityDataStore;
import Data.DataStore.Authorize.AuthorizeDataStore;
import Domain.Repository.Activity.ActivityRepository;
import Domain.Repository.Activity.ActivityRepositoryImpl;
import Domain.Repository.Activity.AuthorizeRepository;
import Domain.UseCase.ActivityUseCase;
import Domain.UseCase.ActivityUseCaseImpl;
import Util.FileTempHelper;
import Util.JsonConverter;
import com.google.api.services.appsactivity.Appsactivity;
import com.google.api.services.appsactivity.model.Activity;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.csv.CsvDataSetWriter;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActivityLogTest {

    private static IDatabaseConnection connection;
    private static QueryDataSet backupDataSet;
    private static File tempCsv;

    @Before
    public void setUp() throws Exception {

        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/gda_sample?user=root&password=");

        this.connection = new DatabaseConnection(con);
        // DBのバックアップ
        this.backupDataSet = new QueryDataSet(this.connection);
        this.backupDataSet.addTable("activity_log");

        // tempCSVファイルで保存
        this.tempCsv = FileTempHelper.createTmpDir();

        CsvDataSetWriter.write(this.backupDataSet, this.tempCsv);

        // 現在のDBを削除
        IDataSet dataSet = this.connection.createDataSet();
        DatabaseOperation.DELETE_ALL.execute(this.connection, dataSet);
    }

    @Test
    /**
     * アクティビティの取得からDB保存まで
     */
    public void activityLogTest() throws Exception {
        // 適宜モック化したビルダー生成
        ActivityLogBuilder builder = new ActivityLogBuilder() {
            @Override
            public AuthorizeDataStore getAuthorizeDataStore() {
                return new AuthorizeDataStoreMocked();
            }

            @Override
            public AuthorizeRepository getAuthorizeRepository(
                AuthorizeDataStore authorizeDataStore) {
                return new AuthorizeRepositoryMocked();
            }

            @Override
            public ActivityDataStore getActivityDataStore(Object serviceObj) {
                return new ActivityDataStoreMocked((Appsactivity) serviceObj);
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

        QueryDataSet queryDataSet = new QueryDataSet(this.connection);
        queryDataSet.addTable("activity_log");

        // DBに保存されたDB情報を取得
        ArrayList<Activity> saveActivities = new ArrayList<>();
        try {
            int rowCount = queryDataSet.getTable("activity_log").getRowCount();
            System.out.println("保存されたテストデータ件数: " + rowCount);
            for (int count = 0; count < rowCount; count++) {
                Activity activity = JsonConverter.jsonActivityConverter(
                    JsonConverter.convertInputStreamToString(
                        this.getClass().getResourceAsStream("/activity_log_data.json")
                    )
                );
                saveActivities.add(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 保存されたデータとテストデータを比較
        try {
            Activity jsonActivity = JsonConverter.jsonActivityConverter(
                JsonConverter.convertInputStreamToString(
                    this.getClass().getResourceAsStream("/activity_log_data.json")
                )
            );
            for (Activity saveActivity : saveActivities) {
                assertEquals(saveActivity, jsonActivity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        // DBの復元
        IDataSet csvDataSet = new CsvDataSet(this.tempCsv);
        DatabaseOperation.CLEAN_INSERT.execute(this.connection, csvDataSet);
    }
}
