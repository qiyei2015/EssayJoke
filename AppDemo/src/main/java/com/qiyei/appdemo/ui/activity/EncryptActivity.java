package com.qiyei.appdemo.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qiyei.appdemo.R;
import com.qiyei.sdk.encrypt.EncryptManager;

public class EncryptActivity extends AppCompatActivity {


    private EditText mEditTextRules;

    private EditText mEditTextContent;

    private TextView mTextViewResult;

    private Button mButton1;

    private Button mButton2;

    String encryptResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        mEditTextRules = (EditText) findViewById(R.id.edt1);
        mEditTextContent = (EditText) findViewById(R.id.edt2);

        mTextViewResult = (TextView) findViewById(R.id.tv2);

        mButton1 = (Button) findViewById(R.id.btn1);
        mButton2 = (Button) findViewById(R.id.btn2);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                encryptResult = EncryptManager.encrypt(mEditTextRules.getText().toString(),mEditTextContent.getText().toString());
                mTextViewResult.setText("加密结果：" + encryptResult);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = EncryptManager.decrypt(mEditTextRules.getText().toString(),encryptResult);
                mTextViewResult.setText("加密结果：" + encryptResult
                        + "\n\n" + "解密结果：" + result);
            }
        });

    }


}
