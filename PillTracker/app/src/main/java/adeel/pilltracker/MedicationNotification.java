package adeel.pilltracker;

import android.content.Intent;
import android.os.StrictMode;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MedicationNotification extends AppCompatActivity {

    public static final String EXTRA_ALARM_MODE = "adeel.pilltracker.EXTRA_ALARM_MODE";
    public static final String EXTRA_ALARM_ID = "adeel.pilltracker.EXTRA_ALARM_ID";
    public static final String EXTRA_ALARM_NAME = "adeel.pilltracker.EXTRA_ALARM_NAME";
    public static final String EXTRA_ALARM_TIME = "adeel.pilltracker.EXTRA_ALARM_TIME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_notification);

        // vibrate so the user knows what's up
        Vibrator vib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        vib.vibrate(1500);

        // display a message relevant to the medication in question
        Intent notificationIntent = getIntent();
        String medicationName = notificationIntent.getStringExtra(EXTRA_ALARM_NAME);
        TextView mainText = (TextView) findViewById(R.id.notificationTextView);
        mainText.setText("It's time to take your " + medicationName);
    }

    // TODO: handle pill-counting logic in this activity somehow.
    @Override
    protected void onStop(){
        super.onStop();
        updatePillCountAndResetAlarm();
    }

    private void updatePillCountAndResetAlarm() {
        /*needs to fire essentially the same intent as we did in InputMedication.saveMedication
        implication being that I should introduce a helper class and refactor so I don't have to
        copy pasta, but also that I need to update the intent that this activity gets sent*/
        Intent notificationIntent = getIntent();

        String medAlarmName = notificationIntent.getStringExtra(MedicationNotification.EXTRA_ALARM_NAME);
        int medAlarmID = notificationIntent.getIntExtra(MedicationNotification.EXTRA_ALARM_ID, 0);
        long medAlarmTimeUTC = notificationIntent.getLongExtra(MedicationNotification.EXTRA_ALARM_TIME, 0);

        Intent intent = new Intent(this, AlarmService.class);
        intent.putExtra(MedicationNotification.EXTRA_ALARM_NAME, medAlarmName);
        intent.putExtra(MedicationNotification.EXTRA_ALARM_ID, medAlarmID);
        intent.putExtra(MedicationNotification.EXTRA_ALARM_MODE, Alarm.ALARM_MODE_START);
        intent.putExtra(MedicationNotification.EXTRA_ALARM_TIME, medAlarmTimeUTC);
        startService(intent);
    }
}
