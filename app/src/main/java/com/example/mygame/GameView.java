package com.example.mygame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private int screenX, screenY, cooldown;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private GameBackground background1, background2;
    private Player player;
    private List<Bullet> bullets;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;
        cooldown = 0;

        background1 = new GameBackground(screenX, screenY, getResources());
        background2 = new GameBackground(screenX, screenY, getResources());

        player = new Player(screenX, screenY, getResources());

        bullets = new ArrayList<>();

        background2.y = screenY;

        paint = new Paint();
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            sleep();

        }
    }

    private void update() {
        background1.y -= 10 * screenRatioY;
        background2.y -= 10 * screenRatioY;

        if (background1.y + background1.background.getHeight() < 0) {
            background1.y = screenY;
        }
        if (background2.y + background2.background.getHeight() < 0) {
            background2.y = screenY;
        }
        if (player.direction != 0) {
            player.x += player.direction * screenRatioX * 10;
        }
        if (player.x <= 0) player.x = 0;
        if (player.x >= screenX - player.widht) player.x = screenX - player.widht;

        List<Bullet> trash = new ArrayList<>();

        if (cooldown == 5) {
            ShootBullet();
            cooldown = 0;
        }

        for (Bullet bullet : bullets) {
            if (bullet.y < 0) {
                trash.add(bullet);
            }
            bullet.y -= 50 * screenRatioY;
        }

        for (Bullet bullet : trash) {
            bullets.remove(bullet);
            trash.remove(bullet);
        }
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            canvas.drawBitmap(player.player, player.x, player.y, paint);

            for (Bullet bullet : bullets) {
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep() {
        try {
            cooldown++;
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2) {
                    player.direction = -1;
                } else player.direction = 1;
                break;
            case MotionEvent.ACTION_UP:
                player.direction = 0;
                break;
        }
        return true;
    }

    public void ShootBullet() {
        Bullet bullet = new Bullet(getResources());
        bullet.x = player.x+player.widht/2;
        bullet.y = player.y;
        bullets.add(bullet);
    }
}
