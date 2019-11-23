package com.qiyei.performance.frame;

import android.view.Choreographer;

import com.qiyei.sdk.log.LogManager;

/**
 * @author Created by qiyei2015 on 2019/11/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class FrameManager {

    private static final String TAG = "FrameManager";

    private static final long FRAME_INTERVAL = 160L;
    private static final long FRAME_INTERVAL_NANO = FRAME_INTERVAL * 1000 * 1000;
    private static long mPrevTime;
    private static int mFrameCount;


    public static void start(){

        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                if (mPrevTime == 0){
                    mPrevTime = frameTimeNanos;
                }
                long interval = frameTimeNanos - mPrevTime;
                //大于一帧
                if (interval > FRAME_INTERVAL_NANO){
                    double fps = ((double) mFrameCount * 1000 * 1000) / interval * 1000;
                    LogManager.d(TAG,"FPS=" + fps);
                    mFrameCount = 0;
                    mPrevTime = 0;
                } else {
                    mFrameCount++;
                }
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }

}
