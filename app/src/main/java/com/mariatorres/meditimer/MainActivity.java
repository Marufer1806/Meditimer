package com.mariatorres.meditimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mPlayer;
    private Button btnstart, btnstop;
    private SeekBar barseekbar;
    private Runnable runnable;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayer =  MediaPlayer.create(this, R.raw.beach);
        btnstart = findViewById(R.id.btnstart);
        btnstop = findViewById(R.id.btnstop);
        barseekbar =  findViewById(R.id.barseekbar);
        handler = new Handler();

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.start();
                changeSeekBar();
            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.pause();
            }
        });

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(MainActivity.this, "Meditation is done", Toast.LENGTH_LONG).show();
                releaseMediaPlayer();
            }
        });


        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                barseekbar.setMax(mPlayer.getDuration());
               // mediaPlayer.start();
                changeSeekBar();
            }
        });

        barseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    mPlayer.seekTo(i);
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

    private void releaseMediaPlayer(){
        if (mPlayer != null){
            mPlayer.release();
            mPlayer = null;
        }
    }

    protected void onStop(){
        super.onStop();
        releaseMediaPlayer();
    }

    private void changeSeekBar() {
        barseekbar.setProgress(mPlayer.getCurrentPosition());

        if (mPlayer.isPlaying()){
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
