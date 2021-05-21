package com.example.mygame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.example.mygame.GameView.screenRatioY;
import static com.example.mygame.GameView.screenRatioX;


public class Bullet {
    int x, y, widht, height;
    Bitmap bullet;

    Bullet(Resources res) {
        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);

        widht = bullet.getWidth();
        height = bullet.getHeight();

        widht /= 24;
        height /= 24;

        widht *= (int) (widht* screenRatioX);
        height *= (int) (height*screenRatioY);

        bullet = Bitmap.createScaledBitmap(bullet, widht, height, false);
    }
}
