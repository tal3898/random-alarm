package learnenglish.taban.com.randomalarmmusic2;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import learnenglish.taban.com.randomalarmmusic2.activities.SnoozeAndStopAlarmActivity;
import learnenglish.taban.com.randomalarmmusic2.datamodels.AlarmSetState;
import learnenglish.taban.com.randomalarmmusic2.logger.ProgramLogger;
import learnenglish.taban.com.randomalarmmusic2.logger.UILogger;

public class RingtonePlayingService extends Service {

    private List<Integer> allSongs;
    private boolean isRunning;
    private Context context;
    MediaPlayer mMediaPlayer;
    private int startId;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        ProgramLogger.info("On bind in RingtonePlayingService");
        return null;
    }

    /**
     * The method gets a list of all the resources ids in raw
     * @return
     */
    public List<Integer> listRaw(){
        List<Integer> allResources = new ArrayList<>();
        Field[] fields=R.raw.class.getFields();
        for(int count=0; count < fields.length; count++){
            int mediaId = this.getResources().getIdentifier(fields[count].getName(),
                    "raw",
                    this.getPackageName());
            allResources.add(mediaId);
        }
        return allResources;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        allSongs = listRaw();
        context = AlarmApplication.getAppContext();

        Intent intent1 = new Intent(this.getApplicationContext(), SnoozeAndStopAlarmActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent1, 0);

        @SuppressLint("WrongConstant") Notification mNotify  = new Notification.Builder(context)
                .setContentTitle("Wake up man")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        AlarmSetState state = AlarmSetState.valueOf(intent.getExtras().getString("extra"));

        assert state != null;
        switch (state) {
            case STOP:
                startId = 0;
                break;
            case NEW_ALARM:
                startId = 1;
                break;
            case SNOOZE:
                startId = 1;
                break;
        }

        // There is no sound, and the user wants to start
        if(!this.isRunning && startId == 1) {
            Random r = new Random();
            int randomNumber = r.nextInt(allSongs.size());
            ProgramLogger.info("random number: " + randomNumber + ", song resource id: " + allSongs.get(randomNumber));

            mMediaPlayer = MediaPlayer.create(this, allSongs.get(randomNumber));
            mMediaPlayer.start();


            AlarmApplication.getNotificationManager().notify(0, mNotify);

            this.isRunning = true;
            this.startId = 0;

        }
        // There is no sound, and the user wants the end
        else if (!this.isRunning && startId == 0){
            this.isRunning = false;
            this.startId = 0;
        }
        // There is sound, and the user wants to start a sound
        else if (this.isRunning && startId == 1){
            this.isRunning = true;
            this.startId = 0;
        }
        // If there is sound, and the user wants to end
        else {
            mMediaPlayer.stop();
            mMediaPlayer.reset();

            this.isRunning = false;
            this.startId = 0;
        }
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        Log.e("JSLog", "on destroy called");
        super.onDestroy();

        this.isRunning = false;
    }







}
