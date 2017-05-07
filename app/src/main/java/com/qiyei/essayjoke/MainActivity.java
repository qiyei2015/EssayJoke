package com.qiyei.essayjoke;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qiyei.baselibrary.ioc.CheckNet;
import com.qiyei.baselibrary.ioc.OnClick;
import com.qiyei.baselibrary.ioc.ViewById;
import com.qiyei.framework.BaseSkinActivity;

public class MainActivity extends BaseSkinActivity {

    static {
        System.loadLibrary("native-lib");
    }

    @ViewById(R.id.test_tv1)
    private TextView mTextView1;

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

    }

    @Override
    protected void initView() {
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("");
        mTextView1.setText("这是IOC注解生成的");
    }

    @Override
    protected void initData() {

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

//    @OnClick(R.id.test_tv1)
//    private void textClick(){
//        Toast.makeText(this,"点击了：",Toast.LENGTH_LONG).show();
//    }
}
