package learnenglish.taban.com.randomalarmmusic2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class AlarmClockManager {
    private AlarmManager alarmManager;

    public AlarmManager getAlarmManager() {
        return alarmManager;
    }

    public void setAlarmManager(AlarmManager alarmManager) {
        this.alarmManager = alarmManager;
    }

    public void addAlarm(Integer hour, Integer minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 10);

        Log.e("MyActivity", "In the main with " + hour + " and " + minute);


        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        Intent myIntent = new Intent(AlarmApplication.getAppContext(), AlarmReceiver.class);

        myIntent.putExtra("extra", "yes");
        PendingIntent pending_intent = PendingIntent.getBroadcast(AlarmApplication.getAppContext(),
                0,
                myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);

    }
}
