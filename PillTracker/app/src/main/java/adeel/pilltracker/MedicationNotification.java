package adeel.pilltracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MedicationNotification extends AppCompatActivity {

    public static final String EXTRA_ALARM_MODE = "adeel.pilltracker.EXTRA_ALARM_MODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_notification);
    }
}
