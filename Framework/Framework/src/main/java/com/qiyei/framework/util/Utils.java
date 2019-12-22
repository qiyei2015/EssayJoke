package com.qiyei.framework.util;

import com.qiyei.sdk.common.RuntimeEnv;
import com.qiyei.sdk.util.UUIDUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Created by qiyei2015 on 2018/9/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class Utils {

    public static void writeFile(){
        for (int i = 0; i < 1000; i++) {
            File file = new File(RuntimeEnv.appContext.getCacheDir().getAbsolutePath() + "/write.txt");

            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file);
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 1000000; j++) {
                    builder.append(UUIDUtil.get32UUID());
                }
                byte[] bytes = builder.toString().getBytes();
                fileOutputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
