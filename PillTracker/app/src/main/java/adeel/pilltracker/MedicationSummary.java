package adeel.pilltracker;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import adeel.pilltracker.db.PillTrackerContract;
import adeel.pilltracker.db.PillTrackerDbHelper;

public class MedicationSummary extends ListActivity {

    ListView mListView;
    SimpleCursorAdapter mAdapter;

    /* largely auto-generated code */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        /* programmatically set click event for the floating action button */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputMedication(view);
            }
        });

        // pull the list of medications
        Cursor medCursor = readMedicationData();

        // set up a CursorAdapter
        String[] fromColumns = {
                PillTrackerContract.Medication.COLUMN_NAME_MEDICATION_NAME
        };
        int[] toViews = {
                R.id.item_medication_list
        };

        mAdapter = new SimpleCursorAdapter(this,
                R.layout.item_medication_list, medCursor, fromColumns, toViews, 0);
        mListView = getListView();
        mListView.setAdapter(mAdapter);

    }

    @Override
    /* whenever the activity is brought back to the foreground, there might be a new or deleted
        medication so we should update the list.
    */
    public void onResume(){
        super.onResume();
        /*mAdapter.notifyDataSetChanged();*/
        Cursor medCursor = readMedicationData();
        mAdapter.changeCursor(medCursor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medication_summary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* used as the click listener for the floating action button,
     * opens the input medication activity
     */
    public void inputMedication(View view){
        Intent intent = new Intent(this, InputMedication.class);
        startActivity(intent);
    }

    /* read a list of medications from the db and return a Cursor object.
       That Cursor will get passed to a SimpleCursorAdapter and then a ListView
       to generate the medication summary for this activity
     */
    public Cursor readMedicationData(){
        PillTrackerDbHelper dbHelper = new PillTrackerDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        // for a medication summary screen we only need to have the medication name,
        // but we want the primary key as well so we can send it to the MedicationDetails activity
        String[] projection = {
                PillTrackerContract.Medication._ID,
                PillTrackerContract.Medication.COLUMN_NAME_MEDICATION_NAME
        };

        // we don't need any filters because we want the whole list of medications
        String selection = "";
        String[] selectionArgs = {};

        // we also don't care about the sort order right now
        String sortOrder = "";

        return db.query(
                PillTrackerContract.Medication.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
    }
}
