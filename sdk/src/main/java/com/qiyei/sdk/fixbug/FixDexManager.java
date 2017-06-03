package com.qiyei.sdk.fixbug;

import android.content.Context;
import android.util.Log;

import com.qiyei.sdk.util.FileUtil;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.BaseDexClassLoader;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/13.
 * Version: 1.0
 * Description:
 */
public class FixDexManager {
    private static final String TAG = FixDexManager.class.getSimpleName();
    private Context mContext;
    /**
     * 要修复的DexFile
     */
    private File mDexFile = null;

    public FixDexManager(Context context) {
        mContext = context;
        //获取应用可访问的dex目录
        mDexFile = mContext.getDir("data",Context.MODE_PRIVATE);
    }

    /**
     * 修复dex
     * @throws Exception
     */
    public void fixDex() throws Exception{
        File[] files = mDexFile.listFiles();
        List<File> dexFiles = new ArrayList<>();

        for (File file : files){
            if (file.getName().endsWith(".dex")){
                dexFiles.add(file);
            }
        }

        loadDex(dexFiles);
    }

    /**
     * 修复dex
     * @param dexFile
     */
    public void fixDex(String dexFile) throws Exception{
        //1 先获取dexElements数组

        File srcFile = new File(dexFile);
        if (!srcFile.exists()){
            throw new NullPointerException("srcFile is null");
        }

        File destFile = new File(mDexFile,srcFile.getName());
        if (destFile.exists()){
            Log.d(TAG,"patch [" + dexFile + "] has been load !");
            return;
        }

        //将下载的dex拷贝到destFile下
        FileUtil.copyFile(destFile,srcFile);

        //ClassLoader读取fixDex路径
        List<File> fileDexFiles = new ArrayList<>();
        fileDexFiles.add(destFile);

        loadDex(fileDexFiles);

    }

    /**
     * 获取DexElements
     * @param classLoader
     * @return
     */
    private Object getDexElements(ClassLoader classLoader) throws Exception{
        //1 反射获取pathListField对象
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        //2 获取 pathList对象
        Object pathList = pathListField.get(classLoader);

        //3 pathList里面的dexElements
        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        //4 获取dexElement对象
        return dexElementsField.get(pathList);
    }

    /**
     * 加载dex
     * @param fileDexFiles
     */
    private void loadDex(List<File> fileDexFiles) throws Exception {
        //获取已经运行的dexElement
        ClassLoader applicationClassLoader = mContext.getClassLoader();

        Object oldDexElements = getDexElements(applicationClassLoader);
        //dex解压目录
        File optimizedDir = new File(mDexFile,"opt");
        if (!optimizedDir.exists()){
            optimizedDir.mkdir();
        }

        //修复Dex
        for (File dexFile : fileDexFiles){
            // dexPath  dex路径
            // optimizedDirectory  解压路径
            // libraryPath .so文件位置
            // parent 父ClassLoader
            ClassLoader fixDexClassLoader = new BaseDexClassLoader(dexFile.getAbsolutePath(),optimizedDir
                    ,null,applicationClassLoader);

            Object newDexElements = getDexElements(fixDexClassLoader);

            // 3. 把补丁的dexElement 插到 已经运行的 dexElement 的最前面  合并
            // oldDexElements 数组 合并 newDexElements 数组

            // 3.1 合并完成
            oldDexElements = combineArray(newDexElements,oldDexElements);
        }

        //将合并的数组注入到BaseDexClassLoader的dexElements 中
        injectDexElements(applicationClassLoader,oldDexElements);
    }

    /**
     * 合并两个数组
     *
     * @param arrayLhs
     * @param arrayRhs
     * @return
     */
    private Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; ++k) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }

    /**
     * 注入到BaseDexClassLoader的dexElements中
     * @param classLoader
     * @param oldDexElements
     */
    private void injectDexElements(ClassLoader classLoader, Object oldDexElements) throws Exception{
        //先获取 pathList
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

        //pathList里面的dexElements
        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        //进行替换
        dexElementsField.set(pathList,oldDexElements);
    }
}
