package com.example.mygame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.mygame.GameView.screenRatioX;
import static com.example.mygame.GameView.screenRatioY;

public class Enemy {
    int x, y, widht, height, speed;
    Bitmap enemy;

    Enemy(Resources res) {
        enemy = BitmapFactory.decodeResource(res, R.drawable.player);

        widht = enemy.getWidth();
        height = enemy.getHeight();

        widht /= 64;
        height /= 32;

        widht *= (int) (widht* screenRatioX);
        height *= (int) (height*screenRatioY);

        enemy = Bitmap.createScaledBitmap(enemy, widht, height, false);
    }

    Rect HitBox() {
        return new Rect(x, y, x + widht, y + height);
    }
}
