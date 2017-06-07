package com.qiyei.essayjoke.test;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qiyei.essayjoke.R;
import com.qiyei.sdk.crash.ExceptionCrashHandler;
import com.qiyei.sdk.ioc.CheckNet;
import com.qiyei.sdk.ioc.OnClick;
import com.qiyei.sdk.ioc.ViewById;
import com.qiyei.sdk.util.ToastUtil;
import com.qiyei.framework.activity.BaseSkinActivity;
import com.qiyei.framework.titlebar.CommonTitleBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends BaseSkinActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @ViewById(R.id.test_tv1)
    private TextView mTextView1;
    @ViewById(R.id.btn1)
    private Button mButton1;

    private Context mContext;

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initTitle() {
        CommonTitleBar commonNavigationBar = new CommonTitleBar.Builder(this)
                .setTitle("主界面")
                .setRightText("投稿")
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showLongToast("点击了右边");
                    }
                })
                .build();
    }

    @Override
    protected void initView() {
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("");
        mTextView1.setText("这是IOC注解生成的");

        mButton1.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mContext = this;
        uploadCrashFile();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @OnClick(R.id.test_tv1)
    @CheckNet
    private void textClick(View view){
        Toast.makeText(this,"点击了：" + view.getId(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        //ToastUtil.showLongToast("点击：" + (2/0));
        startActivity(TestActivity.class);
    }

    /**
     * 上传崩溃日志
     */
    private void uploadCrashFile(){
        File file = ExceptionCrashHandler.getInstance().getCrashFile();
        if (!file.exists()){
            return;
        }

        FileInputStream input = null;
        try {
            input = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(input);
            char[] buffer = new char[1024];
            int len = 0;
            while ( (len = reader.read(buffer)) != -1){
                String s = new String(buffer,0,len);
                //Log.d(TAG,s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
