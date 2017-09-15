package com.qiyei.sdk.dialog;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.qiyei.sdk.R;
import com.qiyei.sdk.log.LogManager;


/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/13.
 * Version: 1.0
 * Description:
 */
public class BaseDialog extends DialogFragment {
    /**
     * 调试标志
     */
    private static final String TAG = BaseDialog.class.getSimpleName();
    /**
     * context
     */
    protected Context mContext;
    /**
     * Dialog的参数
     */
    protected DialogParams mParams;

    /**
     * 构造方法
     */
    public BaseDialog(){
        super();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog);
        //保存数据，防止重建Dialog时出现数据丢失的情况
        if (savedInstanceState != null){

        }
        setCancelable(mParams.isCancelable);
        LogManager.i(TAG,"onCreate()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogManager.i(TAG,"onCreateView()");
        //去除标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //必须返回的View是Builder中已经设置的那个对象，使用LayoutInflater加载的是另外一个对象
        return mParams.mContentView;
    }


    @Override
    public void onStart() {
        super.onStart();

        //设置布局属性
        Window window = getDialog().getWindow();
        window.setLayout(mParams.mWidth,mParams.mHeight);
        window.setGravity(mParams.mGravity);

        LogManager.i(TAG,"onStart()");
    }

    /**
     *
     */
    public static class Builder{

        private Context mBuilderContext;
        //参数
        private DialogParams mBuilderParams ;
        //helper
        private DialogHelper mHelper;
        //说应用的dialog
        BaseDialog dialog;

        /**
         * 构造方法
         * @param context
         */
        public Builder(Context context){
            mBuilderContext = context;
            dialog = new BaseDialog();

            mBuilderParams = new DialogParams();
            mHelper = new DialogHelper(dialog);
            mBuilderParams.mHelper = mHelper;
        }

        /**
         * 设置setContentView
         * @param layoutId
         * @return
         */
        public Builder setContentView(int layoutId){
            mBuilderParams.mLayoutId = layoutId;
            mBuilderParams.mContentView = LayoutInflater.from(mBuilderContext).inflate(mBuilderParams.mLayoutId,null,false);
            mBuilderParams.mHelper.setContentView(mBuilderParams.mContentView);
            return this;
        }

        /**
         * 设置ContentView
         * @param view
         * @return
         */
        public Builder setContentView(View view){
            mBuilderParams.mContentView = view;
            mBuilderParams.mLayoutId = 0;
            mBuilderParams.mHelper.setContentView(mBuilderParams.mContentView);
            return this;
        }

        /**
         * 设置点击对话框外是否可以取消
         * @param cancelable
         * @return
         */
        public Builder setCancelable(boolean cancelable){
            mBuilderParams.isCancelable = cancelable;
            return this;
        }

        /**
         * 设置要显示需要的fragment
         * @param manager
         * @return
         */
        public Builder setFragmentManager(FragmentManager manager){
            mBuilderParams.mFragmentManager = manager;
            return this;
        }

        /**
         * 全部宽度显示
         * @return
         */
        public Builder fullWidth(){
            mBuilderParams.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 设置位置 居中 底部 顶部
         * @param gravity
         * @return
         */
        public Builder setGravity(int gravity){
            mBuilderParams.mGravity = gravity;
            return this;
        }

        /**
         * 设置内容
         * @param viewId
         * @param text
         */
        public Builder setText(int viewId, CharSequence text) {
            LogManager.i(TAG,"setText viewId : " + viewId + ",text :" + text);
            mBuilderParams.mHelper.setText(viewId,text);
            return this;
        }

        /**
         * 设置ImageView
         * @param viewId
         * @param bitmap
         */
        public Builder setImage(int viewId, Bitmap bitmap) {
            LogManager.i(TAG,"setImage viewId : " + viewId + ",bitmap :" + bitmap);
            mBuilderParams.mHelper.setImage(viewId,bitmap);
            return this;
        }

        /**
         * 设置ImageView的Drawable
         * @param viewId
         * @param resId
         */
        public Builder setDrawable(int viewId, int resId) {
            LogManager.i(TAG,"setDrawable viewId : " + viewId + ",resId :" + resId);
            Drawable drawable ;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                drawable = mBuilderContext.getDrawable(resId);
            }else {
                drawable = mBuilderContext.getResources().getDrawable(resId);
            }
            mBuilderParams.mHelper.setDrawable(viewId,drawable);
            return this;
        }

        /**
         * 设置ImageView的Drawable
         * @param viewId
         * @param drawable
         */
        public Builder setDrawable(int viewId, Drawable drawable) {
            LogManager.i(TAG,"setDrawable viewId : " + viewId + ",drawable :" + drawable);
            mBuilderParams.mHelper.setDrawable(viewId,drawable);
            return this;
        }

        /**
         * 设置点击事件
         *
         * @param viewId
         * @param listener
         */
        public Builder setDialogListener(int viewId, DialogListener listener) {
            LogManager.i(TAG,"setOnClickListener viewId : " + viewId + ",listener :" + listener);
            mBuilderParams.mHelper.setOnClickListener(viewId,listener);
            return this;
        }

        /**
         * 创建对话框
         * @return
         */
        public BaseDialog build(){
            dialog.mParams = mBuilderParams;
            return dialog;
        }
    }

    /**
     * 根据id获取对应的view
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        return mParams.mHelper.getView(viewId);
    }

    /**
     * 显示对话框
     */
    public void show(){
        //如果没有被添加
        if (!isAdded()){
            FragmentTransaction transaction = mParams.mFragmentManager.beginTransaction();
            transaction.add(this, TAG);
            transaction.commitAllowingStateLoss();
        }
        LogManager.i(TAG,"show()");
    }

    /**
     * 取消显示对话框
     */
    @Override
    public void dismiss(){
        super.dismiss();
    }

}
