package com.mariatorres.meditimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import me.itangqi.waveloadingview.WaveLoadingView;

public class Monkey extends AppCompatActivity {

    private static final long Start_Time_In_Millis = 300000;

    private WaveLoadingView waveLoadingView;
    private SeekBar seekBar;
    private Runnable runnable;
    private Handler handler;
    private ImageView play1, reset,pause;

    private MediaPlayer m1player;
    private CountDownTimer countDownTimer;
    private long timermillisecond = Start_Time_In_Millis;
    private boolean timerRunning;
    private TextView timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monkey);

        m1player = MediaPlayer.create(this, R.raw.beach);
        play1 = findViewById(R.id.play1);
        waveLoadingView = findViewById(R.id.wave);
        seekBar = findViewById(R.id.seekbar);
        waveLoadingView.setProgressValue(0);
        handler = new Handler();
        timer = findViewById(R.id.timerplay1);
        reset = findViewById(R.id.reset);
        pause = findViewById(R.id.pause);



        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
               // seekBar.setProgress(0);
                m1player.seekTo(0);
            }
        });

        updateTimer();

        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!m1player.isPlaying()){
                    pause.setVisibility(View.VISIBLE);
                    play1.setVisibility(View.INVISIBLE);
                    m1player.start();
                    startTimer();
                 //   changeSeekBar();

                }else{
                    m1player.pause();
                    pauseTimer();
                    play1.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.INVISIBLE);



                }

            }


        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!m1player.isPlaying()) {
                    pause.setVisibility(View.VISIBLE);
                    play1.setVisibility(View.INVISIBLE);
                    m1player.start();
                    startTimer();
                //    changeSeekBar();


                } else {
                    pauseTimer();
                    m1player.pause();
                    play1.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.INVISIBLE);
                }
            }
        });

//        m1player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                seekBar.setMax(m1player.getDuration());
//                changeSeekBar();
//            }
//        });

//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if(fromUser){
//                    m1player.seekTo(progress);
//                    //waveLoadingView.setProgressValue(progress);
//
//                }
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

        m1player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(Monkey.this, "Congratulations, the meditation is done.", Toast.LENGTH_LONG).show();
                pause.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(Monkey.this, UserLevel.class);
                startActivity(intent);

            }
        });


    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timermillisecond, 1000) {
            @Override
            public void onTick(long l) {
                timermillisecond = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                reset.setVisibility(View.VISIBLE);
            }
        }.start();

        timerRunning = true;
        reset.setVisibility(View.INVISIBLE);

    }

    public void pauseTimer(){
        countDownTimer.cancel();
        timerRunning = false;
        reset.setVisibility(View.VISIBLE);
    }

    public void resetTimer(){
        timermillisecond = Start_Time_In_Millis;
        updateTimer();
        reset.setVisibility(View.INVISIBLE);

    }

        public void updateTimer(){
            int min = (int) timermillisecond / 1000 / 60;
            int seg = (int) timermillisecond / 1000 % 60;

            String timetext = String.format(Locale.getDefault(),"%02d:%02d", min, seg);

            timer.setText(timetext);
        }

        private void releaseMediaPlayer(){
            if (m1player != null){
                m1player.release();
                m1player = null;
            }
        }

        protected void onStop(){
            super.onStop();
            releaseMediaPlayer();
        }

//    private void changeSeekBar() {
//        seekBar.setProgress(m1player.getCurrentPosition());
//       // waveLoadingView.setProgressValue(m1player.getCurrentPosition());
//        if (m1player.isPlaying()){
//            runnable = new Runnable() {
//                @Override
//                public void run() {
//                    changeSeekBar();
//                }
//            };
//
//            handler.postDelayed(runnable, 1000);
//        }
//
//    }


}
