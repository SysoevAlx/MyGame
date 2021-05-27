package com.example.mygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class OptionActivity extends AppCompatActivity {

    private boolean isMute;  // Звуки
    private boolean isMuteMusic; // Музыка

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        hideNavBar();

        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        isMute = prefs.getBoolean("isMute", false);
        isMuteMusic = prefs.getBoolean("isMuteMusic", false);


        // Кнопка звука
        ImageView soundButton = findViewById(R.id.soundb);
        if (isMute)
        {
            soundButton.setImageResource(R.drawable.sound_off);
        }
        else {
            soundButton.setImageResource(R.drawable.sound_on);
        }
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMute = !isMute;
                if (isMute)
                {
                    soundButton.setImageResource(R.drawable.sound_off);
                }
                else {
                    soundButton.setImageResource(R.drawable.sound_on);
                }
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isMute", isMute);
                editor.apply();
            }
        });

        // Кнопка музыки
        ImageView musicButton = findViewById(R.id.musicb);
        if (isMuteMusic)
        {
            musicButton.setImageResource(R.drawable.music_off);
        }
        else {
            musicButton.setImageResource(R.drawable.music_on);
        }
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMuteMusic = !isMuteMusic;
                if (isMuteMusic)
                {
                    musicButton.setImageResource(R.drawable.music_off);
                }
                else {
                    musicButton.setImageResource(R.drawable.music_on);
                }
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isMuteMusic", isMuteMusic);
                editor.apply();
            }
        });


        findViewById(R.id.homeb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OptionActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
            }
        });
    }

    // Скрыть системную навигацию при разворачивании приложения
    @Override
    protected void onResume() {
        super.onResume();
        hideNavBar();
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