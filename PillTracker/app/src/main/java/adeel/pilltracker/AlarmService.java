package adeel.pilltracker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

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
        Log.i("AlarmService", "Alarm service invoked");
        String medAlarmName = "";
        String tempTime;
        long medAlarmTimeUTC = 0;
        int medAlarmID = 0;

        String alarmMode = intent.getStringExtra(MedicationNotification.EXTRA_ALARM_MODE);


        switch(alarmMode){
            case Alarm.ALARM_MODE_START:
                Log.i("AlarmService", "Starting an alarm.");
                medAlarmName = intent.getStringExtra(MedicationNotification.EXTRA_ALARM_NAME);
                medAlarmTimeUTC = intent.getLongExtra(MedicationNotification.EXTRA_ALARM_TIME, 0);
                medAlarmID = intent.getIntExtra(MedicationNotification.EXTRA_ALARM_ID, 0);
                Log.i("AlarmService", "Got the params");
                alarm.setAlarm(this, medAlarmName, medAlarmTimeUTC, medAlarmID);
                break;
            case Alarm.ALARM_MODE_CANCEL:
                /* TODO: get parameters and call alarm.cancelAlarm */
                break;
            case Alarm.ALARM_MODE_RENEW:
                /* TODO: get parameters and call alarm.renewAlarm */
                break;
        }

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