package learnenglish.taban.com.randomalarmmusic2.activities;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Date;

import learnenglish.taban.com.randomalarmmusic2.AlarmApplication;
import learnenglish.taban.com.randomalarmmusic2.AlarmReceiver;
import learnenglish.taban.com.randomalarmmusic2.R;
import learnenglish.taban.com.randomalarmmusic2.datamodels.AlarmSetState;
import learnenglish.taban.com.randomalarmmusic2.logger.UILogger;

public class SnoozeAndStopAlarmActivity extends AppCompatActivity {

    private static final int SNOOZE_MINUTES_INTERVAL = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snooze_and_stop_alarm);

        AlarmApplication.getAlarmClockApplication().setAlarmManager((AlarmManager) getSystemService(ALARM_SERVICE));

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onSnoozeAlarm(View view) {
        //LocalDateTime nowTime = LocalDateTime.now();
        Date date = new Date();

        UILogger.info("snoozed to " + date.getHours() + ":" + date.getMinutes() + ":" + SNOOZE_MINUTES_INTERVAL+5);

        // Stop the alarm
        Intent myIntent = new Intent(SnoozeAndStopAlarmActivity.this, AlarmReceiver.class);
        myIntent.putExtra("extra", AlarmSetState.SNOOZE.toString());
        sendBroadcast(myIntent);

        // Add a snooze
        AlarmApplication.getAlarmClockApplication()
                .addAlarm(date.getHours(), date.getMinutes(),SNOOZE_MINUTES_INTERVAL+5);

        // Exit the app
        finish();
        System.exit(0);

    }

    public void onStopAlarm(View view) {
        UILogger.info("Stopped the alarm clock");

        // Stop the alarm
        Intent myIntent = new Intent(SnoozeAndStopAlarmActivity.this, AlarmReceiver.class);
        myIntent.putExtra("extra", AlarmSetState.STOP.toString());

        sendBroadcast(myIntent);

        // Exit the app
        finish();
        System.exit(0);
    }
}
