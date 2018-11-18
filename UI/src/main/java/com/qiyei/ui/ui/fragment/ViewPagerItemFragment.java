package com.qiyei.ui.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiyei.ui.R;


/**
 * Created by Darren on 2016/12/5.
 * Email: 240336124@qq.com
 * Description:
 */

public class ViewPagerItemFragment extends Fragment {

    public static ViewPagerItemFragment newInstance(String item) {
        ViewPagerItemFragment itemFragment = new ViewPagerItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", item);
        itemFragment.setArguments(bundle);
        return itemFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager_item, null);
        TextView tv = (TextView) view.findViewById(R.id.text);
        Bundle bundle = getArguments();
        tv.setText(bundle.getString("title"));
        return view;
    }
}
