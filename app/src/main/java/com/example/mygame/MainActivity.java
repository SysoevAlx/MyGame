package com.example.mygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideNavBar();

        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);

        TextView highScoreTxt = findViewById(R.id.highScoreTxt);
        highScoreTxt.setText ("HighScore: " + prefs.getInt("highscore", 0));

        TextView moneyTxt = findViewById(R.id.moneyTxt);
        moneyTxt.setText ("$ " + prefs.getInt("Allmoney", 0));

        findViewById(R.id.playb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GameplayActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        findViewById(R.id.settingsb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OptionActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        findViewById(R.id.shopb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShopActivity.class));
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