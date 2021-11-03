package se.kau.android.mainmoviedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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

public class MovieDatabaseActivity extends AppCompatActivity {
    private static final String TAG = "JSONMovie";

    private static final String MOVIEPREFS = "movieIDPreferences";
    private static final String KEY = "prefKey";
    private int mode = Activity.MODE_PRIVATE;
    private RequestQueue requestQueue;

    private ArrayList<String> movieListArray; // list of movies
    private ArrayList<String> movieIDList; // list of the ID of the movies
    private ArrayAdapter adapter;
    private EditText userMovieInput; // input of the user
    private ListView lvMovieList; // list view of the movie
    private Button btnSearch;
    private Intent intent;

    public void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity onResume running");
        intent=getIntent();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, " MainActivity onCreate running");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_database);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        userMovieInput =findViewById(R.id.txtMovieInput);
        btnSearch=findViewById(R.id.btnSearch);
        lvMovieList = findViewById(R.id.lvMovieList);

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "search button clicked");
                backgroundRunner background = new backgroundRunner(getApplicationContext());
                background.execute();

                closeKeyboard();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        requestQueue.stop();
    }




    /************************************* AsyncTask klass ***************************************/

    class backgroundRunner extends AsyncTask<Void, Void, Void> {
        private static final String TAG = "JSONMovie";

        ProgressDialog progressDialog = new ProgressDialog(MovieDatabaseActivity.this);
        private Context ctx;

        public backgroundRunner(Context ctxIn) {
            this.ctx = ctxIn;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading, please wait...");
            progressDialog.show();

            mySharedPreferences();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground running");

            String url = "https://api.themoviedb.org/3/search/movie?api_key=b14813050c00ecdd2f664fd50d3d23e1&language=en-US&query="
                    + userMovieInput.getText() + "&include_adult=false";
            Log.d(TAG, "the URL: " + url);
            Log.d(TAG, "the movieInput: " + userMovieInput.getText());

            movieListArray = new ArrayList<String>();
            movieIDList = new ArrayList<String>();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("results");

                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject results = jsonArray.getJSONObject(i);
                                    String title = results.getString("title");
                                    String id = results.getString("id");

                                    movieListArray.add((i+1) + ": " + title);
                                    movieIDList.add(id);
                                }
                                Log.d(TAG, "the movieList is: " + movieListArray);

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

            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            Log.d(TAG, "onPostExecute running");
            super.onPostExecute(s);
            adapter = new ArrayAdapter<String>(MovieDatabaseActivity.this,
                    android.R.layout.simple_list_item_1, movieListArray);
            lvMovieList.setAdapter(adapter);

            progressDialog.dismiss();
        }
    }

    public void mySharedPreferences(){
        lvMovieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onPreExecute()onItemClick running");
                SharedPreferences mySharedPreferences =getSharedPreferences(MOVIEPREFS, mode);
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putString(KEY, movieIDList.get(i));
                Log.d(TAG, "MOVIEPREFS: " + movieIDList.get(i));

                editor.apply();
                intent = new Intent(MovieDatabaseActivity.this, SimilarMovieDatabaseActivity.class);
                startActivity(intent);
            }
        });
    }

    // slutligen är denna metoden, jag fick den från https://www.youtube.com/watch?v=CW5Xekqfx3I
    // det är inte viktig men jag ville lära mig hur man kan stänga keyboard :)
    private void closeKeyboard(){
        View v = this.getCurrentFocus();
        if(v != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}