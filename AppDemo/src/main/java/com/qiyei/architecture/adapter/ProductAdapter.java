package com.qiyei.architecture.adapter;

import android.content.Context;

import com.qiyei.appdemo.R;
import com.qiyei.architecture.model.Product;
import com.qiyei.sdk.view.xrecycler.base.BaseRecyclerAdapter;
import com.qiyei.sdk.view.xrecycler.base.BaseViewHolder;

import java.util.List;


/**
 * @author Created by qiyei2015 on 2018/1/13.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ProductAdapter extends BaseRecyclerAdapter<Product> {

    public ProductAdapter(Context context, List<Product> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, Product item, int position) {
        holder.setText(R.id.tv,item.getId() + "  " + item.getName());
    }

}
