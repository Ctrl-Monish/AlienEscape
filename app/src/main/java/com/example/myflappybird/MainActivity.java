package com.example.myflappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    MediaPlayer themesong;
    private GameView gameView;
    private Handler handler;
    private final static long TIME_INTERVAL=30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        setContentView(gameView);
        themesong = MediaPlayer.create(MainActivity.this,R.raw.evil_morty);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameView.invalidate();
                themesong.start();
            }
        },0,TIME_INTERVAL);
    }
}