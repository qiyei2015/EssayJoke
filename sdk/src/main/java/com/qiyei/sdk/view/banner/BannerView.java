package com.qiyei.sdk.view.banner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiyei.sdk.log.LogUtil;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/26.
 * Version: 1.0
 * Description:
 */
public class BannerView extends FrameLayout {
    /**
     * 调试用的标志
     */
    public static final String TAG = BannerViewPager.TAG;
    /**
     * ViewPager
     */
    private BannerViewPager mViewPager;
    /**
     * 包含TextView 和DotContainer的子View
     */
    private RelativeLayout mChildLayout;
    /**
     * 包含指示器Dot的LinearLayout
     */
    private LinearLayout mDotContainer;
    /**
     * Banner的描述TextView
     */
    private TextView mDescTextView;
    /**
     * Dot的Gravity
     */
    private int mDotGravity = Gravity.RIGHT;
    /**
     * 选中时的Drawable
     */
    private Drawable mFocusDrawable;
    /**
     * 未被选中时的Drawable
     */
    private Drawable mNormalDrawable;
    /**
     * 当前选中的page的index
     */
    private int mCurrentIndex = 0;

    /**
     * Context
     */
    private Context mContext;

    public BannerView(Context context) {
        this(context,null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context,attrs,defStyleAttr);
    }

    /**
     * 设置Adapter
     * @param adapter
     */
    public void setAdapter(BannerPageAdapter adapter) {
        mViewPager.setAdapter(adapter);

        initIndicator();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pageSelect(0);

        //动态指定高度 用第0个图片作为高度
        getLayoutParams().height = mViewPager.getAdapter().getView(0).getMeasuredHeight();
        LogUtil.d(TAG,"bannerView.height --> " + mViewPager.getAdapter().getView(0).getMeasuredHeight());
        requestLayout();
    }

    /**
     * 开启轮播
     */
    public void startLoop(){
        mViewPager.startLoop();
    }

    /**
     * 停止轮播
     */
    public void stopLoop(){
        mViewPager.stopLoop();
    }

    /**
     * 设置页面切换时间
     * @param time
     */
    public void setSwitchTime(int time){
        mViewPager.setSwitchTime(time);
    }

    /**
     * 设置轮播动画的持续时间
     * @param time 单位为ms
     */
    public void setDuration(int time){
        mViewPager.setDuration(time);
    }

    /**
     * 设置viewpager中的item的点击事件
     */
    public void setItemClickListener(BannerItemClickListener listener) {
        mViewPager.setItemClickListener(listener);
    }

    /**
     * 设置dot 指示器的位置
     * @param gravity
     */
    public void setDotGravity(int gravity){
        mDotGravity = gravity;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mDotContainer.getLayoutParams();
        switch (gravity){
            case Gravity.CENTER:
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                break;
            case Gravity.LEFT:
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
                break;
            case Gravity.RIGHT:
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
                break;
            default:
                break;
        }

        mDotContainer.setLayoutParams(params);
    }

    /**
     * 设置Banner的广告文字描述字体大小
     * @param size
     */
    public void setDesTextSize(int size){
        mDescTextView.setTextSize(size);
    }

    /**
     * 设置Banner的广告文字描述字体颜色
     * @param color
     */
    public void setDescTextColor(int color){
        mDescTextView.setTextColor(color);
    }

    /**
     * 初始化View 先添加一个BannerViewPager 再添加一个RelativeLayout 作为子View
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initView(Context context, AttributeSet attrs, int defStyleAttr){
        mContext = context;

        mFocusDrawable = new ColorDrawable(Color.RED);

        mNormalDrawable = new ColorDrawable(Color.WHITE);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mChildLayout = new RelativeLayout(context);
        mDotContainer = new LinearLayout(context);
        mDescTextView = new TextView(context);

        //1 先添加BannerViewPager
        mViewPager = new BannerViewPager(context);
        mViewPager.setLayoutParams(layoutParams);
        addView(mViewPager);

        //2 添加包含TextView 和DotContainer的子View的子View
        //2.1 设置mChildLayout的布局属性，并添加到FrameLayout中
        layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM ;
        mChildLayout.setLayoutParams(layoutParams);
        //设置padding
        mChildLayout.setPadding(dip2px(10),dip2px(5),dip2px(10),dip2px(5));

        mChildLayout.setBackgroundColor(Color.GRAY);
        //改变透明度
        mChildLayout.getBackground().setAlpha(70);

        addView(mChildLayout);

        //2.2 先添加mDescTextView 到mChildLayout中
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //左部
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
        mDescTextView.setLayoutParams(params);
        mChildLayout.addView(mDescTextView);

        //2.3 添加mDotContainer 默认添加到右侧
        params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //右部 垂直居中
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
        //水平排列
        mDotContainer.setOrientation(LinearLayout.HORIZONTAL);
        mDotContainer.setLayoutParams(params);
        mChildLayout.addView(mDotContainer);
    }

    /**
     * 初始化指示器
     */
    private void initIndicator(){
        //获取page的个数
        int count = mViewPager.getAdapter().getCount();
        mDotContainer.setGravity(mDotGravity);

        for (int i = 0 ; i < count ;i++){

            DotView view = new DotView(mContext);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip2px(10),dip2px(10));
            params.leftMargin = dip2px(5);
            params.rightMargin = dip2px(5);
            view.setLayoutParams(params);

            if(i == 0) {
                // 选中位置
                view.setDrawable(mFocusDrawable);
            }else{
                // 未选中的
                view.setDrawable(mNormalDrawable);
            }

            mDotContainer.addView(view);
        }

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param dpValue
     * @return
     */
    private int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 页面选中
     * @param position
     */
    private void pageSelect(int position){

        //设置之前的为Normal
        DotView oldDot = (DotView) mDotContainer.getChildAt(mCurrentIndex);
        oldDot.setDrawable(mNormalDrawable);
        //更新当前的dot为选中
        DotView newDot = (DotView) mDotContainer.getChildAt(position);
        newDot.setDrawable(mFocusDrawable);

        mCurrentIndex = position;
        //更新Banner的描述
        mDescTextView.setText(mViewPager.getAdapter().getBannerDesc(position));

    }

}
