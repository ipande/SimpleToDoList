package codepath.ui.com.codepath.todolist.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoItemDatabase extends SQLiteOpenHelper{
    private static final String COL_ID = "id";
    private static final String COL_POS = "pos";
    private static final String COL_TEXT = "text";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Constants.TABLE_NAME + " (" +
                    COL_ID + "INTEGER PRIMARY KEY," +
                    COL_POS + "INTEGER " + COMMA_SEP +
                    COL_TEXT + "TEXT" + COMMA_SEP + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Constants.TABLE_NAME;

    public TodoItemDatabase(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
