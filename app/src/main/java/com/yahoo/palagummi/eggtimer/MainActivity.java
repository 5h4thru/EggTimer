package com.yahoo.palagummi.eggtimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button button = null;
    TextView timerText = null;
    ImageView imageView = null;
    SeekBar seekBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the seekBar
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(600);
        // initial setup for the seekBar and the timer
        seekBar.setProgress(30);
        changeTimer(0,30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int minutes = (int) progress / 60;
                int seconds = progress - (minutes * 60);
                if(progress < 10) {
                    seekBar.setProgress(10);
                    changeTimer(0,10);
                }
                else {
                    changeTimer(minutes, seconds);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }

    public void changeTimer(int minutes, int seconds) {
        timerText = (TextView) findViewById(R.id.timerText);
        timerText.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));
    }
}
