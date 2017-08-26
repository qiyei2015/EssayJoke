package com.qiyei.appdemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.qiyei.framework.activity.BaseSkinActivity;
import com.qiyei.framework.titlebar.CommonTitleBar;
import com.qiyei.sdk.util.ToastUtil;
import com.qiyei.sdk.view.ColorTrackTextView;

import java.util.ArrayList;
import java.util.List;

import com.qiyei.appdemo.R;
import com.qiyei.appdemo.fragment.ItemFragment;

public class ColorTrackTextViewActivity extends BaseSkinActivity {
    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华"};
    private LinearLayout mIndicatorContainer;// 变成通用的
    private List<ColorTrackTextView> mIndicators;
    private ViewPager mViewPager;
    private String TAG = "ViewPagerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_color_track_text_view);
    }

    @Override
    protected void initView() {
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
    protected void initData() {
        mIndicators = new ArrayList<>();
        mIndicatorContainer = (LinearLayout) findViewById(R.id.indicator_view);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        initIndicator();
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
                // 滚动的过程中会不断的回掉
                // LogManager.e("TAG", "position --> " + position + "  positionOffset --> "
                //         + positionOffset + " positionOffsetPixels --> " + positionOffsetPixels);

                ColorTrackTextView left = mIndicators.get(position);
                left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                left.setCurrentProgress(1-positionOffset);

                try{
                    ColorTrackTextView right = mIndicators.get(position+1);
                    right.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
                    right.setCurrentProgress(positionOffset);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageSelected(int position) {
                // 选中毁掉
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
            // 设置颜色
            colorTrackTextView.setTextSize(20);
            colorTrackTextView.setChangeColor(Color.RED);
            colorTrackTextView.setText(items[i]);
            colorTrackTextView.setLayoutParams(params);
            // 把新的加入LinearLayout容器
            mIndicatorContainer.addView(colorTrackTextView);
            // 加入集合
            mIndicators.add(colorTrackTextView);
        }
    }

}
