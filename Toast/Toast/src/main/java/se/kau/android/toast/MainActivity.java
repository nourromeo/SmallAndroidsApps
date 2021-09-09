package se.kau.android.toast;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    final static String TAG ="click";
    Button clickButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickButton = findViewById(R.id.myButton);
    }

    public void clickMe(View v){
        Log.d(TAG, "Its clicking .. HELLO WORLD!");
        Toast.makeText(MainActivity.this, "HELLO WORLD!", Toast.LENGTH_SHORT).show();
    }
}