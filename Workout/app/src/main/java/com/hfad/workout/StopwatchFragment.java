package com.hfad.workout;


import android.app.Fragment;


import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Button;

public class StopwatchFragment extends Fragment
                                implements View.OnClickListener{

    private int seconds = 0;
    private boolean running = false;
    private boolean wasRunning = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        runTimer(view);

        // Связывание обработчика onClickListener с кнопками
        Button start_button = (Button)view.findViewById(R.id.start_button);
        start_button.setOnClickListener(this);
        Button stop_button = (Button)view.findViewById(R.id.stop_button);
        stop_button.setOnClickListener(this);
        Button reset_button = (Button)view.findViewById(R.id.reset_button);
        reset_button.setOnClickListener(this);

        return view;
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

    private void runTimer(View v){
        final TextView timeview = (TextView)v.findViewById(R.id.time_view);

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

    @Override
    public void onClick(View v){

        switch( v.getId() ){
            case R.id.start_button:
                onClickStart(v);
                break;
            case R.id.stop_button:
                onClickStop(v);
                break;
            case R.id.reset_button:
                onClickReset(v);
                break;
        }
    }



}

