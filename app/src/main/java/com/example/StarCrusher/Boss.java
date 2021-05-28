package com.example.StarCrusher;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.StarCrusher.GameView.screenRatioX;
import static com.example.StarCrusher.GameView.screenRatioY;

public class Boss {
    int x, y, widht, height, health = 10;
    Bitmap boss;

    Boss(int screenX, int screenY, Resources res) { // добавил параметр по y для лучшего позицонирования
        boss = BitmapFactory.decodeResource(res, R.drawable.boss);

        widht = boss.getWidth();
        height = boss.getHeight();

        widht /= 24;
        height /= 24;

        widht *= (int) (widht* screenRatioX);
        height *= (int) (height*screenRatioY);

        boss = Bitmap.createScaledBitmap(boss, widht, height, false);

        y -= screenY;
        x = screenX / 2 - boss.getWidth()/2; // посередине
    }

    Rect HitBox() {
        return new Rect(x, y, x + widht, y + height);
    }

}
