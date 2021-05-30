package com.example.StarCrusher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopActivity extends AppCompatActivity {
    private int skinnow = 0;
    private SharedPreferences prefs;
    private ImageView skinImage;
    private Button button, butbuy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        hideNavBar();

        prefs = getSharedPreferences("game", MODE_PRIVATE);


        TextView moneyTxt = findViewById(R.id.moneyTxt);
        moneyTxt.setText ("$ " + prefs.getInt("Allmoney", 0));
        skinImage = findViewById(R.id.skin);


        findViewById(R.id.homeb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        findViewById(R.id.scoreb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopActivity.this, ScoreActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        findViewById(R.id.settingsb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopActivity.this, OptionActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        //Смотрим кнопки по айдишнику
        ImageView leftArrow = findViewById(R.id.leftarrow);
        ImageView rightArrow = findViewById(R.id.rightarrow);
        button = findViewById(R.id.acceptButton);
        butbuy = findViewById(R.id.BuyButton);

        leftArrow.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skinnow >=0) {
                    skinnow-=1;
                    ChangeSkinNow();
                }
            }
        });

        rightArrow.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skinnow <=2) {
                    skinnow+=1;
                    ChangeSkinNow();
                }
            }
        });

        button.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                switch (skinnow) {
                    case 0:
                        editor.putInt("skin", R.drawable.player);
                        editor.apply();
                        break;
                    case 1:
                        editor.putInt("skin", R.drawable.player2);
                        editor.apply();
                        break;
                    case 2:
                        editor.putInt("skin", R.drawable.player3);
                        editor.apply();
                        break;
                }
            }
        });

        butbuy.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                switch (skinnow) {
                    case 1:
                        if (prefs.getInt("Allmoney", 0) > 10000) {
                            editor.putInt("Allmoney", prefs.getInt("Allmoney", 0) - 10000);
                            editor.putBoolean("bought1", true);
                            editor.apply();
                            butbuy.setVisibility(View.INVISIBLE);
                            button.setEnabled(true);
                        }
                        break;
                    case 2:
                        if (prefs.getInt("Allmoney", 0) > 20000) {
                            editor.putInt("Allmoney", prefs.getInt("Allmoney", 0) - 20000);
                            editor.putBoolean("bought2", true);
                            editor.apply();
                            butbuy.setVisibility(View.INVISIBLE);
                            button.setEnabled(true);
                        }
                        break;
                }
                moneyTxt.setText ("$ " + prefs.getInt("Allmoney", 0));
            }
        });

    }

    // Скрыть системную навигацию при разворачивании приложения
    @Override
    protected void onResume() {
        super.onResume();
        hideNavBar();
    }

    private void ChangeSkinNow() {
        switch (skinnow) {
            case 0:
                skinImage.setImageResource(R.drawable.player);
                button.setEnabled(true);
                butbuy.setVisibility(View.INVISIBLE);
                break;
            case 1:
                skinImage.setImageResource(R.drawable.player2);
                if (prefs.getBoolean("bought1", false) == false){
                    button.setEnabled(false);
                    butbuy.setText("Buy" + "\n" + "$ 10000");
                    butbuy.setVisibility(View.VISIBLE);
                }
                else {button.setEnabled(true);
                butbuy.setVisibility(View.INVISIBLE);}
                break;
            case 2:
                skinImage.setImageResource(R.drawable.player3);
                if (prefs.getBoolean("bought2", false) == false){
                    button.setEnabled(false);
                    butbuy.setText("Buy" + "\n" + "$ 20000");
                    butbuy.setVisibility(View.VISIBLE);
                }
                else {button.setEnabled(true);
                butbuy.setVisibility(View.INVISIBLE);}
                break;
        }


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