package se.kau.android.locationcount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "Location";
    private Intent intent;

    public void onResume() {
        super.onResume();

        Log.d(TAG, "MainActivity onResume running");
        intent=getIntent();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void pClick(View view) {
        Log.d(TAG,"pClick button onclick");

        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}