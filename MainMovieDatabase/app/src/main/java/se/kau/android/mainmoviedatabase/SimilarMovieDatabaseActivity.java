package se.kau.android.mainmoviedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SimilarMovieDatabaseActivity extends AppCompatActivity {
    private static final String TAG = "JSONMovie";

    private static final String MOVIEPREFS = "movieIDPreferences";
    private static final String KEY = "prefKey";
    private int mode = Activity.MODE_PRIVATE;

    private ArrayList<String> movieIDListArray;
    ListView lvSimilarMovieList;
    private ArrayAdapter adapter;
    private Intent intent;
    private DatabaseProvider dbp;
    String url;
    Button btnShortList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, " similarMovieList onCreate running");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_movie_database);
        lvSimilarMovieList = findViewById(R.id.lwSimilarMovieList);
        btnShortList = findViewById(R.id.btnShortList);
        dbp = new DatabaseProvider(this);

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        mySharedPreferences();
        movieIDListArray = new ArrayList<String>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject results = jsonArray.getJSONObject(i);
                                String title = results.getString("title");
                                movieIDListArray.add(title);
                            }

                            adapter = new ArrayAdapter<String>(SimilarMovieDatabaseActivity.this,
                                    android.R.layout.simple_list_item_1, movieIDListArray);
                            lvSimilarMovieList.setAdapter(adapter);

                            lvSimilarMovieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    insertToDatabase(movieIDListArray.get(i));
                                    // asy.execute(movieIDList.get(i));
                                    // finish();
                                    Log.d(TAG,"setOnItemClickListener movieIDList is showing: " + movieIDListArray.get(i));

                                    Toast.makeText(SimilarMovieDatabaseActivity.this, movieIDListArray.get(i) + " is saved in your shortlist!", Toast.LENGTH_LONG).show();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                        error.printStackTrace();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    public void mySharedPreferences(){
        SharedPreferences mySharedPreferences = getSharedPreferences(MOVIEPREFS, mode);
        String tmp = mySharedPreferences.getString(KEY, "");
        Log.d(TAG, "the movieInput: " + tmp);

        url = "https://api.themoviedb.org/3/movie/" + tmp + "/similar?api_key=b14813050c00ecdd2f664fd50d3d23e1&language=en-US&page=1";
        Log.d(TAG, "the URL: " + url);
    }

    public void shortListOnClick(View v){
        Log.d(TAG,"shortListOnClick button onclick");
        intent = new Intent(this, ShortListDatabaseActivity.class);
        startActivity(intent);

    }

    public void insertToDatabase(String s_name){
        Log.d(TAG,"DatabaseHandler insertToDatabase Running");

        SQLiteDatabase sd = dbp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseProvider.FIELD1, s_name);
        long result = sd.insert(DatabaseProvider.TABLE_NAME, null, values);
        Log.d(TAG, "DatabaseHandler insertToDatabase writing:" + result);
        Log.d(TAG,"insertToDatabase: " + s_name);
    }

}