package com.example.mac.plane006;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by mac on 2019/4/5.
 */

public class BackGround {
    private int y1;
    private  int y2;
    private Bitmap bitmap ;
    public BackGround( Bitmap bitmap){
        this.bitmap = bitmap;
        y1 = 0;
        y2 = y1 - bitmap.getHeight();

    }



    public void draw(Canvas canvas, Paint paint){
        logic();
        canvas.drawBitmap(bitmap,0,y1,paint);
        canvas.drawBitmap(bitmap,0,y2,paint);

    }

    public void logic(){
        y1+=5;
        y2+=5;
        if (y1>MySurfaceView.height){
            y1 = y2 - bitmap.getHeight();//移动到第二张图片的顶部

        }
        if (y2>MySurfaceView.height){
            y2 = y1 - bitmap.getHeight();

        }

    }
}
