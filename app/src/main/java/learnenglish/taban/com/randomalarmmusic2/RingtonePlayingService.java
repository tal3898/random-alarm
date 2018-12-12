package learnenglish.taban.com.randomalarmmusic2;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
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

public class RingtonePlayingService extends Service {

    private List<Integer> allSongs;
    private boolean isRunning;
    private Context context;
    MediaPlayer mMediaPlayer;
    private int startId;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MyActivity", "In the Richard service");
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
            Log.i("Raw Asset: ", fields[count].getName());
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

        String state = intent.getExtras().getString("extra");

        Log.e("what is going on here  ", state);

        assert state != null;
        switch (state) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }


        if(!this.isRunning && startId == 1) {
            Log.e("if there was not sound ", " and you want start");

            Random r = new Random();
            int random_number = r.nextInt(allSongs.size());
            Log.e("random number is ", String.valueOf(random_number));

            mMediaPlayer = MediaPlayer.create(this, allSongs.get(random_number));
            mMediaPlayer.start();


            AlarmApplication.getNotificationManager().notify(0, mNotify);

            this.isRunning = true;
            this.startId = 0;

        }
        else if (!this.isRunning && startId == 0){
            Log.e("if there was not sound ", " and you want end");

            this.isRunning = false;
            this.startId = 0;

        }

        else if (this.isRunning && startId == 1){
            Log.e("if there is sound ", " and you want start");

            this.isRunning = true;
            this.startId = 0;

        }
        else {
            Log.e("if there is sound ", " and you want end");

            mMediaPlayer.stop();
            mMediaPlayer.reset();

            this.isRunning = false;
            this.startId = 0;
        }


        Log.e("MyActivity", "In the service");

        return START_NOT_STICKY;

    }


    @Override
    public void onDestroy() {
        Log.e("JSLog", "on destroy called");
        super.onDestroy();

        this.isRunning = false;
    }







}
