package com.example.mac.plane006;

/**
 * Created by mac on 2019/4/5.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class BossPlane {
    private Bitmap bitmap;
    private int x, y;
    private int frameW, frameH;
    private int speed = 10;
    private int count;//计数器
    private int time = 100;//疯狂模式间隔时间
    private boolean isCrazy;
    private int crazySpeed = 50;


    private int bossHp = 10;


    public BossPlane(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.frameW = bitmap.getWidth() / 10;
        this.frameH = bitmap.getHeight();
        x = MySurfaceView.width / 2 - frameW / 2;


    }


    public void draw(Canvas canvas, Paint paint) {
        canvas.save();
        canvas.clipRect(x, y, x + frameW, y + frameH);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
        logic();
    }

    public void logic() {
        count++;
        //疯狂模式
        if (isCrazy) {
            y = y + crazySpeed;
            crazySpeed--;
            if (y == 0) {
                isCrazy = false;
                crazySpeed = 50;

            }

        } else {
            if (count % time == 0) {
                isCrazy = true;

            }
            x = x + speed;
            if (x > MySurfaceView.width - frameW) {
                speed = -speed;

            }
            if (x < 0) {
                speed = -speed;

            }
        }

    }








    public boolean isCollision(Bullet bullet) {
        if (bullet.getX()>x&&bullet.getX()+bullet.getBitmap().getWidth()<x+frameW&&bullet.getY()>y&&bullet.getY()<y+frameH){
            bossHp--;

            bullet.setDead(true);//设置子弹状态，碰撞后修改子弹状态为dead，从数组中移除
            if (bossHp<0){
                MySurfaceView.GAME_STATE =2;
            }
            return true;


        }

        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getFrameW() {
        return frameW;
    }

    public int getFrameH() {
        return frameH;
    }

}
