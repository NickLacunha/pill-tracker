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

    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        // TODO: Replace with appropriate message (probably set by an extra in the intent)

        Toast.makeText(context, "Alarm !!!!!!!!!!", Toast.LENGTH_LONG).show(); // For example

        // TODO: Add med-counting logic

        wl.release();
    }

    /* Sets the medication alarm.
     * medAlarmName is the name of the medication
     * medAlarmTimeUTC is the time in milliseconds-from-epoch for the alarm to go off
     * medAlarmID is the _ID of the medication in the database.
     */
    public void setAlarm(Context context, String medAlarmName, long medAlarmTimeUTC, int medAlarmID)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        PendingIntent pi;

        // put the message extra into the intent
        i.putExtra(MedicationNotification.EXTRA_ALARM_NAME, medAlarmName);

        // set pendingintent requestcode to the ID of the medication in the database so we can
        // easily cancel the alarm later if we need to.
        pi = PendingIntent.getBroadcast(context, medAlarmID, i, 0);

        am.set(AlarmManager.RTC_WAKEUP, medAlarmTimeUTC, pi); // Millisec * Second * Minute
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