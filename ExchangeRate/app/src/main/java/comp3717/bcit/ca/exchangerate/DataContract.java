package comp3717.bcit.ca.exchangerate;

import android.provider.BaseColumns;


public final class DataContract
{
    private DataContract()
    {
    }

    public static abstract class DataEntry
        implements BaseColumns
    {
        public static final String TABLE_NAME = "data";
        public static final String COLUMN_NAME_VALUE = "value";
    }
}