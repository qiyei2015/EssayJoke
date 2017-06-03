package com.qiyei.sdk.view.xrecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.qiyei.sdk.R;


/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/24.
 * Version: 1.0
 * Description:
 */
public class CommonRefreshView implements IViewCreator {
    // 加载数据的ImageView
    private View mRefreshIv;

    @Override
    public View getView(Context context, ViewGroup parent) {
        View refreshView = LayoutInflater.from(context).inflate(R.layout.layout_refresh_header_view, parent, false);
        mRefreshIv = refreshView.findViewById(R.id.refresh_iv);
        return refreshView;

    }

    @Override
    public void onPull(int currentHeight, int refreshHeight, XStatus status) {
        float rotate = ((float) currentHeight) / refreshHeight;
        // 不断下拉的过程中不断的旋转图片
        mRefreshIv.setRotation(rotate * 360);
    }

    @Override
    public void onRunning() {
        // 刷新的时候不断旋转
        RotateAnimation animation = new RotateAnimation(0, 720,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setRepeatCount(-1);
        animation.setDuration(1000);
        mRefreshIv.startAnimation(animation);
    }

    @Override
    public void onStopRunning() {
        // 停止加载的时候清除动画
        mRefreshIv.setRotation(0);
        mRefreshIv.clearAnimation();
    }

}
