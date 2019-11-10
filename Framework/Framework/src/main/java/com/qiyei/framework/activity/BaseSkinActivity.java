package com.qiyei.framework.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.LayoutInflaterFactory;
import androidx.core.view.ViewCompat;

import com.qiyei.framework.skin.SkinManager;
import com.qiyei.framework.skin.SkinResources;
import com.qiyei.framework.skin.attrs.SkinAttrs;
import com.qiyei.framework.skin.attrs.SkinView;
import com.qiyei.framework.skin.listener.ISkinChangeListener;
import com.qiyei.framework.skin.support.SkinAppCompatViewInflater;
import com.qiyei.framework.skin.support.SkinAttrsSupport;
import com.qiyei.sdk.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/7.
 * Version: 1.0
 * Description:
 */
public abstract class BaseSkinActivity extends BaseActivity implements LayoutInflaterFactory,ISkinChangeListener{
    /**
     * 插件换肤的布局加载器
     */
    private SkinAppCompatViewInflater mAppCompatViewInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory(layoutInflater,this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs){
        //拦截到View的创建，获取View之后去解析
        //1. 创建View
        View view = createAppCompatView(parent,name,context,attrs);

        // 2 解析属性 src  textColor  background  自定义属性
        //2.1 一个Activity的布局肯定对应多个view
        if (view != null){
            List<SkinAttrs> skinAttrsList = SkinAttrsSupport.getSkinAttrs(context,attrs);
            SkinView skinView = new SkinView(view,skinAttrsList);
            //3 同一交给SkinManager管理
            managerSkinView(skinView);
            // 4.判断一下要不要换肤
            SkinManager.getInstance().checkChangeSkin(skinView);
        }

        return view;
    }

    /**
     * 换肤
     * @param skinResources
     */
    @Override
    public void skinChange(SkinResources skinResources){

    }

    @Override
    protected void onDestroy() {
        SkinManager.getInstance().unregister(this);
        super.onDestroy();
    }

    /**
     * 创建View
     * @param parent
     * @param name
     * @param context
     * @param attrs
     * @return
     */
    private View createAppCompatView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {
        final boolean isPre21 = Build.VERSION.SDK_INT < 21;

        if (mAppCompatViewInflater == null) {
            mAppCompatViewInflater = new SkinAppCompatViewInflater();
        }

        // We only want the View to inherit it's context if we're running pre-v21
        final boolean inheritContext = isPre21 && true
                && shouldInheritContext((ViewParent) parent);

        return mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                isPre21, /* Only read android:theme pre-L (L+ handles this anyway) */
                true /* Read read app:theme as a fallback at all times for legacy reasons */
        );
    }

    /**
     * 拷贝自系统源码
     * @param parent
     * @return
     */
    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == getWindow().getDecorView() || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }

    /**
     * 同一管理SkinView
     * @param view
     */
    private void managerSkinView(SkinView view){
        List<SkinView> skinViews = SkinManager.getInstance().getSkinViews(this);
        if (skinViews == null){
            skinViews = new ArrayList<>();
            SkinManager.getInstance().register(this,skinViews);
        }
        skinViews.add(view);
    }

}
