package com.example.mac.plane006;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class GameSoundPool {
    private SoundPool soundPool;
    private int s1;
    private int s2;
    private int s3;

    public GameSoundPool(Context context){
        this.soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        s1 = soundPool.load(context,R.raw.biggun1,1);
        s2 = soundPool.load(context,R.raw.xiaobaozha,1);
        // s3 = soundPool.load(context,R.raw.bg_logobg,2);
    }
    public void playSound(int s) {
        switch (s){
            case 1:
                soundPool.play(s1,1,1,1,1,1.0f);
                break;
            case 2:
                soundPool.play(s2,1,1,1,1,1.0f);
                break;
            case 3:
                soundPool.play(s3,1,1,1,1,1.0f);
                break;
        }
    }
}


