package com.qiyei.sdk.view.IndicatorView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.qiyei.sdk.R;
import com.qiyei.sdk.log.LogManager;


/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/15.
 * Version: 1.0
 * Description: 继承水平滚动的View
 */
public class IndicatorView extends HorizontalScrollView implements ViewPager.OnPageChangeListener{
    private static final String TAG = IndicatorView.class.getSimpleName();
    /**
     * 自定义适配器
     */
    private IndicatorAdapter mAdapter;
    /**
     * item的容器，包含底部的指示器
     */
    private IndicatorGroupView mIndicatorContainer;
    /**
     * 每页显示的item个数
     */
    private int mVisiableNum = 0;
    /**
     * 每个item宽度
     */
    private int mItemWidth = 0;

    /**
     * 指示器的ViewPager
     */
    private ViewPager mViewPager;
    /**
     * 当前的indicatorView
     */
    private int mCurrentPosition;
    /**
     * 是否在滚动
     */
    private boolean isScroll;


    public IndicatorView(Context context) {
        this(context,null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);
        mVisiableNum = array.getInteger(R.styleable.IndicatorView_visiableNum,0);
        array.recycle();

        mIndicatorContainer = new IndicatorGroupView(context);
        mIndicatorContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        //将mIndicatorContainer添加成自己的child
        addView(mIndicatorContainer);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && mItemWidth == 0 && mAdapter != null){
            mItemWidth = getItemWidth();
            int count = mAdapter.getCount();
            //依次指定每个item的宽度为mItemWidth
            for (int i = 0 ; i < count ;i++){
                ViewGroup.LayoutParams params = mIndicatorContainer.getItemView(i).getLayoutParams();
                params.width = mItemWidth;
                mIndicatorContainer.getItemView(i).setLayoutParams(params);
            }

            //添加底部的指示器
            View bootomTrackView = mAdapter.getBottomTrackView();
            if (bootomTrackView != null){
                float w = bootomTrackView.getWidth() * 1.0f;
                if (w <= 0f){
                    w = getItemWidth();
                }else if ( getItemWidth() * 1.5f < w){
                    w = getItemWidth() * 1.5f;
                }else {
                    w = bootomTrackView.getWidth();
                }
                mIndicatorContainer.addBottomTrackView(bootomTrackView,(int)w);
            }

            LogManager.d(TAG, "mItemWidth -> " + mItemWidth);
        }
    }

    /**
     * 采用对象适配器模式
     */
    private void setAdapter(IndicatorAdapter adapter){
        if (adapter == null){
            throw new NullPointerException("adapter cannot be null");
        }

        this.mAdapter = adapter;

        int count = mAdapter.getCount();

        //动态添加到mIndicatorContainer中
        for (int i = 0;i < count; i++){
            View itemView = mAdapter.getView(i,mIndicatorContainer);
            mIndicatorContainer.addItemView(itemView);
            switchIndicatorClick(itemView,i);
        }
    }

    /**
     *
     * @param adapter 适配器
     * @param viewPager 联动的ViewPager
     */
    public void setAdapter(IndicatorAdapter adapter, ViewPager viewPager){
        setAdapter(adapter);

        mViewPager = viewPager;
        //设置监听改变
        mViewPager.addOnPageChangeListener(this);
        //高亮当前item指示器
        highLightIndicator(viewPager.getCurrentItem());
    }

    /**
     * page滚动
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        LogManager.d(TAG,"position:" + position + ",positionOffset" + positionOffset);

//        //滚动的时候让当前头部的item一直保持在最中心
//        indicatorScrollTo(position,positionOffset);
        if (isScroll){
            indicatorScrollTo(position,positionOffset);
            mIndicatorContainer.scrollBottomTrack(position,positionOffset);
        }
    }

    /**
     * 选择page
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        View lastView = mIndicatorContainer.getItemView(mCurrentPosition);
        if (lastView != null){
            mAdapter.restoreIndicator(lastView);
        }
        //更新mCurrentPosition
        mCurrentPosition = position;
        highLightIndicator(position);
    }

    /**
     * page状态改变
     * @param state
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 0){
            isScroll = false;
        }else if (state == 1){
            isScroll = true;
        }
    }

    /**
     * 获取item的宽度
     * @return
     */
    private int getItemWidth() {
        int itemWidth = 0;
        int maxItemWidth = 0;

        /**
         * 如果指定了每页的个数，则宽度就是总宽度 / 个数
         */
        if (mVisiableNum != 0){
            itemWidth = getWidth() / mVisiableNum;
            return itemWidth;
        }

        /**
         * 如果没指定，就是所有item宽度中最大的一个
         */
        for ( int i = 0; i < mAdapter.getCount() ; i++){
            int width = mIndicatorContainer.getItemView(i).getMeasuredWidth();
            itemWidth = Math.max(width,itemWidth);
            maxItemWidth += width;
        }

        /**
         * 如果所有的宽度都不够一个屏幕宽度，就itemWidth = getWidth / count
         */
        if (maxItemWidth < getWidth()){
            itemWidth = getWidth() / mAdapter.getCount();
        }
        return itemWidth;
    }

    /**
     * 滚动到最中心
     * @param position
     * @param positionOffset
     */
    private void indicatorScrollTo(int position, float positionOffset) {
        mItemWidth = getItemWidth();

        //当前距离第0个最左边偏移量
        int currentOffset = (int)((position + positionOffset) * mItemWidth);
        //滚到中心时，屏幕的偏移量
        int screenOffset = (getWidth() - mItemWidth) / 2;
        //需要滚动的距离
        int offset = currentOffset - screenOffset;
        //滚动
        scrollTo(offset,0);
    }

    /**
     * 点击Indicator 对应的itemView fragment 做对应的切换
     * @param itemView
     * @param i
     */
    private void switchIndicatorClick(View itemView, final int i) {
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewPager != null){
                    mViewPager.setCurrentItem(i);
                }
                indicatorScrollTo(i,0);
                mIndicatorContainer.scrollBottomTrack(i);
            }
        });
    }

    /**
     * 高亮position的indicator
     * @param position
     */
    private void highLightIndicator(int position){
        View itemView = mIndicatorContainer.getItemView(position);
        mAdapter.highLightIndicator(itemView);
    }

}
