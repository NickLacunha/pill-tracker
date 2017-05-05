package adeel.pilltracker;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import adeel.pilltracker.db.PillTrackerContract;
import adeel.pilltracker.db.PillTrackerDbHelper;

public class InputMedication extends AppCompatActivity {

    private PillTrackerDbHelper mDbHelper = new PillTrackerDbHelper(this);

    private Alarm alarm = new Alarm();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_medication);

        /* programmatically set click event for the floating action button */
        Button saveButton = (Button) findViewById(R.id.submit_med_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMedication(view);
            }
        });
    }

    public void saveMedication(View view) {
        /* Initialize and get values from interface */
        String medicationName;
        String description;
        String capacity;
        String taken = "0";

        EditText medEdit = (EditText) findViewById(R.id.med_name_field);
        EditText descEdit = (EditText) findViewById(R.id.med_time_field);   // using the description field to hold the daily med time
        EditText capEdit = (EditText) findViewById(R.id.med_quantity_field);

        medicationName = medEdit.getText().toString();
        description = descEdit.getText().toString();
        capacity = capEdit.getText().toString();

        /* Database transaction */
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PillTrackerContract.Medication.COLUMN_NAME_MEDICATION_NAME, medicationName);
        values.put(PillTrackerContract.Medication.COLUMN_NAME_DESCRIPTION, description);
        values.put(PillTrackerContract.Medication.COLUMN_NAME_CAPACITY, capacity);
        values.put(PillTrackerContract.Medication.COLUMN_NAME_PILLS_TAKEN, taken);

        int newRowId = (int) db.insert(PillTrackerContract.Medication.TABLE_NAME, null, values);


        // from the time given (description), set the milliseconds-from-epoch for the first alarm to go off
        String[] timeParts = description.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timeParts[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));

        long setTime = calendar.getTimeInMillis();
        long currentTime = System.currentTimeMillis();
        if (setTime < currentTime) {
            setTime += 1000 * 60 * 60 *24;
        }

        calendar.setTimeInMillis(setTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String debugTime = sdf.format(calendar.getTime());

        /* Invoke the AlarmService to set a new alarm for this medication
        * The extras passed in tell the alarm service what medication to inform the user to take,
        * what ID to use to register the pending intent for the alarm (so it can be cancelled)
        * and what time the alarm should set for. The "Alarm mode" extra tells the AlarmService
        * that we are starting a new alarm.
        */
        Intent intent = new Intent(this, AlarmService.class);
        intent.putExtra(MedicationNotification.EXTRA_ALARM_NAME, medicationName);
        intent.putExtra(MedicationNotification.EXTRA_ALARM_ID, newRowId);
        intent.putExtra(MedicationNotification.EXTRA_ALARM_MODE, Alarm.ALARM_MODE_START);
        intent.putExtra(MedicationNotification.EXTRA_ALARM_TIME, setTime);
        startService(intent);

        /* Notify user that the operation was completd */
        AlertDialog.Builder builder = new AlertDialog.Builder(InputMedication.this);
        builder.setMessage(R.string.save_success_message)
                .setTitle(R.string.save_success_title);

        // finish the input medication activity after the dialog is closed
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
