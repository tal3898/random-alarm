package learnenglish.taban.com.randomalarmmusic2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

import learnenglish.taban.com.randomalarmmusic2.datamodels.AlarmSetState;
import learnenglish.taban.com.randomalarmmusic2.logger.ProgramLogger;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getExtras().getString("extra");
        AlarmSetState stateEnum = AlarmSetState.valueOf(state);
        ProgramLogger.info("In the reciever with extra data: " + state);

        Intent serviceIntent = new Intent(context,RingtonePlayingService.class);
        serviceIntent.putExtra("extra", state);

        context.startService(serviceIntent);

        // If the user created a new alarm (and it is the first call), or it is
        // an old alarm which is ringing, set new alarm in this hour for the next day
        if (stateEnum == AlarmSetState.NEW_ALARM ||
                stateEnum == AlarmSetState.OLD_ALARM_REPEAT) {
            ProgramLogger.info("Setting a new alarm clock for the next day");
            Date nowDate = new Date();
            AlarmApplication.getAlarmClockManager().addTemporaryAlarm(nowDate.getHours(), nowDate.getMinutes() + 1,
                    0, AlarmSetState.OLD_ALARM_REPEAT);
        }
    }
}
