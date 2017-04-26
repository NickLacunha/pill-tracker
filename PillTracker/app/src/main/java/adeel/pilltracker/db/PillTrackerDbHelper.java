package adeel.pilltracker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created on 4/22/2017.
 */

public class PillTrackerDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PillTracker.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PillTrackerContract.Medication.TABLE_NAME + " (" +
                    PillTrackerContract.Medication._ID + " INTEGER PRIMARY KEY," +
                    PillTrackerContract.Medication.COLUMN_NAME_MEDICATION_NAME + " TEXT," +
                    PillTrackerContract.Medication.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    PillTrackerContract.Medication.COLUMN_NAME_CAPACITY + " INTEGER," +
                    PillTrackerContract.Medication.COLUMN_NAME_PILLS_TAKEN + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PillTrackerContract.Medication.TABLE_NAME;

    public PillTrackerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onCreate(db);
    }
}
