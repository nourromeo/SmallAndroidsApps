package se.kau.android.similarartist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class SimilarArtistActivity extends AppCompatActivity {
    private static final String TAG = "SimilarArtist";

    private ListView lvSimilarArtist;
    private ArrayList<String> artistList;
    private String ArtistIn;
    private Button btnSearch;
    private EditText edtArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, " MainActivity onCreate running");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_artist);

        lvSimilarArtist = findViewById(R.id.lvSimilarArtist);
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "search button clicked");

                edtArtist = findViewById(R.id.autoCompleteTextView);
                ArtistIn = edtArtist.getText().toString().replace(' ', '+');
                closeKeyboard();

                RunningInBackgroud background = new RunningInBackgroud(getApplicationContext());
                background.execute();
            }
        });
    }

    class RunningInBackgroud extends AsyncTask<String, Void, ArrayList<String>> {
        private static final String TAG = "SimilarArtist";
        private Exception exception = null;
        private Context ctx;

        public RunningInBackgroud(Context ctxIn) {
            this.ctx = ctxIn;
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            Log.d(TAG, "doInBackground running");
            artistList = new ArrayList<String>();

            try {
                URL url = new URL("http://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&artist=" + ArtistIn + "&api_key=d3aec114f968321f03138df7fb4d2900");
                Log.d(TAG, "the URL: " + url);

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();

                parser.setInput(url.openStream(), null);
                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (parser.getName().equalsIgnoreCase("name")) {
                            artistList.add(parser.nextText());
                        }
                    }
                    eventType = parser.next();
                }

            } catch (XmlPullParserException | IOException e) {
                exception = e;
            }

            return artistList;
        }

        @Override
            protected void onPostExecute(ArrayList<String> s) {
                Log.d(TAG, "onPostExecute running: " + s);
                super.onPostExecute(s);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SimilarArtistActivity.this, android.R.layout.simple_list_item_1, artistList);
            lvSimilarArtist.setAdapter(adapter);
        }

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