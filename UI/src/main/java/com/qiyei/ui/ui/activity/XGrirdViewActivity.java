package com.qiyei.ui.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.qiyei.sdk.view.XGridView;
import com.qiyei.ui.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author Created by qiyei2015 on 2018/9/9.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class XGrirdViewActivity extends AppCompatActivity {
    private Context mContext;

    private Button btn1;
    private TextView tv1;
    private XGridView xGridView;

    private List<HashMap<String,Object>> dataSourceList = new ArrayList<>();
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xgrird_view);
        initData();
        initView();
        setListener();
    }

    private void initView() {
        tv1 = (TextView) findViewById(R.id.tv1);
        btn1 = (Button) findViewById(R.id.btn1);
        xGridView = (XGridView) findViewById(R.id.x_grid_view);
        xGridView.setAdapter(adapter);
        xGridView.setOnItemChangeListener(new XGridView.OnItemChangeListener() {
            @Override
            public void onChange(int from, int to) {
                Toast.makeText(mContext,"From:" + from + "  To:" + to,Toast.LENGTH_LONG);

                HashMap<String, Object> temp = dataSourceList.get(from);
                //直接交互
                //Collections.swap(dataSourceList,from,to);

                //非直接交互 这里的处理需要注意下 排序交换
                if(from < to){
                    for(int i = from; i < to; i++){
                        Collections.swap(dataSourceList, i, i+1);
                    }
                }else if(from > to){
                    for(int i = from; i > to; i--){
                        Collections.swap(dataSourceList, i, i-1);
                    }
                }
                dataSourceList.set(to, temp);

                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initData() {
        mContext = this;
        for (int i = 0; i < 30;i++){
            HashMap<String,Object> itemMap = new HashMap<>();
            itemMap.put("item_image",R.drawable.ic_alipay_main_icon);
            itemMap.put("item_text","拖拽" + i);
            dataSourceList.add(itemMap);
        }
        adapter = new SimpleAdapter(this,dataSourceList,R.layout.item_grid_view
                ,new String[]{"item_image","item_text"},new int[]{R.id.item_imv,R.id.item_tv});
    }

    private void setListener() {
        final ValueAnimator animator = ValueAnimator.ofInt(0,400);
        animator.setDuration(1000);

        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(btn1,"translationX",20f,400f);
        objectAnimator.setDuration(1000);
        final ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(btn1,"rotation",10f,270f);
        rotateAnimator.setDuration(2000);

        final AnimatorSet set = new AnimatorSet();
        set.play(objectAnimator).with(rotateAnimator);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //animator.start();
                //objectAnimator.start();
                set.start();
            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                tv1.setTranslationX(value);
            }
        });

    }
}
