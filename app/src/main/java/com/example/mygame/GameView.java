package com.example.mygame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private final GameplayActivity activity;
    private int soundhit, soundexplosion, soundbutton;
    private Thread thread;
    private boolean isPlaying, isGameOver;
    private int screenX, screenY, cooldown, killcount, difficulty, score = 0, money = 0, skin, backspeed;
    private int[] formercoordinate = new int[2];
    public static float screenRatioX, screenRatioY;
    private Random random;
    private Paint paint;
    private GameBackground background1, background2;
    private Player player;
    private Boss boss;
    private Enemy[] enemies;
    private SharedPreferences prefs;
    private List<Bullet> bullets;
    private List<Bullet> ebullets;
    private SoundPool soundPool;


    public GameView(GameplayActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;
        cooldown = 0;
        killcount = 0;
        difficulty = 5;

        background1 = new GameBackground(screenX, screenY, getResources());
        background2 = new GameBackground(screenX, screenY, getResources());
        skin = prefs.getInt("skin", R.drawable.player);

        player = new Player(screenX, screenY, getResources(), skin);
        boss = new Boss(screenX, screenY, getResources());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // работаем с музыкой

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else
            soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);

        soundhit = soundPool.load(activity, R.raw.shoot, 1);
        soundexplosion = soundPool.load(activity, R.raw.explode, 2);
        soundbutton = soundPool.load(activity, R.raw.buttons, 3);


        bullets = new ArrayList<>();
        ebullets = new ArrayList<>();
        enemies = new Enemy[4];
        for (int i = 0; i < 4; i++) {
            Enemy enemy = new Enemy(getResources());
            enemy.y = -100;
            enemies[i] = enemy;
        }


        background2.y = screenY;

        paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.WHITE);
        Typeface furore = ResourcesCompat.getFont(getContext(), R.font.furore);
        paint.setTypeface(furore);

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

        backspeed = (int) (14 * screenRatioY);

        background1.y -= backspeed;
        background2.y -= backspeed;

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

        if (killcount >= 5 && boss.y < 300) {
            boss.y += 25;
        }

        if (boss.y > 300) {
            for (int i = 0; i < difficulty; i++) {
                if (random.nextInt(50) < 15 && cooldown % 20 == 1) { // стрельба босса
                    Bullet enb = new Bullet(getResources(), 2);
                    enb.x = random.nextInt(boss.widht) + boss.x;
                    enb.y = boss.y + boss.height;
                    ebullets.add(enb);
                }
            }
        }

        List<Bullet> trashBullet = new ArrayList<>();

        for (Bullet bullet : bullets) {
            if (bullet.y < 0) {
                trashBullet.add(bullet);
            }
            if (Rect.intersects(bullet.HitBox(), boss.HitBox())) {
                bullet.y = -screenY;
                if (boss.health == 0) { // логика босса
                    boss.y -= screenY;
                    cooldown = 0;
                    killcount = 0;
                    difficulty++;
                    score = score + 10;
                    money = money + 100;
                    boss.health = 10;
                    if (!prefs.getBoolean("isMute", false)) {
                        soundPool.play(soundexplosion, 1, 1, 1, 3, 1.5f);
                    }
                }
                boss.health -= 1;
            }
            bullet.y -= 40 * screenRatioY;
        }

        for (Enemy enemy : enemies) { // логика противников
            if (enemy.y > screenY) {
                enemy.y = -100;
            }

            if (enemy.y + enemy.height < 0) {
                enemy.speed = random.nextInt((int) (20 * screenRatioY)) + 5;
                enemy.x = random.nextInt(screenX - enemy.widht);
            }

            enemy.y += enemy.speed;

            if (random.nextInt(50) < 10 && cooldown % 20 == 1) { // стрельба врагов
                Bullet enb = new Bullet(getResources(), 2);
                enb.x = enemy.x + enemy.widht / 2;
                enb.y = enemy.y + enemy.height;
                ebullets.add(enb);
            }


            for (Bullet bullet : bullets) {
                if (Rect.intersects(enemy.HitBox(), bullet.HitBox())) {
                    enemy.y = -200;
                    bullet.y -= screenY;
                    killcount++;
                    score++;
                    money = money + 20;
                    if (!prefs.getBoolean("isMute", false)) {
                        soundPool.play(soundexplosion, 1, 1, 1, 0, 1);
                    }
                }
            }

            if (Rect.intersects(enemy.HitBox(), player.HitBox())) {
                if (!prefs.getBoolean("isMute", false)) {
                    soundPool.play(soundexplosion, 1, 1, 1, 0, 1);
                }
                isGameOver = true;
                return;
            }
        }

        for (Bullet bullet : ebullets) { // логика вражкеских пуль

            bullet.y += 40 * screenRatioY;
            if (bullet.y > screenY) {
                trashBullet.add(bullet);
            }
            if (Rect.intersects(bullet.HitBox(), player.HitBox())) {
                if (!prefs.getBoolean("isMute", false)) {
                    soundPool.play(soundexplosion, 1, 1, 1, 0, 1);
                }
                isGameOver = true;
                return;
            }
        }

        for (Bullet bullet : trashBullet) {
            bullets.remove(bullet);
            ebullets.remove(bullet);
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

            for (Bullet bullet : ebullets) {
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }

            for (Enemy enemy : enemies) {
                canvas.drawBitmap(enemy.enemy, enemy.x, enemy.y, paint);
            }

            canvas.drawText("Score: " + score + "", 50, 100, paint);
            canvas.drawText("Money: " + money + "", 50, 200, paint);

            if (isGameOver) {
                isPlaying = false;
                getHolder().unlockCanvasAndPost(canvas);
                SaveHighScore();
                SaveMoney();
                waitBeforeOff();
                return; // конец геймовер
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void SaveHighScore() {
        if (prefs.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }
    }

    private void SaveMoney() {
        money = prefs.getInt("Allmoney", 0) + money;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Allmoney", money);
        editor.apply();
    }

    private void waitBeforeOff() {
        try {
            Thread.sleep(1000);
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleep() {
        try {
            if (isPlaying){
            cooldown++;
            Thread.sleep(4);}
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
                } else if (event.getX() > player.x + player.widht / 2 + 10) {
                    player.directionx = 1;
                }

                if (event.getY() < player.y + player.height / 2 + 10) player.directiony = -1;
                else if (event.getY() > player.y + player.height / 2 - 10) player.directiony = 1;
                break;
            case MotionEvent.ACTION_UP:
                player.directionx = 0;
                player.directiony = 0;
                break;
        }
        return true;
    }

    public void ShootBullet() {
        if (!prefs.getBoolean("isMute", false)) {
            soundPool.play(soundhit, 0.5f, 0.5f, 0, 0, 1);
        }
        Bullet bullet = new Bullet(getResources(), 1);
        bullet.x = player.x + player.widht / 2;
        bullet.y = player.y - bullet.height / 2;
        bullets.add(bullet);
    }
}
