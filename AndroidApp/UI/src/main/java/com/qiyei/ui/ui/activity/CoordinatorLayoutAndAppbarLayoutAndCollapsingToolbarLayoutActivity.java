package com.qiyei.ui.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.ui.R;

public class CoordinatorLayoutAndAppbarLayoutAndCollapsingToolbarLayoutActivity extends AppCompatActivity {

    private static final String TAG = "CoordinatorLayoutAndAppbarLayoutAndCollapsingToolbarLayoutActivity";

    Toolbar mToolbar;
    AppBarLayout mAppBarLayout;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    LinearLayout mLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout_and_appbar_layout_and_collapsing_toolbar_layout);

        mAppBarLayout = findViewById(R.id.app_bar_layout);
        mCollapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        mToolbar = findViewById(R.id.toolbar);

        mLinearLayout = findViewById(R.id.ll_content);

        mToolbar.setTitle("测试CoordinatorLayout2");
        mToolbar.setTitleTextColor(Color.RED);

        setSupportActionBar(mToolbar);

        setAvatorChange();
        initContentView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getSupportActionBar().setDisplayShowTitleEnabled(true);
//        LogManager.i(TAG,"show=" + getSupportActionBar().isShowing());
//        LogManager.i(TAG,"show Title=" + getSupportActionBar().getTitle().toString());
    }

    /**
     * 渐变toolbar背景
     */
    private void setAvatorChange() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //verticalOffset始终为0以下的负数
                float percent = (Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange());

                //mToolbar.setBackgroundColor(changeAlpha(Color.WHITE,percent));
            }
        });

    }

    /** 根据百分比改变颜色透明度 */
    private int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

    private void initContentView(){
        for (int i = 0 ;i < 50 ;i++){
            TextView textView = new TextView(this);
            textView.setText("内容-->" + i);
            mLinearLayout.addView(textView);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
            params.setMargins(20,20,20,20);
        }
    }
}
