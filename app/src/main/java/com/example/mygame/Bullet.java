package com.example.mygame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.mygame.GameView.screenRatioY;
import static com.example.mygame.GameView.screenRatioX;


public class Bullet {
    int x, y, widht, height;
    Bitmap bullet;

    Bullet(Resources res, int type) {
        if (type == 1)
        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
        else bullet = BitmapFactory.decodeResource(res, R.drawable.ebullet);

        widht = bullet.getWidth();
        height = bullet.getHeight();

        if (type == 1) {
            widht /= 24;
            height /= 24;
        }
        else {
            widht /= 24;
            height /= 10;
        }
        widht *= (int) (widht* screenRatioX);
        height *= (int) (height*screenRatioY);

        bullet = Bitmap.createScaledBitmap(bullet, widht, height, false);
    }

    Rect HitBox() {
        return new Rect(x, y, x + widht, y + height);
    }
}
