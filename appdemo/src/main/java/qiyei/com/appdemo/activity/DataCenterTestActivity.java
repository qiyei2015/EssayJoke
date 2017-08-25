package qiyei.com.appdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qiyei.sdk.dc.DCConstant;
import com.qiyei.sdk.dc.DataManager;
import com.qiyei.sdk.dc.DataObserver;
import com.qiyei.sdk.log.LogUtil;
import com.qiyei.sdk.util.ToastUtil;

import java.util.HashSet;
import java.util.Set;

import qiyei.com.appdemo.R;

public class DataCenterTestActivity extends AppCompatActivity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_center_test);
        mButton = (Button) findViewById(R.id.btn1);

        final String uri1 = DataManager.getInstance().getUri(DCConstant.MEM_DATA,"11");
        final String uri2 = DataManager.getInstance().getUri(DCConstant.MEM_DATA,"12");
        final String uri3 = DataManager.getInstance().getUri(DCConstant.MEM_DATA,"13");

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getInstance().setString(uri1,"1234");
                DataManager.getInstance().setString(uri2,"5678");
                DataManager.getInstance().setString(uri3,"90");
            }
        });

        Set<String> uris = new HashSet<>();
        uris.add(uri1);

        DataManager.getInstance().registerDataObserver(uris,new DataObserver() {
            @Override
            public void dataUpdate(Set<String> uris) {
                LogUtil.d("DataCenter"," uris ---->" + uris.toString());
                ToastUtil.showLongToast(DataManager.getInstance().getString(uri1,""));
            }
        });

    }

}
