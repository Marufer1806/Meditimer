package com.mariatorres.meditimer;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Locale;

import me.itangqi.waveloadingview.WaveLoadingView;

public class Monkey extends AppCompatActivity {

    private WaveLoadingView waveLoadingView;
    private SeekBar seekBar;
    private Runnable runnable;
    private Handler handler;
    private ImageView play1;

    private MediaPlayer m1player;
    private CountDownTimer countDownTimer;
    private long timermillisecond = 300000;
    private boolean timerRunning;
    private TextView timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monkey);

        m1player = MediaPlayer.create(this, R.raw.lake);
        play1 = findViewById(R.id.play1);
        waveLoadingView = findViewById(R.id.wave);
        seekBar = findViewById(R.id.seekbar);
        waveLoadingView.setProgressValue(0);
        handler = new Handler();
        timer = findViewById(R.id.timerplay1);




        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!m1player.isPlaying()){
                    play1.setImageResource(R.drawable.pause);
                    m1player.start();
                    starttimer();
                    changeSeekBar();
                }else{
                    m1player.pause();
                    play1.setImageResource(R.drawable.play1);
                }

            }


        });

        m1player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                seekBar.setMax(m1player.getDuration());
                changeSeekBar();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    m1player.seekTo(progress);
                    //waveLoadingView.setProgressValue(progress);

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void starttimer() {
        countDownTimer = new CountDownTimer(timermillisecond, 1000) {
            @Override
            public void onTick(long l) {
                timermillisecond = l;
                updatetimer();
            }

            @Override
            public void onFinish() {
            }
        }.start();

    }

        public void updatetimer(){
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



    private void changeSeekBar() {
        seekBar.setProgress(m1player.getCurrentPosition());
       // waveLoadingView.setProgressValue(m1player.getCurrentPosition());
        if (m1player.isPlaying()){
            runnable = new Runnable() {
                @Override
                public void run() {
                    changeSeekBar();
                }
            };

            handler.postDelayed(runnable, 1000);
        }

    }


}
