package com.qiyei.ui.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.qiyei.ui.R;

/**
 * @author Created by qiyei2015 on 2020/1/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "MySurfaceView";

    private SurfaceHolder mSurfaceHolder;

    private Canvas mCanvas;

    private Paint mPaint;

    private int mWidth;
    private int mHeight;

    int M = 200;

    public MySurfaceView(Context context) {
        this(context,null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mWidth = getWidth();
        mHeight = getHeight();
        Log.i(TAG,"=========surfaceCreated========,width=" + mWidth + ",height=" + mHeight);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    draw();
                }
            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG,"=========surfaceChanged========");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG,"=========surfaceDestroyed========");
    }

    private void draw(){
        Log.i(TAG,"=========draw start========");
        Path path = new Path();
        //移动到最左边
        path.moveTo(0,mHeight/ 2);

        int i = 0;
        //2秒画完 2000;
        while (i < M){
            try {
                Thread.sleep(1);
                i++;
                double x = (2 * Math.PI)  * (i * 1.0 / M );
                double y = Math.sin(x) * mHeight / 4;
                Log.i(TAG,"draw y=" + y);
                double ly = mHeight / 2 - y;
                double lx = mWidth * (i * 1.0 / M );
                Log.i(TAG,"draw lx=" + lx + ",ly=" + ly);
                path.lineTo((float) lx,(float) ly);

                mCanvas = mSurfaceHolder.lockCanvas();
                //绘制背景
                mCanvas.drawColor(getResources().getColor(R.color.colorPrimary));

                mPaint.setColor(Color.BLACK);
                //绘制坐标轴
                mCanvas.drawLine((float) 0,(float) (mHeight/2),(float) (mWidth),(float) (mHeight/2),mPaint);
                mCanvas.drawLine(mWidth / 2,0,(float) (mWidth / 2),mHeight,mPaint);

                mPaint.setColor(Color.RED);
                mCanvas.drawPath(path,mPaint);
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.i(TAG,"=========draw end========");

        mCanvas = mSurfaceHolder.lockCanvas();
        //绘制背景
        mCanvas.drawColor(getResources().getColor(R.color.colorPrimary));
        mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
