package learnenglish.taban.com.randomalarmmusic2;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;

public class AlarmApplication extends Application {
    private static Context applicationContext;
    private static NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        AlarmApplication.applicationContext = getApplicationContext();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public static NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public static Context getAppContext() {
        return applicationContext;
    }
}
