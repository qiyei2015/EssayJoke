package com.qiyei.sdk.view.banner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/25.
 * Version: 1.0
 * Description:
 */
public abstract class BannerPageAdapter extends PagerAdapter {

    /**
     * item点击回调的监听
     */
    private BannerItemClickListener mListener;

    @Override
    public boolean isViewFromObject(View view, Object object) {
        //官方建议
        return view == object;
    }

    /**
     * 创建ViewPager条目回调的方法
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = getView(position % getCount());
        container.addView(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.click(position);
                }
            }
        });

        return itemView;
    }

    /**
     * 销毁条目回调的方法
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
        object = null;
    }

    /**
     * @param listener the {@link #mListener} to set
     */
    public void setItemClickListener(BannerItemClickListener listener) {
        mListener = listener;
    }

    /**
     * 获取对应位置的Banner描述
     * @param position
     * @return
     */
    public String getBannerDesc(int position){
        return "";
    }

    /**
     * 获取View
     * @param position
     * @return
     */
    public abstract View getView(int position);

    /**
     * 返回数量
     * @return
     */
    public abstract int getCount();
    
}
