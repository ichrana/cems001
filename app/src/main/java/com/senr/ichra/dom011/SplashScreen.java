package com.senr.ichra.dom011;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    boolean isLoged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataSharedPre();
        if (isLoged) {
            Intent i = new Intent(SplashScreen.this, Main2Activity.class);
            startActivity(i);
            finish();
        } else {
                setContentView(R.layout.splash_screen);

            Thread timerThread = new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Intent intent = new Intent(SplashScreen.this, SplashScreen2.class);
                        startActivity(intent);
                    }
                }
            };
            timerThread.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void getDataSharedPre() {
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        isLoged = sharedPref.getBoolean("isLoged", false);
        Log.e("getDataSharedPre", " " + isLoged);
    }
}