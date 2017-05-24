package com.qiyei.essayjoke.test;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.qiyei.baselibrary.view.xrecyclerview.BaseViewHolder;
import com.qiyei.baselibrary.view.xrecyclerview.CategoryItemDecoration;
import com.qiyei.baselibrary.view.xrecyclerview.XRecyclerAdapter;
import com.qiyei.baselibrary.view.xrecyclerview.XRecyclerView;
import com.qiyei.essayjoke.R;
import com.qiyei.framework.view.CommonRefreshView;


import java.util.ArrayList;
import java.util.List;

public class RecyclerViewTestActivity extends AppCompatActivity implements XRecyclerView.XRecyclerViewListener{

    private XRecyclerView mRecyclerView;

    private List<String> mDatas;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                mRecyclerView.stopRefrsh();
                return;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_test);

        mRecyclerView = (XRecyclerView) findViewById(R.id.recycler_view);
        initData();

        initView();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new CategoryItemDecoration(getDrawable(R.drawable.recyclerview_decoration)));
        //mRecyclerView.setAdapter(new CommonAdapter(this,R.layout.recyclerview_item));
        mRecyclerView.setXRecyclerViewListener(this);
        mRecyclerView.setAdapter(new XRecyclerAdapter<String>(this,mDatas,R.layout.recyclerview_item) {
            @Override
            public void convert(BaseViewHolder holder, String data, int position) {

                holder.setText(R.id.tv,data)
                        .setTextColor(R.id.tv,Color.RED)
                        .setTextSize(R.id.tv,28);
            }
        });

        mRecyclerView.addRefreshViewCreator(new CommonRefreshView());

        for (int i = 0;i < 5 ;i++){
            TextView header = new TextView(this);
            header.setText("头部 " + i);
            header.setTextSize(30);

            TextView footer = new TextView(this);
            footer.setText("底部 "+ i);
            footer.setTextSize(30);

            mRecyclerView.addHeaderView(header);
            mRecyclerView.addFooterView(footer);
        }

    }

    @Override
    public void onRefresh() {
        Message msg = Message.obtain();
        msg.what = 1;
        mHandler.sendMessageDelayed(msg,3000);
    }

    @Override
    public void onLoadMore() {

    }

    private void initData(){

        mDatas = new ArrayList<>();
        for (int i = 0;i < 50;i++){
            String s = new String("测试 " + i);
            mDatas.add(s);
        }
    }

}
