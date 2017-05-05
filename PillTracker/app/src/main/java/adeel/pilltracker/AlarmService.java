package adeel.pilltracker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService extends Service
{
    Alarm alarm = new Alarm();
    public void onCreate()
    {
        super.onCreate();
    }

    /* TODO: take information from intent and use it to
    *****determine what alarm method to call
    *****pass parameters to that method
    * */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        String alarmMode = intent.getStringExtra(MedicationNotification.EXTRA_ALARM_MODE);

        /* TODO: Fill this out
        switch(alarmMode){


        }
        */

        alarm.setAlarm(this);
        return START_STICKY;
    }

    /*
    @Override
    public void onStart(Intent intent, int startId)
    {
        alarm.setAlarm(this);
    }*/

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}