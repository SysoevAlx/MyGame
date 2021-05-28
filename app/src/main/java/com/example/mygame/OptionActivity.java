package com.example.mygame;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class OptionActivity extends AppCompatActivity {

    private boolean isMute;  // Звуки
    private boolean isMuteMusic; // Музыка

    Button nicksave;
    EditText editText;
    String pn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        hideNavBar();


        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);

        isMute = prefs.getBoolean("isMute", false);
        isMuteMusic = prefs.getBoolean("isMuteMusic", false);


        nicksave = (Button) findViewById(R.id.nicknamesave);
        editText=(EditText) findViewById(R.id.nickname);
        String df = prefs.getString("pn", "player");
        editText.setText(df);


        nicksave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pn = editText.getText().toString();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("pn", pn);
                editor.apply();
            }
        });


        // Кнопка звука
        ImageView soundButton = findViewById(R.id.soundb);
        if (isMute)
        {
            soundButton.setImageResource(R.drawable.sound_off);
            soundButton.setBackgroundResource(R.color.OffRed);
        }
        else {
            soundButton.setImageResource(R.drawable.sound_on);
            soundButton.setBackgroundResource(R.color.OnGreen);
        }
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMute = !isMute;
                if (isMute)
                {
                    soundButton.setImageResource(R.drawable.sound_off);
                    soundButton.setBackgroundResource(R.color.OffRed);
                }
                else {
                    soundButton.setImageResource(R.drawable.sound_on);
                    soundButton.setBackgroundResource(R.color.OnGreen);
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
            musicButton.setBackgroundResource(R.color.OffRed);
        }
        else {
            musicButton.setImageResource(R.drawable.music_on);
            musicButton.setBackgroundResource(R.color.OnGreen);
        }
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMuteMusic = !isMuteMusic;
                if (isMuteMusic)
                {
                    musicButton.setImageResource(R.drawable.music_off);
                    musicButton.setBackgroundResource(R.color.OffRed);
                }
                else {
                    musicButton.setImageResource(R.drawable.music_on);
                    musicButton.setBackgroundResource(R.color.OnGreen);
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

        findViewById(R.id.scoreb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OptionActivity.this, ScoreActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        findViewById(R.id.shopb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OptionActivity.this, ShopActivity.class));
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