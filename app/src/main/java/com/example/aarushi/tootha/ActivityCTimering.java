package com.example.aarushi.tootha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.os.CountDownTimer;

public class ActivityCTimering extends AppCompatActivity {

    private TextView countdownText;
    private Button countdownButton;
    private CountDownTimer countDownTimer;
    private long timeleftinMill = 120000;
    private boolean timerRunning;
    private Button mButtonReset;
    private TextView text;
    public ImageView iv;

    public static Integer[] mThumbIds = {
            R.drawable.tooth2, R.drawable.tooth3, R.drawable.tooth4, R.drawable.tooth5, R.drawable.tooth6};
    String[] wcl = {"1.  Gently brush the outer surfaces of 2-3 teeth vibrating back and forth & rolling motion", "2.  Move brush to next 2-3 teeth and repeat", "3.    Gently brush along all the inner tooth surfaces","4.   Tilt brush vertically behind the front teeth. Make several up & down strokes using the front half of the brush","5.  Place the brush against the biting surface use a gentle back and forth scrubbing motion. Brush the tongue from back to front"};

    int i, j;

    private ProgressBar progressBarCircle;
    Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctimer);

        play = findViewById(R.id.button_play);
        final MediaPlayer mp = MediaPlayer.create(ActivityCTimering.this, R.raw.sound);
        //imb=findViewById(R.id.imageButton);
        mButtonReset = findViewById(R.id.button_reset);
        countdownText = findViewById(R.id.countdown_text);
        countdownButton = findViewById(R.id.countdownbutton);
        progressBarCircle = findViewById(R.id.progressBarCircle);



        play.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (mp.isPlaying()) {
                    mp.pause();
                    play.setBackgroundResource(R.drawable.play);
                } else {
                    mp.start();
                    play.setBackgroundResource(R.drawable.pause);
                }
            }
        });

        countdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
                if (mp.isPlaying()) {
                    mp.pause();
                    play.setBackgroundResource(R.drawable.play);
                } else {
                    mp.start();
                    play.setBackgroundResource(R.drawable.pause);
                }
            }
        });
        updateTimer();

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                mp.pause();
            }
        });
        updateTimer();

        text = findViewById(R.id.textView1);
        iv = (ImageView) findViewById(R.id.imageView);
        i = 0;
        j = 0;
        t.start();
    }

    Thread t = new Thread() {
        @Override
        public void run() {
            iv.setImageResource(R.drawable.tooth2);
            try {
                while (!isInterrupted()) {
                    Thread.sleep(20000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text.setText(wcl[j]);
                            iv.setImageResource(mThumbIds[i]);
                            i++;
                            j++;
                            if (i >= mThumbIds.length) {
                                i = 0;
                            }
                            if (j >= wcl.length) {
                                j = 0;
                            }


                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        }
    };



    public void startStop() {
        if (timerRunning) {
            stopTimer();
        } else {
            startTimer();
            setProgressBarValues();
        }
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeleftinMill, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeleftinMill = millisUntilFinished;
                updateTimer();
                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {
                countdownText.setText("FINISH");
                countdownButton.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
                setProgressBarValues();
                t.interrupt();
            }
        }.start();
        countdownButton.setText("PAUSE");
        timerRunning = true;
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    public void stopTimer() {
        countDownTimer.cancel();
        countdownButton.setText("START");
        timerRunning = false;
        mButtonReset.setVisibility(View.VISIBLE);
        t.interrupt();
    }

    public void updateTimer() {
        int minutes = (int) timeleftinMill / 60000;
        int seconds = (int) timeleftinMill % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";

        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        countdownText.setText(timeLeftText);
    }

    public void resetTimer() {
        timeleftinMill = 120000;
        updateTimer();
        mButtonReset.setVisibility(View.INVISIBLE);
        countdownButton.setVisibility(View.VISIBLE);
    }

    private void setProgressBarValues() {

        progressBarCircle.setMax((int) 120000 / 1000);
        progressBarCircle.setProgress((int) timeleftinMill / 1000);
    }

}



