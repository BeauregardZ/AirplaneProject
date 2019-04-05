package com.example.mac.plane006;

/**
 * Created by mac on 2019/4/5.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.YuvImage;
import android.util.Log;
import android.view.MotionEvent;

public class MyPlane {

    private Bitmap bitmap;
    private int x, y;
    private static int width, height;
    private int fx;

    private boolean noCollision;
    private int noCollisionCount;//碰撞计数器
    private Bitmap bitmapHp;
    private int hp = 3;


    public MyPlane(Bitmap bitmap, Bitmap bitmapHp) {
        this.bitmap = bitmap;
        x = MySurfaceView.width / 2 - bitmap.getWidth() / 2;
        y = MySurfaceView.height - bitmap.getHeight();
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        this.bitmapHp = bitmapHp;


    }


    public void draw(Canvas canvas, Paint paint) {
        if (hp<=0){
            MySurfaceView.GAME_STATE =3;

        }

        if (noCollision) {
            noCollisionCount++;
            if (noCollisionCount % 10 == 0) {
                Log.e("&&&&&", "****");
                canvas.drawBitmap(bitmap, x, y, paint);//飞机闪烁
            }
            if (noCollisionCount > 100) {//无敌时间
                noCollision = false;
                noCollisionCount = 0;
            }

        } else {
            //非无敌状态
            canvas.drawBitmap(bitmap, x, y, paint);

        }

        for (int i = 0; i < hp; i++) {
            canvas.drawBitmap(bitmapHp, i * bitmapHp.getWidth(), MySurfaceView.height - bitmapHp.getHeight(), paint);

        }

    }


    public void touchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float ex = event.getX();
            float ey = event.getY();
            if (ex > x && ex < x + width && ey > y && ey < y + height) {
                x = (int) ex - width / 2;
                y = (int) ey - height / 2;
                if (y < 0) {
                    y = 0;
                }
                if (y + height > MySurfaceView.height) {
                    y = MySurfaceView.height - height;
                }
            }
            if (x < 0) {
                x = 0;
            }
            if (x + width > MySurfaceView.width) {
                y = MySurfaceView.width - width;
            }

        }

    }

    //飞机与子弹相撞
    public boolean isCollision(Bullet bullet) {
        if (noCollision) {
            return false;
        } else {
            if (bullet.getX() > x && bullet.getX() < x + width && bullet.getY() > y && bullet.getY() < y + height) {
                Log.e("***", "------------------");
                noCollision = true;
                if (hp > 0) {
                    hp--;
                }
                return true;
            }


        }
        return false;
    }

    //飞机与敌机相撞
    public boolean isCollision(BossPlane bossPlane) {
        if (noCollision) {
            return false;
        } else {
            if (bossPlane.getY() + bossPlane.getFrameH() > y && bossPlane.getY() + bossPlane.getFrameH() < y + height) {

                if (x < bossPlane.getX() && x + width > bossPlane.getX()) {
                    Log.e("AAAAAAAAAa", "isCollision: ...................................");
                    noCollision = true;
                    if (hp > 0) {
                        hp--;
                    }
                    return true;

                }
                if (x > bossPlane.getX() && x + width < bossPlane.getX() + bossPlane.getFrameW()) {
                    noCollision = true;
                    if (hp > 0) {
                        hp--;
                    }
                    return true;

                }
                if (x > bossPlane.getX() && x + width > bossPlane.getX() + bossPlane.getFrameW()) {
                    noCollision = true;
                    if (hp > 0) {
                        hp--;
                    }
                    return true;
                }
            }

        }
        return false;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public int getWidth() {
        return width;


    }

}
