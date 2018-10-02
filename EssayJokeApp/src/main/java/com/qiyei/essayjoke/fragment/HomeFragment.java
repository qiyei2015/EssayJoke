package com.qiyei.essayjoke.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qiyei.essayjoke.R;

import com.qiyei.framework.ui.fragment.BaseFragment;
import com.qiyei.sdk.ioc.ViewById;
import com.qiyei.sdk.ioc.ViewUtils;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.view.IndicatorView.IndicatorAdapter;
import com.qiyei.sdk.view.IndicatorView.IndicatorView;

import java.util.List;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/24.
 * Version: 1.0
 * Description:
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";

    @ViewById(R.id.indicator_view)
    private IndicatorView mIndicatorView;
    @ViewById(R.id.view_pager)
    private ViewPager mViewPager;

    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华","段友秀", "同城", "游戏"};
    private List<TextView> mIndicators;


    public HomeFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        ViewUtils.inject(this,view);
        initViewPager();
        return view;
    }

    /**
     * @return {@link #TAG}
     */
    @Override
    protected String getTAG() {
        return TAG;
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {

        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
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
                LogManager.e("TAG", "position --> " + position + "  positionOffset --> "
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
                TextView colorTrackTextView = new TextView(mContext);
                colorTrackTextView.setWidth(400);
                // 设置颜色
                colorTrackTextView.setTextSize(14);
                colorTrackTextView.setGravity(Gravity.CENTER);
                // colorTrackTextView.setChangeColor(Color.RED);
                colorTrackTextView.setText(items[position]);
                colorTrackTextView.setTextColor(Color.BLACK);
                //colorTrackTextView.setBackgroundColor(Color.BLUE);
                int padding = 20;
                colorTrackTextView.setPadding(padding,padding,padding,padding);
                //  mIndicators.add(colorTrackTextView);
                return colorTrackTextView;

            }

            @Override
            public void highLightIndicator(View view) {
                TextView textView = (TextView) view;
                textView.setTextColor(Color.RED);
                LogManager.d(getTAG(),"highLightIndicator,textView:" + Color.RED);
            }

            @Override
            public void restoreIndicator(View view) {
                TextView textView = (TextView) view;
                textView.setTextColor(Color.BLACK);
                LogManager.d(getTAG(),"restoreIndicator,textView:" + Color.BLACK);
            }

            @Override
            public View getBottomTrackView() {
                View view = new View(mContext);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(150,6);
                view.setLayoutParams(params);
                view.setBackgroundColor(Color.RED);

                return view;
            }
        },mViewPager);
    }

}
