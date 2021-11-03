package se.kau.android.mainmoviedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShortListDatabaseActivity extends AppCompatActivity {
    private static final String TAG = "JSONMovie";

    private DatabaseProvider dbp;
    ListView lvShortList;
    private ArrayList<String> shortListArray;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, " ShortListDatabaseActivity onCreate running");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_list_database);
        dbp = new DatabaseProvider(this);
        lvShortList = findViewById(R.id.lwShortList);

        shortListArray = new ArrayList<String>();

        provideDatabaseToShortList();

        adapter = new ArrayAdapter<String>(ShortListDatabaseActivity.this,
                android.R.layout.simple_list_item_1, shortListArray);
        lvShortList.setAdapter(adapter);
    }

    public void provideDatabaseToShortList() {
        SQLiteDatabase sqLiteDatabase = dbp.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseProvider.TABLE_NAME, null)) {
            if(result.getCount() != 0) {
                while (result.moveToNext()) {
                    shortListArray.add(result.getString(0));
                }
                Log.d(TAG, " ShortListDatabaseActivity provideDatabaseToShortList shortListArray: " + shortListArray);

                result.close();
            }
        }
    }
}