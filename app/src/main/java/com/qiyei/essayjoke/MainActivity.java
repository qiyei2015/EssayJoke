package com.qiyei.essayjoke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qiyei.baselibrary.ioc.CheckNet;
import com.qiyei.baselibrary.ioc.OnClick;
import com.qiyei.baselibrary.ioc.ViewById;
import com.qiyei.baselibrary.ioc.ViewUtils;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    @ViewById(R.id.test_tv1)
    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        mTextView1.setText("这是IOC注解生成的");
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
