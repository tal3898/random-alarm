package learnenglish.taban.com.randomalarmmusic2;

import android.app.Application;
import android.content.Context;

public class AlarmApplication extends Application {
    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        AlarmApplication.applicationContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return applicationContext;
    }
}
