package qiyei.com.appdemo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.qiyei.framework.imageselector.ImageSelector;
import com.qiyei.sdk.log.LogUtil;
import com.qiyei.sdk.util.ImageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import qiyei.com.appdemo.R;

public class ImageSelectedTestActivity extends AppCompatActivity {

    private static final String TAG = "ImageSelected";

    private Button mButton;
    private ImageView mImageView;

    private static final int requestCode = 2;

    private List<String> mImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selected_test);

        mButton = (Button) findViewById(R.id.btn1);
        mImageView = (ImageView) findViewById(R.id.imv);
        mImageList = new ArrayList<>();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelector.create().multi().count(3).showCamera(true).start(ImageSelectedTestActivity.this,requestCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == requestCode){
                mImageList = (List<String>) data.getSerializableExtra(ImageSelector.KEY_RESULT);
                LogUtil.d(TAG,"image -->"+mImageList.toString());
                String path = mImageList.get(0);

                Bitmap bitmap = BitmapFactory.decodeFile(path);

                ImageUtil.compressImage(bitmap,50, Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
                        new File(path).getName());
                //mImageView.setImageBitmap(bitmap);

                //Glide.with(this).load(mImageList.get(0)).override(1080,1500).into(mImageView);
            }
        }

    }
}
