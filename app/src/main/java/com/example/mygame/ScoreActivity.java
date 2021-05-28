package com.example.mygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        hideNavBar();

        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        TextView highScore1 = findViewById(R.id.fscore);
        highScore1.setText ("" + prefs.getInt("highscore", 00000));
        TextView highScoreName1 = findViewById(R.id.fname);
        highScoreName1.setText ("" + prefs.getString("highname", "....."));

        TextView highScore2 = findViewById(R.id.sscore);
        highScore2.setText ("" + prefs.getInt("highscore2", 00000));
        TextView highScoreName2 = findViewById(R.id.sname);
        highScoreName2.setText ("" + prefs.getString("highname2", "....."));

        TextView highScore3 = findViewById(R.id.tscore);
        highScore3.setText ("" + prefs.getInt("highscore3", 00000));
        TextView highScoreName3 = findViewById(R.id.tname);
        highScoreName3.setText ("" + prefs.getString("highname3", "....."));

        TextView highScore4 = findViewById(R.id.frscore);
        highScore4.setText ("" + prefs.getInt("highscore4", 00000));
        TextView highScoreName4 = findViewById(R.id.frname);
        highScoreName4.setText ("" + prefs.getString("highname4", "....."));

        TextView highScore5 = findViewById(R.id.fvscore);
        highScore5.setText ("" + prefs.getInt("highscore5", 00000));
        TextView highScoreName5 = findViewById(R.id.fvname);
        highScoreName5.setText ("" + prefs.getString("highname5", "....."));


        findViewById(R.id.homeb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScoreActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        findViewById(R.id.shopb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScoreActivity.this, ShopActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        findViewById(R.id.settingsb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScoreActivity.this, OptionActivity.class));
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