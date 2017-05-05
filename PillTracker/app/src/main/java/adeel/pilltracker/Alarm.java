package adeel.pilltracker;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;


/* This is a subclass of BroadcastReceiver which provides an interface by which
 * the caller can set, cancel, or reset alarms for medications.
 */
public class Alarm extends BroadcastReceiver
{
    public static final String ALARM_MODE_START = "adeel.pilltracker.ALARM_MODE_START";
    public static final String ALARM_MODE_CANCEL = "adeel.pilltracker.ALARM_MODE_CANCEL";
    public static final String ALARM_MODE_RENEW = "adeel.pilltracker.ALARM_MODE_RENEW";

    public static final long DAY_MILLIS = (1000*60*60*24);

    @Override
    public void onReceive(Context context, Intent alarmIntent)
    {
        String medAlarmName = alarmIntent.getStringExtra(MedicationNotification.EXTRA_ALARM_NAME);
        int medAlarmID = alarmIntent.getIntExtra(MedicationNotification.EXTRA_ALARM_ID, 0);
        long medAlarmTimeUTC = alarmIntent.getLongExtra(MedicationNotification.EXTRA_ALARM_TIME, 0);

        Intent notificationIntent = new Intent(context, MedicationNotification.class);

        notificationIntent.putExtra(MedicationNotification.EXTRA_ALARM_NAME, medAlarmName);
        notificationIntent.putExtra(MedicationNotification.EXTRA_ALARM_TIME, medAlarmTimeUTC);
        notificationIntent.putExtra(MedicationNotification.EXTRA_ALARM_ID, medAlarmID);

        context.startActivity(notificationIntent);
    }

    /* Sets the medication alarm.
     * medAlarmName is the name of the medication
     * medAlarmTimeUTC is the time in milliseconds-from-epoch for the alarm to go off
     * medAlarmID is the _ID of the medication in the database.
     */
    public void setAlarm(Context context, String medAlarmName, long medAlarmTimeUTC, int medAlarmID)
    {
        int ID = medAlarmID;
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        PendingIntent pi;

        // when the alarm goes off, it will need to be reset and the med count will need to be updated
        i.putExtra(MedicationNotification.EXTRA_ALARM_NAME, medAlarmName);
        i.putExtra(MedicationNotification.EXTRA_ALARM_ID, medAlarmID);
        i.putExtra(MedicationNotification.EXTRA_ALARM_TIME, medAlarmID + DAY_MILLIS );  // advance the alarm time by a day

        // set pendingintent requestcode to the ID of the medication in the database so we can
        // easily cancel the alarm later if we need to.
        pi = PendingIntent.getBroadcast(context, ID, i, 0);

        am.setExact(AlarmManager.RTC_WAKEUP, medAlarmTimeUTC, pi); // Millisec * Second * Minute
    }

    // TODO: Add parameters to this method and replace with our application logic
    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarm.class);
        // pendingintent id should be the same as medication id
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    // TODO: add a method called "resetAlarm" that combines the setAlarm and cancelAlarm
    // do this after the parameters for those two features are done.
}