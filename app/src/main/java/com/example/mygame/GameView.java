package com.example.mygame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying, isGameOver;
    private int screenX, screenY, cooldown, killcount;
    private int[] formercoordinate = new int[2];
    public static float screenRatioX, screenRatioY;
    private Random random;
    private Paint paint;
    private GameBackground background1, background2;
    private Player player;
    private Boss boss;
    private List<Enemy> enemies;
    private List<Bullet> bullets;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;
        cooldown = 0;
        killcount = 0;

        background1 = new GameBackground(screenX, screenY, getResources());
        background2 = new GameBackground(screenX, screenY, getResources());

        player = new Player(screenX, screenY, getResources());
        boss = new Boss(screenX, screenY, getResources());

        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Enemy enemy = new Enemy(getResources());
            enemy.y = -100;
            enemies.add(enemy);
        }


        background2.y = screenY;

        paint = new Paint();
        random = new Random();
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
        if (player.directionx != 0) {
            player.x += player.directionx * screenRatioX * 10;
        }
        if (player.directiony != 0) {
            player.y += player.directiony * screenRatioX * 10;
        }

        if (player.x + player.widht / 2 <= formercoordinate[0] + 10 && player.x + player.widht / 2 >= formercoordinate[0] - 10)
            player.directionx = 0;

        if (player.y + player.height / 2 <= formercoordinate[1] + 25 && player.y + player.height / 2 >= formercoordinate[1] - 25)
            player.directiony = 0;


        if (player.x <= 0) player.x = 0;
        if (player.x >= screenX - player.widht) player.x = screenX - player.widht;


        if (cooldown % 20 == 1) {
            ShootBullet();
        }

        if (killcount == 5 && boss.y<-500)
        {
            boss.y = 300;
        }

        List<Bullet> trashBullet = new ArrayList<>();

        for (Bullet bullet : bullets) {
            if (bullet.y < 0) {
                trashBullet.add(bullet);
            }
            if (Rect.intersects(bullet.HitBox(), boss.HitBox()))
            {
                bullet.y = -screenY;
                if (boss.health==0) { // логика босса
                    boss.y -= screenY;
                    boss.health = 20;
                    cooldown=0;
                }
                boss.health -=1;
            }
            bullet.y -= 20 * screenRatioY;
        }

        for (Enemy enemy : enemies) { // логика противников
            if (enemy.y > screenY) {
                enemy.y = -100;
            }

            if (enemy.y + enemy.height < 0) {
                enemy.speed = random.nextInt((int) (30 * screenRatioY)) + 5;
                enemy.x = random.nextInt(screenX - enemy.widht);
            }

            enemy.y += enemy.speed;

            for (Bullet bullet : bullets) {
                if (Rect.intersects(enemy.HitBox(), bullet.HitBox())) {
                    enemy.y -= 500;
                    bullet.y -=screenY;
                    killcount++;
                }
            }

            if (Rect.intersects(enemy.HitBox(), player.HitBox())) {
                isGameOver = true;
                return;
            }
        }

        for (Bullet bullet : trashBullet) {
            bullets.remove(bullet);
        }
        trashBullet.clear();
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            canvas.drawBitmap(player.player, player.x, player.y, paint);
            canvas.drawBitmap(boss.boss, boss.x, boss.y, paint);


            for (Bullet bullet : bullets) {
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }

            for (Enemy enemy : enemies) {
                canvas.drawBitmap(enemy.enemy, enemy.x, enemy.y, paint);
            }
            if(isGameOver) {
                isPlaying = false;
                return; // конец геймовер
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep() {
        try {
            cooldown++;
            Thread.sleep(4);
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
        formercoordinate[0] = (int) event.getX();
        formercoordinate[1] = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (event.getX() < player.x + player.widht / 2 - 10) {
                    player.directionx = -1;
                }
                else if (event.getX() > player.x + player.widht / 2 + 10) {
                    player.directionx = 1;
                }

                if (event.getY()<player.y+player.height/2 + 10) player.directiony= -1;
                else if (event.getY()>player.y+player.height/2-10) player.directiony = 1;
                break;
            case MotionEvent.ACTION_UP:
                player.directionx = 0;
                player.directiony = 0;
                break;
        }
        return true;
    }

    public void ShootBullet() {
        Bullet bullet = new Bullet(getResources());
        bullet.x = player.x + player.widht / 2;
        bullet.y = player.y;
        bullets.add(bullet);
    }
}
