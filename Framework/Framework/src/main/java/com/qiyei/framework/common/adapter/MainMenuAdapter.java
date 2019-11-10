package com.qiyei.framework.common.adapter;

import android.content.Context;
import android.view.View;


import com.qiyei.framework.R;
import com.qiyei.framework.common.listener.MainMenuListener;
import com.qiyei.framework.common.model.MainMenu;
import com.qiyei.sdk.view.xrecycler.base.BaseRecyclerAdapter;
import com.qiyei.sdk.view.xrecycler.base.BaseViewHolder;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2018/8/18.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MainMenuAdapter extends BaseRecyclerAdapter<MainMenu> {

    private MainMenuListener mListener;

    /**
     * @param  listener the {@link #mListener} to set
     */
    public void setListener(MainMenuListener listener) {
        mListener = listener;
    }

    public void removeListener(){
        mListener = null;
    }

    public MainMenuAdapter(Context context, List<MainMenu> datas, int layoutId) {
        super(context, datas, layoutId);
    }


    @Override
    public void convert(BaseViewHolder holder, MainMenu item, int position) {
        holder.setText(R.id.tv,item.getName());
        holder.setOnClickListener(R.id.tv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.click(v,item,position);
                }
            }
        });

    }

}
