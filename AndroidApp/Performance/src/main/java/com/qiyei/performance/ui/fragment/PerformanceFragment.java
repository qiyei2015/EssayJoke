package com.qiyei.performance.ui.fragment;


import android.view.View;

import com.qiyei.framework.common.model.MainMenu;
import com.qiyei.framework.ui.fragment.CommonListFragment;
import com.qiyei.performance.ui.activity.ANRActivity;
import com.qiyei.performance.ui.activity.AppStartOptimizationActivity;
import com.qiyei.performance.ui.activity.AsyncLayoutInflaterActivity;
import com.qiyei.performance.ui.activity.ImageViewHookActivity;
import com.qiyei.performance.ui.activity.MemoryLeakActivity;
import com.qiyei.performance.ui.activity.MemoryOOMActivity;
import com.qiyei.performance.ui.activity.MemoryOptimizationActivity;
import com.qiyei.performance.ui.activity.UIDrawActivity;
import com.qiyei.performance.ui.activity.X2CDemoActivity;


import java.util.ArrayList;
import java.util.List;


/**
 * @author Created by qiyei2015 on 2018/6/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class PerformanceFragment extends CommonListFragment {


    private static final String TAG = "PerformanceFragment";
    /**
     * 菜单item
     */
    private List<MainMenu> mMenuList = new ArrayList<>();

    private String[] names = new String[]{"测试1 启动优化","测试2 ImageView Hook","测试3 AsyncLayoutInflater","测试4 X2CDemo",
            "测试5 UI绘制优化","测试6 ANR","测试7 内存优化","测试8 内存泄漏","测试9 OOM"};
    private Class<?>[] clazzs = new Class[]{AppStartOptimizationActivity.class, ImageViewHookActivity.class, AsyncLayoutInflaterActivity.class, X2CDemoActivity.class,
            UIDrawActivity.class, ANRActivity.class, MemoryOptimizationActivity.class, MemoryLeakActivity.class, MemoryOOMActivity.class};


    public PerformanceFragment() {
        super();
        for (int i = 0 ; i < names.length ; i++){
            MainMenu menu = new MainMenu(i+1,names[i],clazzs[i]);
            mMenuList.add(menu);
        }
    }

    @Override
    protected String getTAG() {
        return TAG;
    }


    @Override
    protected void initView(View view) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void initData() {
        setLiveData(mMenuList);
    }
}
