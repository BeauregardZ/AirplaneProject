package com.example.mac.plane006;

/**
 * Created by mac on 2019/4/5.
 */
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet {

    private Bitmap bitmap;
    private int x,y;
    private int speed =10;
    public boolean isDead;
    private int type;


    public Bullet(Bitmap bitmap,int x,int y,int type){
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.type = type;



    }
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(bitmap,x,y,paint);
        logic();


    }
    public void logic(){
        switch (type){
            case 0:
                y-=speed;
                if (y<0){
                    isDead = true;
                }
                break;

            case 1:
                y+=speed+5;
                if (y>MySurfaceView.height){
                    isDead = true;
                }

                break;

        }

    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDead() {
        return isDead;


    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
