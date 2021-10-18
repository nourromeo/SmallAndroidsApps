package se.kau.android.similarartist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "SimilarArtist";
    private Intent intent;

    public void onResume() {
        super.onResume();

        Log.d(TAG, "MainActivity onResume running");
        intent=getIntent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MainActivity onCreate running");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void StartClick(View v){
        Log.d(TAG,"SpelaClick button onclick");

        intent = new Intent(this, SimilarArtistActivity.class);
        startActivity(intent);

    }

}
