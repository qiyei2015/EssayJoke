package com.qiyei.architecture.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.qiyei.architecture.R;
import com.qiyei.sdk.database.engine.SQLiteDataOpenHelper;

public class SQLiteDemoActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_demo);
        mContext = this;

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testThreadAccess4();
            }
        });
    }

    private void testThreadAccess1(){
        for (int i = 0 ; i < 100;i++){
            final int index = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SQLiteDataOpenHelper openHelper = new SQLiteDataOpenHelper(mContext);
                    SQLiteDatabase database = openHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("name", "name-" + index);
                    values.put("age", index);
                    database.insert(SQLiteDataOpenHelper.USER_TABLE, null, values);
                }
            }).start();
        }
    }

    private void testThreadAccess2(){
        SQLiteDataOpenHelper openHelper = new SQLiteDataOpenHelper(mContext);
        SQLiteDatabase database = openHelper.getWritableDatabase();
        for (int i = 0 ; i < 100;i++){
            final int index = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ContentValues values = new ContentValues();
                    values.put("name", "name-" + index);
                    values.put("age", index);
                    database.insert(SQLiteDataOpenHelper.USER_TABLE, null, values);
                }
            }).start();
        }
    }

    private void testThreadAccess3(){
        SQLiteDataOpenHelper openHelper = new SQLiteDataOpenHelper(mContext);
        for (int i = 0 ; i < 100;i++){
            final int index = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SQLiteDatabase database = openHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("name", "name-" + index);
                    values.put("age", index);
                    database.insert(SQLiteDataOpenHelper.USER_TABLE, null, values);
                }
            }).start();
        }
    }

    private void testThreadAccess4(){
        SQLiteDataOpenHelper openHelper = new SQLiteDataOpenHelper(mContext);
        SQLiteDatabase database = openHelper.getWritableDatabase();
        for (int i = 0 ; i < 100;i++){
            final int index = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SQLiteDataOpenHelper openHelper = new SQLiteDataOpenHelper(mContext);
                    SQLiteDatabase database = openHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("name", "name-" + index);
                    values.put("age", index);
                    database.insert(SQLiteDataOpenHelper.USER_TABLE, null, values);
                }
            }).start();
        }
    }
}
