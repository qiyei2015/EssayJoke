package com.qiyei.sdk.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
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


/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/13.
 * Version: 1.0
 * Description:
 */
public class BaseDialog extends DialogFragment {

    private static final String TAG = BaseDialog.class.getSimpleName();

    private DialogParams mDialogParams;
    public BaseDialog(){
        super();
    }

    @SuppressLint("ValidFragment")
    public BaseDialog(DialogParams params){
        mDialogParams = params;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog);
        setCancelable(mDialogParams.isCancelable);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //去除标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (mDialogParams.mLayoutId != 0){
            return inflater.inflate(mDialogParams.mLayoutId,container,false);
        }else {
            return mDialogParams.mContentView;
        }
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

    }

    @Override
    public void onStart() {
        super.onStart();

        //设置布局属性
        Window window = getDialog().getWindow();
        window.setLayout(mDialogParams.mWidth,mDialogParams.mHeight);
        window.setGravity(mDialogParams.mGravity);
    }

    /**
     * builder设计模式创建Dialog
     */
    public static class Builder{
        private Context mContext;
        /**
         * 对话框参数
         */
        private DialogParams mParams;

        public Builder(Context context,int layoutId){
            mContext = context;
            mParams = new DialogParams();
            mParams.mHelper = new DialogHelper();
            mParams.mLayoutId = layoutId;
            mParams.mHelper.setContentView(LayoutInflater.from(mContext).inflate(mParams.mLayoutId,null,false));
        }

        /**
         * 设置setContentView
         * @param layoutId
         * @return
         */
        public Builder setContentView(int layoutId){
            mParams.mLayoutId = layoutId;
            mParams.mContentView = null;
            mParams.mHelper.setContentView(LayoutInflater.from(mContext).inflate(mParams.mLayoutId,null,false));
            return this;
        }

        /**
         * 设置ContentView
         * @param view
         * @return
         */
        public Builder setContentView(View view){
            mParams.mContentView = view;
            mParams.mLayoutId = 0;
            mParams.mHelper.setContentView(mParams.mContentView);
            return this;
        }

        /**
         * 设置点击对话框外是否可以取消
         * @param cancelable
         * @return
         */
        public Builder setCancelable(boolean cancelable){
            mParams.isCancelable = cancelable;
            return this;
        }

        /**
         * 设置要显示需要的fragment
         * @param manager
         * @return
         */
        public Builder setFragmentManager(FragmentManager manager){
            mParams.mFragmentManager = manager;
            return this;
        }

        /**
         * 全部宽度显示
         * @return
         */
        public Builder fullWidth(){
            mParams.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 设置位置 居中 底部 顶部
         * @param gravity
         * @return
         */
        public Builder setGravity(int gravity){
            mParams.mGravity = gravity;
            return this;
        }

        /**
         * 设置内容
         * @param viewId
         * @param text
         */
        public Builder setText(int viewId, CharSequence text) {
            mParams.mHelper.setText(viewId,text);
            return this;
        }

        /**
         * 设置点击事件
         *
         * @param viewId
         * @param listener
         */
        public Builder setOnClickListener(int viewId, View.OnClickListener listener) {
            mParams.mHelper.setOnClickListener(viewId,listener);
            return this;
        }

        /**
         * 创建Dialog对象
         * @return
         */
        public BaseDialog create(){
            return new BaseDialog(mParams);
        }
    }

    /**
     * 根据id获取对应的view
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        return mDialogParams.mHelper.getView(viewId);
    }

    /**
     * 显示对话框
     */
    public void show(){
        //如果没有被添加
        if (!isAdded()){
            FragmentTransaction transaction = mDialogParams.mFragmentManager.beginTransaction();
            transaction.add(this, TAG);
            transaction.commitAllowingStateLoss();
        }
    }

    /**
     * 取消显示对话框
     */
    @Override
    public void dismiss(){
        super.dismiss();
    }

}
