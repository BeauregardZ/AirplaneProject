package com.example.mac.plane006;

/**
 * Created by mac on 2019/4/5.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Boom {
    private int totalFrame;
    private Bitmap bitmap;
    private int x,y;
    private int currenFrame;//当前显示的第几幅画面
    private int frameW;
    private int frameH;
    private boolean isEnd;



    public Boom(Bitmap bitmap, int x, int y,int totalFrame) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.totalFrame = totalFrame;
        frameW = bitmap.getWidth()/totalFrame;
        frameH = bitmap.getHeight();
    }


    public void draw(Canvas canvas, Paint paint){
        canvas.save();
        canvas.clipRect(x,y,x+frameW,y+frameH);
        canvas.drawBitmap(bitmap,x-currenFrame*frameW,y,paint);
        canvas.restore();
        logic();



    }
    public void logic(){
        if (currenFrame<totalFrame){
            currenFrame++;
        }else {
            isEnd = true;
        }
    }
    public boolean isEnd(){
        return isEnd;
    }

}