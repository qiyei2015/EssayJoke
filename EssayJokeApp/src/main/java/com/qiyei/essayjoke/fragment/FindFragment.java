package com.qiyei.essayjoke.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.qiyei.essayjoke.R;
import com.qiyei.essayjoke.adapter.FindAdapter;
import com.qiyei.essayjoke.model.DiscoverListResult;
import com.qiyei.framework.ui.fragment.BaseFragment;
import com.qiyei.sdk.http.HttpManager;
import com.qiyei.sdk.http.base.HttpRequest;
import com.qiyei.sdk.http.base.INetCallback;
import com.qiyei.sdk.http.base.RequestMethod;
import com.qiyei.sdk.image.ImageManager;
import com.qiyei.sdk.ioc.ViewById;
import com.qiyei.sdk.ioc.ViewUtils;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.util.DisplayUtil;
import com.qiyei.sdk.util.ToastUtil;
import com.qiyei.sdk.view.banner.BannerAdapter;
import com.qiyei.sdk.view.banner.BannerItemClickListener;
import com.qiyei.sdk.view.banner.BannerView;
import com.qiyei.sdk.view.xrecycler.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/24.
 * Version: 1.0
 * Description:
 */
public class FindFragment extends BaseFragment {

    @ViewById(R.id.recycler_view)
    private XRecyclerView mXRecyclerView;

    private List<DiscoverListResult.DataBean.CategoriesBean.CategoryListBean> mDatas;

    private FindAdapter mAdapter;

    private BannerView mBannerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find,container,false);
        ViewUtils.inject(this,view);
        initData();
        initView();
        return view;

    }

    @Override
    public void onDetach() {
        super.onDetach();

        mBannerView.stopLoop();
    }

    @Override
    protected String getTAG() {
        return FindFragment.class.getSimpleName();
    }

    private void initView() {
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //mXRecyclerView.addItemDecoration(new CategoryItemDecoration(mContext.getDrawable(R.drawable.recyclerview_decoration)));
        mXRecyclerView.setAdapter(mAdapter);

        new HttpManager().execute(getChildFragmentManager(),buildRequest(), new INetCallback<DiscoverListResult>() {
            @Override
            public void onSuccess(DiscoverListResult result) {
                LogManager.d(getTAG(),"name --> "+result.getData().getCategories().getName());
                mAdapter.setDatas(result.getData().getCategories().getCategory_list());
                initBanner(result.getData().getRotate_banner().getBanners());
            }

            @Override
            public void onFail(Exception e) {
                LogManager.d(getTAG(),e.getMessage());
                ToastUtil.showLongToast(e.getMessage());
            }
        });

        mXRecyclerView.addHeaderView(mBannerView);
    }

    private void initData(){
        mDatas = new ArrayList<>();
        mAdapter = new FindAdapter(getContext(),mDatas,R.layout.find_list_item);
        mBannerView = new BannerView(getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(getContext(),140));
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mBannerView.setLayoutParams(layoutParams);
    }

    private void addCommonParams(Map<String,Object> params){
        params.put("app_name","joke_essay");
        params.put("version_name","5.7.0");
        params.put("ac","wifi");
        params.put("device_id","30036118478");
        params.put("device_brand","Xiaomi");
        params.put("update_version_code","5701");
        params.put("manifest_version_code","570");
        params.put("longitude","113.000366");
        params.put("latitude","28.171377");
        params.put("device_platform","android");
    }

    private HttpRequest buildRequest(){

        HttpRequest request = new HttpRequest();

        request.setUrl("http://is.snssdk.com/2/essay/discovery/v3/");
        Map<String,Object> params = new HashMap<>();

        params.put("iid","6152551759");
        params.put("aid","7");
        params.put("channel",360);

        addCommonParams(params);

        request.setParams(params);
        request.setRequestMethod(RequestMethod.GET);
        request.setUseCache(true);
        return request;
    }

    private void initBanner(final List<DiscoverListResult.DataBean.RotateBannerBean.BannersBean> list){
        mBannerView.setAdapter(new BannerAdapter() {
            @Override
            public int getCount() {
                LogManager.d(getTAG(),"banner size  --> " + list.size());
                return list.size();
            }

            @Override
            public View getView(int position,View convertView) {

                ImageView bannerIv = null;
                if (convertView != null){
                    bannerIv = (ImageView) convertView;
                }else {
                    bannerIv = new ImageView(getContext());
                }

                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.TOP;
                bannerIv.setLayoutParams(layoutParams);
                bannerIv.setScaleType(ImageView.ScaleType.FIT_XY);
                String url = list.get(position).getBanner_url().getUrl_list().get(0).getUrl();

                ImageManager.getInstance().loadImage(bannerIv,url);
                return bannerIv;
            }

            @Override
            public String getBannerDesc(int position) {
                if (list.size() > 0 && list.get(position) != null){
                    return list.get(position).getBanner_url().getTitle();
                }else {
                    return null;
                }

            }
        });
        //开启轮播
        mBannerView.startLoop();
        //设置轮播动画时间
        mBannerView.setDuration(1200);
        mBannerView.setSwitchTime(3000);
        mBannerView.setItemClickListener(new BannerItemClickListener() {
            @Override
            public void click(int position) {
                ToastUtil.showLongToast("点击Banner的 " + position);
            }
        });
    }


}
