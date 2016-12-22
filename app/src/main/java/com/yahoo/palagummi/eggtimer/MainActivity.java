package com.yahoo.palagummi.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    boolean counterIsActive = false; // counter is not active initially
    CountDownTimer countDownTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the seekBar
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(600);
        // initial setup for the seekBar and the timer
        seekBar.setProgress(30);
        updateTimer(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                updateTimer(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }

    // helper method to change the timerText
    public void updateTimer(int secondsLeft) {
        timerText = (TextView) findViewById(R.id.timerText);
        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);
        String minutesString = "";
        String secondsString = "";
        if (minutes < 10) {
            minutesString = "0" + Integer.toString(minutes);
        } else {
            minutesString = Integer.toString(minutes);
        }
        if (seconds < 10) {
            secondsString = "0" + Integer.toString(seconds);
        } else {
            secondsString = Integer.toString(seconds);
        }
        timerText.setText(minutesString+ ":" + secondsString);
    }

    // control Timer form the button
    public void controlTimer(View view) {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        button = (Button) findViewById(R.id.button);
        timerText = (TextView) findViewById(R.id.timerText);

        if(seekBar.isEnabled() && !counterIsActive) {
            seekBar.setEnabled(false);
            button.setText("Reset");
            counterIsActive = true;
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 500, 1000) { // adding 1/2  of the timer to change the behaviour of long/1000 which is floored to int
                @Override
                public void onTick(long l) { // l is in milliseconds
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mPlayer.start();
                }
            }.start();
        } else {
            resetTimer();
        }
    }

    // reset timer helper method
    public void resetTimer() {
        seekBar.setEnabled(true);
        button.setText("Start");
        timerText.setText("00:30");
        seekBar.setProgress(30);
        countDownTimer.cancel();
        counterIsActive = false;
    }
}
