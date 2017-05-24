package com.qiyei.essayjoke.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiyei.essayjoke.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/18.
 * Version: 1.0
 * Description:
 */
public class CommonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mDatas = new ArrayList<>();
    private Context mContext;
    private int mLayoutId;

    public CommonAdapter(Context context ,int layoutId) {
        mContext = context;
        mLayoutId = layoutId;

        for (int i = 0;i < 50;i++){
            String s = new String("测试 " + i);
            mDatas.add(s);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutId,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder h = (ViewHolder) holder;
        h.textView.setTextSize(28);
        h.textView.setTextColor(Color.RED);
        h.textView.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
        }
    }




}
