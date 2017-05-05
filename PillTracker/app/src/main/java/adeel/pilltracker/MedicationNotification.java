package adeel.pilltracker;

import android.content.Intent;
import android.os.StrictMode;
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

        Intent notificationIntent = getIntent();
        String medicationName = notificationIntent.getStringExtra(EXTRA_ALARM_NAME);

        TextView mainText = (TextView) findViewById(R.id.notificationTextView);
        mainText.setText("It's time to take your " + medicationName);
    }
}
