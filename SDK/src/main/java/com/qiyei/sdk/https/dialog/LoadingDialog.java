package com.qiyei.sdk.https.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiyei.sdk.R;


/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: http请求时显示的对话框
 */
public class LoadingDialog extends DialogFragment {

    public LoadingDialog(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_loading_dialog, null, false);
//        TextView tvLoadMsg = (TextView) root.findViewById(R.id.load_msg);
//        tvLoadMsg.setText(getTag());
        return root;
    }

}
