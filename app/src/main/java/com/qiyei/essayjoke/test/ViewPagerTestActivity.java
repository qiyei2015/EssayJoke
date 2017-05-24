package com.qiyei.essayjoke.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiyei.baselibrary.util.ToastUtil;
import com.qiyei.baselibrary.view.ColorTrackTextView;
import com.qiyei.baselibrary.view.IndicatorView.IndicatorAdapter;
import com.qiyei.baselibrary.view.IndicatorView.IndicatorView;
import com.qiyei.essayjoke.R;
import com.qiyei.essayjoke.fragment.ItemFragment;
import com.qiyei.framework.activity.BaseSkinActivity;
import com.qiyei.framework.titlebar.CommonTitleBar;


import java.util.ArrayList;
import java.util.List;

public class ViewPagerTestActivity extends BaseSkinActivity {

    private static final String TAG = ViewPagerTestActivity.class.getSimpleName();

    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华","段友秀", "同城", "游戏"};
    //private String[] items = {"直播", "推荐", "视频"};
    private IndicatorView mIndicatorView;// 变成通用的
    //private List<ColorTrackTextView> mIndicators;
    private List<TextView> mIndicators;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_view_pager_test);
    }

    @Override
    protected void initTitle() {
        CommonTitleBar commonNavigationBar = new CommonTitleBar.Builder(this)
                .setTitle("主界面")
                .setRightText("投稿")
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showLongToast("点击了右边");
                    }
                })
                .build();
    }

    @Override
    protected void initView() {
        mIndicatorView = (IndicatorView) findViewById(R.id.indicator_view);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    @Override
    protected void initData() {
        mIndicators = new ArrayList<>();
        //initIndicator();
        initViewPager();
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }
        });

        // 监听ViewPager的滚动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                 //滚动的过程中会不断的回掉
                 Log.e("TAG", "position --> " + position + "  positionOffset --> "
                         + positionOffset + " positionOffsetPixels --> " + positionOffsetPixels);

//                ColorTrackTextView left = mIndicators.get(position);
//                left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
//                left.setCurrentProgress(1-positionOffset);
//
//                try{
//                    ColorTrackTextView right = mIndicators.get(position+1);
//                    right.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
//                    right.setCurrentProgress(positionOffset);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onPageSelected(int position) {
                // 选中毁掉
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mIndicatorView.setAdapter(new IndicatorAdapter(){
            @Override
            public int getCount() {
                return items.length ;
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                TextView colorTrackTextView = new TextView(ViewPagerTestActivity.this);
                colorTrackTextView.setWidth(400);
                // 设置颜色
                colorTrackTextView.setTextSize(14);
                colorTrackTextView.setGravity(Gravity.CENTER);
                // colorTrackTextView.setChangeColor(Color.RED);
                colorTrackTextView.setText(items[position]);
                colorTrackTextView.setTextColor(Color.BLACK);
                colorTrackTextView.setBackgroundColor(Color.BLUE);
                int padding = 20;
                colorTrackTextView.setPadding(padding,padding,padding,padding);
                //  mIndicators.add(colorTrackTextView);
                return colorTrackTextView;

            }

            @Override
            public void highLightIndicator(View view) {
                TextView textView = (TextView) view;
                textView.setTextColor(Color.RED);
                Log.d(TAG,"highLightIndicator,textView:" + Color.RED);
            }

            @Override
            public void restoreIndicator(View view) {
                TextView textView = (TextView) view;
                textView.setTextColor(Color.BLACK);
                Log.d(TAG,"restoreIndicator,textView:" + Color.BLACK);
            }

            @Override
            public View getBottomTrackView() {
                View view = new View(ViewPagerTestActivity.this);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(300,50);
                view.setLayoutParams(params);
                view.setBackgroundColor(Color.GREEN);

                return view;
            }
        },mViewPager);

    }

    /**
     * 初始化可变色的指示器
     */
    private void initIndicator() {
        for (int i = 0; i < items.length; i++) {
            // 动态添加颜色跟踪的TextView
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            //params.setMargins(20,0,0,0);
            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
            // 设置颜色
            colorTrackTextView.setTextSize(20);
            colorTrackTextView.setGravity(Gravity.CENTER);
            colorTrackTextView.setBackgroundColor(Color.BLUE);
            colorTrackTextView.setChangeColor(Color.RED);
            colorTrackTextView.setWidth(400 );
            colorTrackTextView.setText(items[i]);
            colorTrackTextView.setLayoutParams(params);
            // 把新的加入LinearLayout容器
            //mIndicatorView.addView(colorTrackTextView);
            // 加入集合
            mIndicators.add(colorTrackTextView);
        }
    }


}
