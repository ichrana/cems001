package com.senr.ichra.dom011;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SplashScreen3 extends AppCompatActivity {

    boolean isSkiped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen3);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if(!isSkiped) {
                        Intent intent = new Intent(SplashScreen3.this, SplashScreen4.class);
                        startActivity(intent);
                    }
                }
            }
        };
        timerThread.start();
    }

    public void onClickButtonSkipss3(View view) {
        isSkiped = true;
        Intent intent = new Intent(SplashScreen3.this, LogOrSign.class);
        startActivity(intent);
        finish();

    }

}