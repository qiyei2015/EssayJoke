package com.qiyei.ui.ui.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.qiyei.sdk.dialog.BaseDialog;
import com.qiyei.sdk.dialog.DialogListener;
import com.qiyei.sdk.util.ToastUtil;
import com.qiyei.ui.R;

public class CommonDialogActivity extends AppCompatActivity {

    private static final String TAG = "CommonDialogActivity";

    public static final String SECOND_TAG = "LifeCycleDemo ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_dialog);
        Log.i(TAG,SECOND_TAG + "onCreate");
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testNormalDialog(v);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testDialog(v);
            }
        });
    }


    private void testNormalDialog(View view){
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_test,null,false);
        BaseDialog dialog = new BaseDialog.Builder(this)
                .setCancelable(true)
                //.setContentView(R.layout.dialog_test)
                .setContentView(R.layout.dialog_test)
                .setText(R.id.dialog_content,"这是一个对话框，哈哈哈！")
                .setDialogListener(R.id.dialog_ok, new DialogListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showLongToast("对话框点击了确认");

                    }
                })
                .setDialogListener(R.id.dialog_cancel, new DialogListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showLongToast("对话框点击了取消");
                    }
                })
//                .setGravity(Gravity.BOTTOM)
//                .fullWidth()
                .setFragmentManager(getSupportFragmentManager())
                .build();

        dialog.show();
    }

    private void testDialog(View view){
        BaseDialog dialog = new BaseDialog.Builder(this)
                .setCancelable(true)
                .setContentView(R.layout.common_dialog)
                //.setContentView(contentView)
                .setText(R.id.id_dialog_title,"这是一个对话框标题！")
                .setText(R.id.id_tv_content,"对话框内容55555")
                .setText(R.id.id_tv_confirm,"确认")
                .setText(R.id.id_tv_cancel,"取消")
                .setDrawable(R.id.id_dialog_title_imv,R.drawable.icon_login_single)
                .setDialogListener(R.id.id_tv_confirm, new DialogListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showLongToast("对话框点击了确认");

                    }
                })
                .setDialogListener(R.id.id_tv_cancel, new DialogListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showLongToast("对话框点击了取消");
                    }
                })
                .setGravity(Gravity.BOTTOM)
                .fullWidth()
                .setFragmentManager(getSupportFragmentManager())
                .build();

        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,SECOND_TAG + "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,SECOND_TAG + "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,SECOND_TAG + "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,SECOND_TAG + "onStop");
    }

    @Override
    public void finish() {
        super.finish();
        Log.i(TAG,SECOND_TAG + "finish");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,SECOND_TAG + "onDestroy");
    }
}
