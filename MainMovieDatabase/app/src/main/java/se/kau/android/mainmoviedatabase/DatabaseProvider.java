package se.kau.android.mainmoviedatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseProvider extends SQLiteOpenHelper {
    private static final String TAG = "JSONMovie";

    private static final int DATABASE_VERSION = 3;
    static final String TABLE_NAME = "ShortList";
    private static final String DATABASE_NAME = "ShortListDB";
    static final String FIELD1 = "chosenMovie";
    private static final String TABLE_CREATE = "CREATE TABLE "
            + TABLE_NAME + " (" + FIELD1 + " TEXT);";

    DatabaseProvider(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"DatabaseProvider onCreate Running");
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
