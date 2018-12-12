package learnenglish.taban.com.randomalarmmusic2;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.time.LocalDateTime;
import java.util.Date;

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
        Log.i("TAL", "snoozed the alarm");
        //LocalDateTime nowTime = LocalDateTime.now();
        Date date = new Date();

        // Stop the alarm
        Intent myIntent = new Intent(SnoozeAndStopAlarmActivity.this, AlarmReceiver.class);
        myIntent.putExtra("extra", "no");
        sendBroadcast(myIntent);

        Log.i("TAL", "snoozed to " + date.getHours() + ":" + date.getMinutes() + ":" + SNOOZE_MINUTES_INTERVAL+5);

        // Add a snooze
        AlarmApplication.getAlarmClockApplication()
                .addAlarm(date.getHours(), date.getMinutes(),SNOOZE_MINUTES_INTERVAL+5);


    }
}
