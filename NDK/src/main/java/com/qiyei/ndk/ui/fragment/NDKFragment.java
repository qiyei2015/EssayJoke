package com.qiyei.ndk.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiyei.framework.ui.fragment.BaseFragment;
import com.qiyei.ndk.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NDKFragment extends BaseFragment {


    public NDKFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ndk, container, false);
    }

}
