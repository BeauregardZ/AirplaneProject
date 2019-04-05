package com.example.mac.plane006;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Vector;

/**
 * Created by mac on 2019/4/5.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {


    public static int height;
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;//绘制图形的画布
    private boolean isDrawing = true;//标志位
    public static int width;
    private MyPlane plane;
    //Vector是线程安全的，ArrayList是非线程安全的
    private Vector<Bullet> bulletVector = new Vector<>();
    private Vector<Bullet> bossbulletVector = new Vector<>();
    private Vector<Boom>boomVector = new Vector<>();
    private int count;
    private BossPlane bossPlane;
    private GameSoundPool gameSoundPool;
    public static int GAME_STATE = 4;
    private MediaPlayer mediaPlayer;


    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context,attrs);
        gameSoundPool = new GameSoundPool(getContext());
        init();

    }

    /**
     * 初始化操作
     */

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);//添加回调事件监听
        setFocusable(true);//设置可聚焦
        setKeepScreenOn(true);//设置屏幕常亮
        setFocusableInTouchMode(true);//设置触摸模式


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(this).start();//启动子线程
        height = getHeight();
        width = getWidth();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
    }

    /**
     * 实现绘图操作
     */

    @Override
    public void run() {
        MediaPlayer mediaPlayer = null;
        mediaPlayer = MediaPlayer.create(getContext(),R.raw.bgm_zhuxuanlv);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        Paint paint = new Paint();




//        BackGround1 backGround1= new BackGround1(BitmapFactory.decodeResource(getResources(), mipmap.mainmenu));
        BackGround backGround = new BackGround(BitmapFactory.decodeResource(getResources(), R.mipmap.bk));
        plane = new MyPlane(BitmapFactory.decodeResource(getResources(), R.mipmap.myplane),BitmapFactory.decodeResource(getResources(), R.mipmap.heart));
        BossPlane bossPlane = new BossPlane(BitmapFactory.decodeResource(getResources(), R.mipmap.bossplane));

        while (isDrawing) {
            count++;
            try {
                canvas = surfaceHolder.lockCanvas();//锁定（选定）画布
                canvas.drawColor(Color.WHITE);
                switch (GAME_STATE){

                    case 1:
                        backGround.draw(canvas, paint);
                        plane.draw(canvas, paint);
                        bossPlane.draw(canvas, paint);

                        if(count%30==0){
                            Bullet bullet = new Bullet(BitmapFactory.decodeResource(getResources(), R.mipmap.mybullet), plane.getX() , plane.getY(),0);
                            Bullet bullet1 = new Bullet(BitmapFactory.decodeResource(getResources(), R.mipmap.mybullet), plane.getX()+ plane.getWidth(), plane.getY(),0);
                            bulletVector.add(bullet);
                            bulletVector.add(bullet1);
                            gameSoundPool.playSound(1);


                        }




                        //移除消失的子弹
                        for (int i = 0; i < bulletVector.size(); i++) {

                            if (bulletVector.elementAt(i).isDead()) {
                                bulletVector.remove(i);

                            }
                        }
                        //绘制玩家子弹
                        for (int i = 0; i < bulletVector.size(); i++){

                            bulletVector.elementAt(i).draw(canvas, paint);
                            if (bossPlane.isCollision(bulletVector.elementAt(i))){
                                gameSoundPool.playSound(2);
                                Boom boom = new Boom(BitmapFactory.decodeResource(getResources(),R.mipmap.fire4),bossPlane.getX(),bossPlane.getY(),7);
                                boomVector.add(boom);
                            }
                        }




                        for (int i =0;i<boomVector.size();i++){
                            if (boomVector.elementAt(i).isEnd()){
                                boomVector.remove(i);
                            }else {

                                boomVector.elementAt(i).draw(canvas,paint);
                                //Log.e("..","....");
                            }
                        }


                        if(count%30==0){
                            Bullet bullet = new Bullet(BitmapFactory.decodeResource(getResources(), R.mipmap.bossbullet), bossPlane.getX() , bossPlane.getY()+bossPlane.getFrameH(),1);
                            Bullet bullet1 = new Bullet(BitmapFactory.decodeResource(getResources(), R.mipmap.bossbullet), bossPlane.getX()+ bossPlane.getFrameW(), bossPlane.getY()+bossPlane.getFrameH(),1);
                            bossbulletVector.add(bullet);
                            bossbulletVector.add(bullet1);

                        }
                        //移除boss的子弹
                        for (int i = 0; i < bossbulletVector.size(); i++) {
                            if (bossbulletVector.elementAt(i).isDead()) {
                                bossbulletVector.remove(i);

                            }
                        }
                        //绘制boss子弹
                        for (int i = 0; i < bossbulletVector.size(); i++) {
                            bossbulletVector.elementAt(i).draw(canvas, paint);
                            plane.isCollision(bossbulletVector.elementAt(i));


                        }

                        plane.isCollision(bossPlane);//一定不要忘了调用这个方法
                        break;
                    case 2://RectF矩形
                        RectF rectF0 = new RectF(0,0,getWidth(),getHeight());
                        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.gamewin),null,rectF0,paint);
                        break;
                    case 3:
                        RectF rectF = new RectF(0,0,getWidth(),getHeight());
                        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.gamelost),null,rectF,paint);
                        break;
                    case 4:
                        RectF rectF1 = new RectF(0,0,getWidth(),getHeight());
                        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.mainmenu),null,rectF1,paint);
                        RectF rectF2 = new RectF(0,getHeight()*2/5,getWidth(),getHeight()*3/5);
                        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.logo),null,rectF2,paint);
                        if(count>20){
                            MySurfaceView.GAME_STATE = 1;
                        }
                        break;


                }




            } catch(Exception e){
                e.printStackTrace();
            } finally{
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);//解锁画布，显示到屏幕上
                }

            }

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction()==MotionEvent.ACTION_MOVE){
//            Log.e("MySurfaceView","onTouchEvent:"+event.getX());
//        }else if (event.getAction() == MotionEvent.ACTION_UP){
//            Log.d("MySurfaceView","手指按起");
//        }else if (event.getAction() == MotionEvent.ACTION_DOWN){
//            Log.d("MySurfaceView","手指按下");

        plane.touchEvent(event);
        return true;//永远监听屏幕触摸事件

    }

}

