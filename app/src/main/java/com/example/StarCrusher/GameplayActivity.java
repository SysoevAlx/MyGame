package com.example.StarCrusher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class GameplayActivity extends AppCompatActivity {


    private GameView gameView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        mediaPlayer = MediaPlayer.create(this, R.raw.backmusic);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.5f,0.4f);
        if (!prefs.getBoolean("isMuteMusic", false)){
        mediaPlayer.start(); }
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        hideNavBar();
        gameView = new GameView(this, point.x, point.y);
        setContentView(gameView);

    }

    @Override
    public void onBackPressed() {
        gameView.isGameOver = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        if (!prefs.getBoolean("isMuteMusic", false)){
        mediaPlayer.start();}
        gameView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();

    }

    // Скрыть системную навигацию
    private void hideNavBar() {
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_IMMERSIVE);

    }
}