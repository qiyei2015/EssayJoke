package com.qiyei.appdemo;

import android.app.Application;
import android.content.Context;

import com.qiyei.framework.skin.SkinManager;
import com.qiyei.sdk.SDKManager;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.util.ToastUtil;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/8.
 * Version: 1.0
 * Description:
 */
public class BaseApplication extends Application {
    private static final String TAG = "Application";

    private static final String APP_ID = "24598257-1";
    private static final String App_Secret = "7b8ceb0c2596b44787e61a9aaf1ff4d3";
    private static final String RSA = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCmA0zG7" +
            "V1igoWQENiSIfi0P7aTCVf/4oqZfCjZk3KqFpV+BCiTrnLvXIZUHyeAzc2cSACf2i3bertlnX5a5yIA/0uX5B7" +
            "+Fg9KzmlTT6niYRXEIli/QM50Mwgdlv55R2iwyzurMsMF6/WJpCqYEUR9ZYq+CJjzPD1bOxsLAoNaSiwPNDPVb" +
            "QpUdZmVFeWt6Lix6XPpUrlLRjQ9ATFFhH/L5eqsNVEyIrQcYho5hhAlr777NwZXp+AD1b67cJ/d5yD1c795xPks" +
            "3UjjOjya9wCAY52vtFFlXjqmqKRlZOECq6lx/KWrx93L5HR6DzZcdRcLXTI+ZpFuUhOjXGip6PQnAgMBAAECgg" +
            "EAb70C3VYAqABwA5LDIkDJVBEaQwdj+rAxV7NxD1kmMYN35VTJNvWRGLjvFQnPHevnu4vfF7jTasnF/lQuKuMt" +
            "SPyMldSOY7jXXCNoUxHop51u+A+iKkBldWKv0LLD3z+III76P3WwPHkgUB92J9pffgNtihDhWb4vEgvLXPK5FH" +
            "iIfgJDW1su8ur6FsjgXCE8qs13bEvW8ZqIZX5AYKrvKEmQdGAJflFOxmind6CkAG/nxLpGHvxHZWOsTtvbnlOuq" +
            "j32hpal1Mlr8FRPyMd3tE/rU1G45LxgJLNHkpfvw9Gwo9uwBtIjswhyJS0OHpqgX7VnQBGzZS5lrPd+ZbAgiQKB" +
            "gQDQHrmqN2BWAwA6vt/n1Fyb83ExLnSZftn3XcD0xFx5z23x8zZoTcQx/2m+4jjrQkh+UvHl+wGG2Bu+GeHTk2" +
            "izbZ2PFx3KMk0jbix3jA4Ey6+MYjOz1Gvm6avTY3Boz7zdZe9ctIqGAk+sBDQnZZJDMLzQ8vn+dDYd4s/M30Gy" +
            "nQKBgQDMNKp3hi6tMNxEZywFMPTv4Gn1nSbqqKKp093NoNp9NQIjUivqvchXUcqerAzBhoGvvrlDD02+Upkpd6" +
            "8t0wDFBnXi6p+LKl2JBLgP+cKxyBnbpcqZCWo5IOkVMzHuyLyp1P9i3q2WEHELNwOH17AL9HQa5pZ/B+lHSi9f" +
            "YC20kwKBgQDB/4IOOyRfyNhT6k0I6xmQfXLL54Pk+Svwu8H753ZUTZks6guTWrpB6NYHixSHp/evWV4HhNy9d4" +
            "EUyUeKU2RLrSgrk96+9fiVyENHBJSkv8FZ2MhdHuueCb+TgqPr74kbz8M55Uki9dCShjCfcLzQSQQYugVyvZ0" +
            "caqpNTOcOuQKBgGZLY7GoWC2OMmlZvaWEFs8rfij55oram1GzvoLJt2nNahrDiRh4pamIYZYG3gT8Gm8tfaTo" +
            "P6u9uNgbCnhMITCRwfBAVFfkiyKeaI4oMa3ZbYqojMsI+ltP7zMdiD/z8v6oop6mqhJofhh+IJimJZx3gsoP" +
            "BQjxr5x0vzJp/btdAoGAEdjBqXc4j0VOy7nPrW8bWjeCY0COtp5QqYEgao1HtiqPrVXf7nEqgxY1uI4FHxLUEI" +
            "T6QarUe9JiEi5VL+StcJ30VSL9hDuEh92Z3AUTGVMyL9Q2VfQwjpn4Yyuw+SqLAn6v5vu5QBdcxFlCotMES03H" +
            "2RDTRTJjn1zaDNjOULM=";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String appVersion;
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            appVersion = "1.0.0";
        }
        // initialize最好放在attachBaseContext最前面
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                .setSecretMetaData(APP_ID,App_Secret,RSA)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            LogManager.d(TAG,"补丁加载成功");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                            LogManager.d(TAG,"新补丁生效需要重启");
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            LogManager.d(TAG,"补丁加载失败");
                        }
                    }
                }).initialize();

        try {
            SDKManager.initSDK(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //初始化皮肤管理器
        SkinManager.getInstance().init(this);

        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();

    }

}
