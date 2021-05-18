package com.example.mygame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.example.mygame.GameView.screenRatioY;
import static com.example.mygame.GameView.screenRationX;

public class Player {
    int x, y, widht, height, direction;
    Bitmap player;

    Player(int screenX, int screenY, Resources res) { // добавил параметр по y для лучшего позицонирования
        player = BitmapFactory.decodeResource(res, R.drawable.player);
        widht = player.getWidth();
        height = player.getHeight();

        widht /= 64;
        height /= 32;

        widht *= (int) (widht*screenRationX);
        height *= (int) (height*screenRatioY);

        player = Bitmap.createScaledBitmap(player, widht, height, false);

        x = screenX / 2 - player.getWidth()/2; // посередине
        y = (int) (screenY - (120 * screenRatioY)) - player.getHeight();
    }

}
