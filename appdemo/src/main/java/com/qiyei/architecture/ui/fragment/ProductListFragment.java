package com.qiyei.architecture.ui.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiyei.appdemo.R;
import com.qiyei.architecture.adapter.ProductAdapter;

import com.qiyei.architecture.model.Product;
import com.qiyei.architecture.viewmodel.ProductListViewModel;
import com.qiyei.architecture.viewmodel.ProductViewModel;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.util.ToastUtil;
import com.qiyei.sdk.view.xrecycler.base.OnItemClickListener;


import java.util.ArrayList;
import java.util.List;


/**
 * @author Created by qiyei2015 on 2018/1/13.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ProductListFragment extends Fragment {

    public static final String TAG = "TTT ProductListFragment ";

    private TextView mTextView;
    private RecyclerView mRecyclerView;

    private ProductListViewModel mViewModel;

    ProductAdapter productAdapter;

    public ProductListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        LogManager.i(TAG,"onAttach");

        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogManager.i(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProductListViewModel.class);
        mViewModel.init("2");

        mViewModel.getProducts("id").observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                productAdapter.setDatas(products);
                mTextView.setText("测试 :" + products.size());
            }
        });

        ProductViewModel productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getLiveData().observe(this, new Observer<Product>() {
            @Override
            public void onChanged(@Nullable Product product) {

            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogManager.i(TAG,"onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_product_list,container,false);
        mTextView = rootView.findViewById(R.id.tv1);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.setTest("2");
            }
        });

        productAdapter = new ProductAdapter(getContext(),new ArrayList<>(),R.layout.recyclerview_item);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(productAdapter);
        productAdapter.setOnItemClickListener(new OnItemClickListener<Product>() {
            @Override
            public void click(View view, Product product,int position) {
                ToastUtil.showShortToast(product.getName());
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LogManager.i(TAG,"onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        LogManager.i(TAG,"onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        LogManager.i(TAG,"onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        LogManager.i(TAG,"onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        LogManager.i(TAG,"onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        LogManager.i(TAG,"onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        LogManager.i(TAG,"onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        LogManager.i(TAG,"onDetach");
        super.onDetach();
    }

}
