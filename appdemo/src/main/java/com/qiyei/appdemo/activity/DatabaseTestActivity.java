package com.qiyei.appdemo.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.qiyei.appdemo.R;
import com.qiyei.appdemo.model.DataBean;
import com.qiyei.sdk.database.DBManager;
import com.qiyei.sdk.database.IDBSession;
import com.qiyei.sdk.database.bean.User;
import com.qiyei.sdk.util.AndroidUtil;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String db = "test.db";
        DBManager.getInstance().initDatabase(AndroidUtil.getExternalDataPath(),db);

//        IDBSession<User> userIDBSession = DBManager.getInstance().openSession(db, User.class);
//        List<User> list = new ArrayList<>();
//
//        for (int i = 0;i < 10;i++){
//            User user = new User(i,"name:" + i);
//            list.add(user);
//        }
//
//        userIDBSession.insert(list);
//        userIDBSession.querySupport();

        IDBSession<DataBean> userIDBSession = DBManager.getInstance().openSession(db, DataBean.class);
        List<DataBean> list = new ArrayList<>();

        for (int i = 0;i < 10;i++){
            DataBean dataBean = new DataBean(i,"name:" + i,"value:" + i);
            list.add(dataBean);
        }

        userIDBSession.insert(list);
        userIDBSession.querySupport();

    }

}
