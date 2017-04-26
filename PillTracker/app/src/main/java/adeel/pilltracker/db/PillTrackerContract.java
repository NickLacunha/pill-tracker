package adeel.pilltracker.db;

import android.provider.BaseColumns;

/**
 * Created on 4/22/2017.
 */

public final class PillTrackerContract {

    private PillTrackerContract () {}

    public static class Medication implements BaseColumns {
        // the names of the columns in the Medication table will go here as static final variables
        // Medicine
        // Description
        // Capacity
        // Number of pills taken
        public static final String TABLE_NAME = "medication";
        public static final String COLUMN_NAME_MEDICATION_NAME = "medication_name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_CAPACITY = "capacitiy";
        public static final String COLUMN_NAME_PILLS_TAKEN = "pills_taken";
    }
}
