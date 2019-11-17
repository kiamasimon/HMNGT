package com.example.simon.hmngt.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.simon.hmngt.R;

public class SplashScreenActivity extends AppCompatActivity {

    private int SLEEP_TIMER =2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar() .hide();
        LogoLauncher logoLauncher = new LogoLauncher ();
        logoLauncher.start();

    }

    private class LogoLauncher extends Thread {
        public void run () {
            try{
                sleep(1000 * SLEEP_TIMER); }
            catch (InterruptedException e){
                e.printStackTrace();
            }

            Intent intent = new Intent (SplashScreenActivity.this, CommonActivity.class );
            startActivity(intent);
            SplashScreenActivity.this.finish();


        }
    }
}

