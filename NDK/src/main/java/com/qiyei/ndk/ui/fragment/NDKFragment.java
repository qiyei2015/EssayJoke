package com.qiyei.ndk.ui.fragment;



import android.support.v4.app.Fragment;
import android.view.View;


import com.qiyei.framework.common.model.MainMenu;
import com.qiyei.framework.ui.fragment.CommonListFragment;
import com.qiyei.ndk.ui.activity.EncryptActivity;


import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NDKFragment extends CommonListFragment {

    private static final String TAG = "NDKFragment";

    /**
     * 菜单item
     */
    private List<MainMenu> mMenuList = new ArrayList<>();

    private String[] names = new String[]{"测试1 加密测试"};
    private Class<?>[] clazzs = new Class[]{EncryptActivity.class};

    public NDKFragment() {
        super();
        for (int i = 0 ; i < names.length ; i++){
            MainMenu menu = new MainMenu(i+1,names[i],clazzs[i]);
            mMenuList.add(menu);
        }
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        setLiveData(mMenuList);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
