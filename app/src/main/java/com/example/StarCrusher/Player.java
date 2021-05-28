package com.example.StarCrusher;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.StarCrusher.GameView.screenRatioY;
import static com.example.StarCrusher.GameView.screenRatioX;

public class Player {
    int x, y, widht, height, directionx, directiony;
    Bitmap player;

    Player(int screenX, int screenY, Resources res, int skin) { // добавил параметр по y для лучшего позицонирования
        player = BitmapFactory.decodeResource(res, skin);
        widht = player.getWidth();
        height = player.getHeight();

        widht /= 64;
        height /= 32;

        widht *= (int) (widht* screenRatioX);
        height *= (int) (height*screenRatioY);

        player = Bitmap.createScaledBitmap(player, widht, height, false);

        x = screenX / 2 - player.getWidth()/2; // посередине
        y = (int) (screenY - (120 * screenRatioY)) - player.getHeight();
    }

    Rect HitBox() {
        return new Rect(x+30, y+25, x + widht-30, y + height-35);
    }

}