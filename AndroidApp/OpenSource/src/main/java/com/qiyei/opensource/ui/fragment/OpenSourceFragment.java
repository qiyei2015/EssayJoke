package com.qiyei.opensource.ui.fragment;


import android.view.View;



import com.qiyei.framework.common.model.MainMenu;
import com.qiyei.framework.ui.fragment.CommonListFragment;
import com.qiyei.opensource.R;
import com.qiyei.opensource.ui.activity.DaggerDemoActivity;
import com.qiyei.opensource.ui.activity.HotfixTestActivity;
import com.qiyei.opensource.ui.activity.MMKVDemoActivity;
import com.qiyei.opensource.ui.activity.OkioTestActivity;
import com.qiyei.opensource.ui.activity.RxjavaTestActivity;


import java.util.ArrayList;
import java.util.List;


/**
 * @author Created by qiyei2015 on 2018/6/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class OpenSourceFragment extends CommonListFragment {


    private static final String TAG = "OpenSourceFragment";
    /**
     * 菜单item
     */
    private List<MainMenu> mMenuList = new ArrayList<>();

    private String[] names = new String[]{"测试1 Rxjava测试","测试2 Okio测试","测试3 Dagger2","测试4 热修复","测试5 MMKV开源框架"};
    private Class<?>[] clazzs = new Class[]{RxjavaTestActivity.class,OkioTestActivity.class,DaggerDemoActivity.class,HotfixTestActivity.class, MMKVDemoActivity.class};


    public OpenSourceFragment() {
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
