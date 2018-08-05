package com.qiyei.video.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qiyei.video.R;


/**
 * @author Created by qiyei2015 on 2018/8/1.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 自定义的VideoView用于实现视频播放
 */
public class CustomVideoView extends RelativeLayout implements View.OnClickListener
        ,MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener,MediaPlayer.OnCompletionListener
        ,MediaPlayer.OnBufferingUpdateListener,TextureView.SurfaceTextureListener{

    private static final String TAG = "CustomVideoView";

    private static final int TIME_MSG = 0x01;
    private static final int TIME_INVAL = 1000;
    /**
     * 重试次数
     */
    private static final int LOAD_TOTAL_COUNT = 3;

    private static final float VIDEO_HEIGHT_PERCENT = 0.5625f;

    /**
     * 生命周期
     */
    enum LifeCycle{
        IDLE,PLAY,PAUSE,ERROR
    }


    private Context mContext;
    /**
     * 父布局容器
     */
    private ViewGroup mParentContainer;
    /**
     *
     */
    private RelativeLayout mPlayerView;
    /**
     * 播放器View
     */
    private TextureView mVideoView;
    /**
     * 最小化按钮？
     */
    private Button mMiniPlayBtn;
    /**
     * 全屏按钮
     */
    private ImageView mFullBtn;
    /**
     * 进度条
     */
    private ImageView mLoadingBar;
    /**
     * ?
     */
    private ImageView mFrameView;
    /**
     * 音频管理器
     */
    private AudioManager mAudioManager;
    /**
     * 媒体播放器
     */
    private MediaPlayer mMediaPlayer;
    /**
     * video
     */
    private Surface videoSurface;

    /**
     * 播放视频源地址
     */
    private String mUri;
    /**
     *
     */
    private String mFrameURI;
    /**
     * 是否静音
     */
    private boolean isMute;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * 目标高度？
     */
    private int mDestationHeight;


    /**
     * 是否能播放
     */
    private boolean canPlay = true;
    /**
     * 是否真正暂停
     */
    private boolean isRealPause;
    /**
     * 是否完成
     */
    private boolean isComplete;
    /**
     * 当前进度
     */
    private int mCurrentCount;
    /**
     * 播放状态
     */
    private LifeCycle playerState = LifeCycle.IDLE;


    /**
     * 视频播放监听器
     */
    private VideoPlayerListener mListener;

    /**
     * 屏幕事件监听
     */
    private ScreenEventReceiver mScreenReceiver;


    /**
     * 进度更新处理
     */
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME_MSG:
                    if (isPlaying()) {
                        //正在播放,回调进度
                        mListener.onBufferUpdate(getCurrentPosition());
                        sendEmptyMessageDelayed(TIME_MSG,TIME_INVAL);
                    }
                    break;
                default:
                    break;
            }
        }
    };



    public CustomVideoView(Context context,ViewGroup parentContainer) {
        super(context);
        mContext = context;
        mParentContainer = parentContainer;
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        initData();
        initView();
        registerReceiver();
    }

    /**
     * 数据初始化
     */
    private void initData(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
        mDestationHeight = (int)(mScreenWidth *  VIDEO_HEIGHT_PERCENT);
    }

    /**
     * 初始化View
     */
    private void initView(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mPlayerView = (RelativeLayout) inflater.inflate(R.layout.video_player_layout,this);
        mVideoView = (TextureView) mPlayerView.findViewById(R.id.textureView);
        mVideoView.setOnClickListener(this);
        //屏幕常亮
        mVideoView.setKeepScreenOn(true);
        mVideoView.setSurfaceTextureListener(this);
        //初始化为小布局
        initSmallLayoutMode();
    }


    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer = mp;
    }

    /**
     * TextureView回调
     * @param surface
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * 消耗事件
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE && playerState == LifeCycle.PAUSE) {
            if (isRealPause() || isComplete()) {
                pause();
            } else {
                decideCanPlay();
            }
        } else {
            pause();
        }
    }

    /**
     * 暂停
     */
    public void pause(){

    }

    /**
     * 恢复播放
     */
    public void resume(){

    }

    /**
     * 停止播放
     */
    public void stop(){

    }


    /**
     * 设置是否静音
     * @param mute
     */
    public void setMute(boolean mute){
        isMute = mute;
        if (mMediaPlayer != null && mAudioManager != null){
            float volume = isMute ? 0.0f : 1.0f;
            //设置左右声道音量
            mMediaPlayer.setVolume(volume,volume);
        }
    }

    /**
     * 显示全屏按钮
     */
    public void showFullButton(boolean show){
        mFullBtn.setImageResource(show ? R.drawable.video_ad_mini:R.drawable.video_ad_mini_null);
        mFullBtn.setVisibility(show ? View.VISIBLE:View.GONE);
    }

    /**
     * 真的停止
     * @return
     */
    public boolean isRealPause(){
        return isRealPause;
    }

    /**
     * 是否播放完成
     * @return
     */
    public boolean isComplete(){
        return isComplete;
    }


    /**
     * 决定是否可以开始播放 开始播放？
     */
    private void decideCanPlay(){

    }


    /**
     * 是否正在播放
     * @return
     */
    private boolean isPlaying(){

        return true;
    }

    /**
     * 获取当前位置
     * @return
     */
    private int getCurrentPosition(){

        return 0;
    }

    /**
     * 初始化为小布局
     */
    private void initSmallLayoutMode(){
        LayoutParams params = new LayoutParams(mScreenWidth,mDestationHeight);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        //居中在父容器
        mPlayerView.setLayoutParams(params);

        mMiniPlayBtn = (Button) mPlayerView.findViewById(R.id.video_small_play_btn);
        mFullBtn = (ImageView) mPlayerView.findViewById(R.id.video_to_full_image_view);
        mFrameView = (ImageView) mPlayerView.findViewById(R.id.video_frame_view);
        mMiniPlayBtn.setOnClickListener(this);
        mFullBtn.setOnClickListener(this);
    }




    /**
     * 初始化广播接收器
     */
    private void registerReceiver() {
        if (mScreenReceiver == null) {
            mScreenReceiver = new ScreenEventReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            //解锁唤醒广播
            filter.addAction(Intent.ACTION_USER_PRESENT);
            mContext.registerReceiver(mScreenReceiver, filter);
        }
    }

    /**
     * 取消个广播接收器
     */
    private void unRegisterReceiver() {
        if (mScreenReceiver != null) {
            getContext().unregisterReceiver(mScreenReceiver);
        }
    }



    /**
     * 监听锁屏事件的广播接收器
     */
    private class ScreenEventReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //主动锁屏时 pause, 主动解锁屏幕时，resume
            switch (intent.getAction()) {
                case Intent.ACTION_USER_PRESENT:
                    if (playerState == LifeCycle.PAUSE) {
                        if (isRealPause) {
                            //手动点的暂停，回来后还暂停
                            pause();
                        } else {
                            decideCanPlay();
                        }
                    }
                    break;
                case Intent.ACTION_SCREEN_OFF:
                    if (playerState ==  LifeCycle.PLAY) {
                        pause();
                    }
                    break;
            }
        }
    }


    /**
     * 视频播放监听
     */
    public interface VideoPlayerListener {
        /**
         * buffer更新
         * @param time
         */
        void onBufferUpdate(int time);


    }

    /**
     * 广告加载监听器
     */
    public interface ADLoadListener {

        void onStartLoad(String url, ImageLoaderListener listener);
    }

    public interface ImageLoaderListener {
        /**
         * 如果图片下载不成功，传null
         *
         * @param loadedImage
         */
        void onLoadingComplete(Bitmap loadedImage);
    }
}
