package in.abmulani.crossoverassignment;

import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.runtime.LicenseLevel;
import com.esri.core.runtime.LicenseResult;
import com.orm.SugarApp;
import com.orm.SugarContext;

import in.abmulani.crossoverassignment.databases.UserData;
import in.abmulani.crossoverassignment.utils.AppConstants;
import in.abmulani.crossoverassignment.utils.AppSharedPreference;
import timber.log.Timber;

public class BaseApplication extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        //all app level initialization should be done here


        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        SugarContext.init(this);

        //dummy initialization for SugarORM
        UserData userData = new UserData();

        AppSharedPreference preference = new AppSharedPreference(this);
        if (!preference.isDummyDataAdded()) {
            //stuff some dummy user data
            UserData.addDummyUserData();
            preference.setDummyDataAdded();
        }


        LicenseResult licenseResult = ArcGISRuntime.setClientId(AppConstants.ARC_CLIENT_ID);

        LicenseLevel licenseLevel = ArcGISRuntime.License.getLicenseLevel();

        if (licenseResult == LicenseResult.VALID && licenseLevel == LicenseLevel.BASIC) {
            Timber.d("ARC_CLIENT_ID validation successful");
        } else {
            Timber.e("INVALID ARC_CLIENT_ID");
        }

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
