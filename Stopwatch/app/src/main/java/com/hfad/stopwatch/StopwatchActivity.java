package com.hfad.stopwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running = false;
    private boolean wasRunning = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        if (savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();

    }

    public void onClickStart(View view){
        running = true;
    }

    public void onClickStop(View view){
        running = false;
    }

    public void onClickReset(View view){
        running  = false;
        seconds = 0;
    }

    private void runTimer(){
        final TextView timeview = (TextView)findViewById(R.id.time_view);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                timeview.setText(time);

                if (running)
                    seconds++;

                handler.postDelayed(this, 1000);
            }
        });

        }

        @Override
        public void onSaveInstanceState(Bundle saveInstanceState){
            super.onSaveInstanceState(saveInstanceState);
            saveInstanceState.putInt("seconds", seconds);
            saveInstanceState.putBoolean("running", running);
            saveInstanceState.putBoolean("wasRunning", wasRunning);
        }
/*
        @Override
        public void onStop(){
            super.onStop();
            wasRunning = running;
            running = false;
        }

        @Override
        public void onStart(){
            super.onStart();
            if (wasRunning)
                running = true;
        }

        @Override
        public void onRestart(){
            super.onRestart();
            running = true;
        }*/


    @Override
    public void onPause(){
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    public void onResume(){
        super.onResume();
        if (wasRunning)
            running = true;
    }

    }

