package com.qiyei.essayjoke.adapter;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import com.qiyei.essayjoke.R;
import com.qiyei.essayjoke.model.DiscoverListResult;
import com.qiyei.sdk.view.xrecycler.IMultiTypeLayout;
import com.qiyei.sdk.view.xrecycler.XRecyclerAdapter;
import com.qiyei.sdk.view.xrecycler.base.BaseRecyclerAdapter;
import com.qiyei.sdk.view.xrecycler.base.BaseViewHolder;

import java.util.List;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/25.
 * Version: 1.0
 * Description:
 */
public class FindAdapter extends XRecyclerAdapter<DiscoverListResult.CategoriesBean.CategoryListBean>{


    public FindAdapter(Context context, List<DiscoverListResult.CategoriesBean.CategoryListBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public FindAdapter(Context context, List<DiscoverListResult.CategoriesBean.CategoryListBean> datas, IMultiTypeLayout typeLayout) {
        super(context, datas, typeLayout);
    }

    public FindAdapter(BaseRecyclerAdapter adapter) {
        super(adapter);
    }

    @Override
    public void convert(BaseViewHolder holder, DiscoverListResult.CategoriesBean.CategoryListBean item, int position) {
        holder.setText(R.id.channel_text,item.getName());
        holder.setText(R.id.channel_topic,item.getIntro());
        String numberDescStr = item.getSubscribe_count()+" 订阅 | "+"总帖数 <font color='#FF678D'>"
                +item.getTotal_updates()+"</font>";

        TextView numberDescTv = holder.getView(R.id.channel_update_info);

        numberDescTv.setText(Html.fromHtml(numberDescStr));

        holder.setImageUrl(R.id.channel_icon,item.getIcon_url());

        if(item.isIs_recommend()){
            holder.setViewVisible(R.id.recommend_label);
        }else{
            holder.setViewGone(R.id.recommend_label);
        }
    }



}
