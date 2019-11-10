package com.qiyei.sdk.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.qiyei.sdk.log.LogManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/2.
 * Version: 1.0
 * Description:
 */
public class ViewUtils {

    private static final String TAG = ViewUtils.class.getSimpleName();

    /**
     * Activity中使用
     * @param activity
     */
    public static void inject(Activity activity){
        inject(new ViewFinder(activity));
    }

    /**
     * View中使用
     * @param view
     */
    public static void inject(View view){
        inject(new ViewFinder(view));
    }

    /**
     * View中使用
     * @param fragment
     * @param view
     */
    public static void inject(Fragment fragment, View view){
        inject(new ViewFinder(fragment,view));
    }

    /**
     * 反注入，防止内存泄漏
     * @param activity
     */
    public static void unInject(Activity activity){

    }

    /**
     * 实际处理者
     * @param finder
     */
    private static void inject(ViewFinder finder){
        injectField(finder);
        injectEvent(finder);
    }

    /**
     * 注入域
     * @param finder
     */
    private static void injectField(ViewFinder finder){
        //获取类里面所有的属性
        Class<?> clazz = finder.findClass();
        Field[] fields = clazz.getDeclaredFields();
        LogManager.d(TAG,"name:" + clazz.getSimpleName() + " fields.length:" + fields.length);

        //依次遍历并获取域上的ViewById注解
        for (Field field : fields){
            //获取ViewById注解
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null){
                //获取id值，对应R.id.xxx
                int id = viewById.value();
                if (id != 0){
                    View view = finder.finderViewById(id);
                    if (view != null){
                        //能够注入所有修饰符 private public protect
                        field.setAccessible(true);
                        try {
                            //动态的注入找到的View
                            field.set(finder.finderObject(),view);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();

                            LogManager.e(TAG,e.getMessage());
                        }
                    }
                }
            }
        }
    }

    /**
     * 注入事件
     * @param finder
     */
    private static void injectEvent(ViewFinder finder){
        //获取所有的方法
        Class<?> clazz = finder.findClass();
        Method[] methods = clazz.getDeclaredMethods();
        //依次遍历并获取方法的ViewById注解
        for (Method method : methods){
            //获取OnClick注解
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null){
                //获取id值，对应R.id.xxx
                int[] ids = onClick.value();
                CheckNet checkNet = method.getAnnotation(CheckNet.class);
                boolean isCheckNet = checkNet != null ? true : false;

                for (int id : ids){
                    View view = finder.finderViewById(id);
                    if (view != null){
                        view.setOnClickListener(new DeclaredOnClickListener(method,finder.finderObject(),isCheckNet));
                    }
                }
            }
        }
    }

    /**
     * 设置OnClickListener的监听器
     */
    private static class DeclaredOnClickListener implements View.OnClickListener{
        /**
         * 反射时方法调用的对象
         */
        private Object mObject;
        /**
         * 反射的方法
         */
        private Method mMethod;
        /**
         * 是否检查网络
         */
        private boolean isCheckNet;

        public DeclaredOnClickListener(Method method , Object object,boolean isCheckNet){
            mMethod = method;
            mObject = object;
            this.isCheckNet = isCheckNet;
        }

        @Override
        public void onClick(View v) {
            //是检查网络，并且网络不可用
            if (isCheckNet && !networkAvailable(v.getContext())){
                // 打印Toast   "亲，您的网络不太给力"  写死有点问题  需要配置
                Toast.makeText(v.getContext(), "亲，您的网络不太给力哦 ！", Toast.LENGTH_LONG).show();
                return;
            }
            //反射该方法

            Class<?>[] params = mMethod.getParameterTypes();
            mMethod.setAccessible(true);
            LogManager.i(TAG,"invoke onClick() params.length -> " + params.length);
            //有1个参数或者0个参数
            if (params.length == 0){
                try {
                    mMethod.invoke(mObject);
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                    LogManager.e(TAG,"invoke fail :" + LogManager.getExecptionInfo(e1));
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                    LogManager.e(TAG,"invoke fail :" + LogManager.getExecptionInfo(e1));
                }
            }else {
                try {
                    mMethod.invoke(mObject,v);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogManager.e(TAG,"invoke fail :" + LogManager.getExecptionInfo(e));
                }
            }
        }
    }

    /**
     * 判断当前网络是否可用
     */
    private static boolean networkAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.e(TAG,e.getMessage());
        }
        return false;
    }
}
