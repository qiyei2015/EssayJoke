package com.qiyei.essayjoke.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qiyei.essayjoke.R;
import com.qiyei.framework.imageselector.ImageSelector;

public class ImageSelectedTestActivity extends AppCompatActivity {

    private Button mButton;

    private static final int requestCode = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selected_test);

        mButton = (Button) findViewById(R.id.btn1);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelector.create().multi().count(3).showCamera(true).start(ImageSelectedTestActivity.this,requestCode);
            }
        });
    }

}
