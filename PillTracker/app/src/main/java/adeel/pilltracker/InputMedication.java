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
import android.widget.EditText;

import adeel.pilltracker.db.PillTrackerContract;
import adeel.pilltracker.db.PillTrackerDbHelper;

public class InputMedication extends AppCompatActivity {

    private PillTrackerDbHelper mDbHelper = new PillTrackerDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_medication);
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

        long newRowId = db.insert(PillTrackerContract.Medication.TABLE_NAME, null, values);

        /* Notify user that the operation was completd */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
    }
}
