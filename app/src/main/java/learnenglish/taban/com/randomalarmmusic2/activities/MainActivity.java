package learnenglish.taban.com.randomalarmmusic2.activities;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Random;

import learnenglish.taban.com.randomalarmmusic2.AlarmApplication;
import learnenglish.taban.com.randomalarmmusic2.AlarmReceiver;
import learnenglish.taban.com.randomalarmmusic2.R;
import learnenglish.taban.com.randomalarmmusic2.datamodels.AlarmSetState;
import learnenglish.taban.com.randomalarmmusic2.logger.ProgramLogger;
import learnenglish.taban.com.randomalarmmusic2.logger.UILogger;

public class MainActivity extends AppCompatActivity {

    private TimePicker alarmTimePicker;
    private TextView alarmTextView;

    private AlarmReceiver alarm;

    MainActivity inst;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlarmApplication.getNotificationManager().cancelAll();

        this.context = this;

        //alarm = new AlarmReceiver();
        alarmTextView = (TextView) findViewById(R.id.alarmText);

        final Intent myIntent = new Intent(this.context, AlarmReceiver.class);

        // Get the alarm manager service
        AlarmApplication.getAlarmClockApplication().setAlarmManager((AlarmManager) getSystemService(ALARM_SERVICE));

        // set the alarm to the time that you picked
        //final Calendar calendar = Calendar.getInstance();

        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);



        Button start_alarm= (Button) findViewById(R.id.start_alarm);
        start_alarm.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)

            @Override
            public void onClick(View v) {
                UILogger.info("Clicked the start alarm button");
                final int hour = alarmTimePicker.getCurrentHour();
                final int minute = alarmTimePicker.getCurrentMinute();;
                AlarmApplication.getAlarmClockApplication().addAlarm(hour, minute, 10);

            }

        });

        Button stop_alarm= (Button) findViewById(R.id.stop_alarm);
        stop_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UILogger.info("Clicked the stop alarm button");
                myIntent.putExtra("extra", AlarmSetState.STOP.toString());
                sendBroadcast(myIntent);
            }
        });

    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }



    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
