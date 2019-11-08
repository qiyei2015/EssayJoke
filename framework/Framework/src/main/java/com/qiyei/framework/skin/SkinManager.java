package com.qiyei.framework.skin;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.qiyei.framework.skin.attrs.SkinView;
import com.qiyei.framework.skin.listener.ISkinChangeListener;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.version.VersionManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/9.
 * Version: 1.0
 * Description:
 */
public class SkinManager {
    /**
     * 调试标志
     */
    public static final String TAG = SkinManager.class.getSimpleName();
    /**
     * 皮肤名字
     */
    private static final String SKIN_NAME = "skinName";
    /**
     * 皮肤路径
     */
    private static final String SKIN_PATH = "skinPath";
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 皮肤资源
     */
    private SkinResources mSkinResources;
    /**
     * 存储Activity对应的皮肤View
     */
    private Map<ISkinChangeListener,List<SkinView>> mSkinViews = new HashMap<>();
    /**
     * 持有的SharePreferences
     */
    private SharedPreferences mPreferences;

    /**
     * 构造方法私有化
     */
    private SkinManager(){

    }

    /**
     * 内部类
     */
    private static class SingleHolder{
        private final static SkinManager sManager = new SkinManager();
    }

    /**
     * 内部类方式获取单例
     * @return
     */
    public static SkinManager getInstance(){
        return SingleHolder.sManager;
    }

    /**
     * 初始化
     * @param context
     */
    public void init(Context context){
        mContext = context.getApplicationContext();
    }

    /**
     * 加载皮肤
     * @param path
     * @return
     */
    public boolean loadSkin(String path){

        boolean result = false;
        LogManager.d(TAG,"path --> " + path);
        //1 校验签名 增量更新再说

        String signature = VersionManager.getSignature(mContext);
        if (!(signature != null && signature.equals(VersionManager.getSignature(path)))){
            result = false;
            LogManager.i(TAG,"loadSkin faild ! current signature: " + signature + "\n Skin signature :" + VersionManager.getSignature(path));
            LogManager.i(TAG,"loadSkin faild ! the signature is illegal !");
            return result;
        }

        //2 检查文件是否存在
        File file = new File(path);
        if (!file.exists()){
            LogManager.i(TAG,"skin file is not exists !");
            return false;
        }

        String packageName = mContext.getPackageManager().getPackageArchiveInfo(
                path, PackageManager.GET_ACTIVITIES).packageName;

        if(TextUtils.isEmpty(packageName)){
            LogManager.i(TAG,"skin packageName is null !");
            return false;
        }

        // 1. 当前皮肤如果一样不要换
        String currentSkinPath = getSkinPath();

        if(path.equals(currentSkinPath)){
            LogManager.i(TAG,"skin path is same,,not need change skin !");
            return false;
        }

        //3 最好把他复制走，用户不能轻易删除的地方  cache目录下面

        //4 初始化资源管理
        mSkinResources = new SkinResources(mContext,path);

        //5 改变皮肤
        changeSkin();

        //6 保存皮肤
        saveSkinPath(path);

        result = true;
        return result;
    }

    /**
     * 恢复默认
     * @return
     */
    public boolean restore() {
        //判断当前有没有皮肤，没有皮肤就不需要执行下去
        String skinPath = getSkinPath();
        if (TextUtils.isEmpty(skinPath)){
            LogManager.i(TAG,"skinPath is null,not need restore skin !");
            return false;
        }

        //当前手机运行的app的apk路径
        String appResPath = mContext.getPackageResourcePath();
        LogManager.d(TAG,"path --> " + appResPath);
        //初始化资源管理器
        mSkinResources = new SkinResources(mContext,appResPath);

        //改变皮肤
        changeSkin();
        //把皮肤信息清空
        clearSkin();
        return true;
    }

    /**
     * 获取Activity的SkinViews
     * @param activity
     * @return
     */
    public List<SkinView> getSkinViews(Activity activity){
        return mSkinViews.get(activity);
    }

    /**
     * 注册
     * @param listener
     * @param skinViews
     */
    public void register(ISkinChangeListener listener,List<SkinView> skinViews){
        mSkinViews.put(listener,skinViews);
    }

    /**
     * 返回SkinResources
     * @return
     */
    public SkinResources getSkinResources(){
        return mSkinResources;
    }

    /**
     * 保存皮肤路径
     * @param path
     */
    public void saveSkinPath(String path){
        mPreferences = mContext.getSharedPreferences(SKIN_NAME,Context.MODE_PRIVATE);
        mPreferences.edit().putString(SKIN_PATH,path).commit();
    }

    /**
     * 获取皮肤路径
     * @return
     */
    public String getSkinPath(){
        mPreferences = mContext.getSharedPreferences(SKIN_NAME,Context.MODE_PRIVATE);
        return mPreferences.getString(SKIN_PATH,null);
    }

    /**
     * 改变皮肤
     */
    private void changeSkin(){
        //改变皮肤
        Set<ISkinChangeListener> listeners = mSkinViews.keySet();
        //遍历每一个ISkinChangeListener并依次调用其skinChange()方法
        for (ISkinChangeListener listener : listeners){
            //依次调用每个key里面的List<SkinView>
            List<SkinView> skinViews = mSkinViews.get(listener);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }

            listener.skinChange(mSkinResources);
        }
    }

    /**
     * 取消注册
     * @param listener
     */
    public void unregister(ISkinChangeListener listener){
        mSkinViews.remove(listener);
    }

    /**
     * 检查一下是否换肤
     * @param skinView
     */
    public void checkChangeSkin(SkinView skinView){
        // 如果当前有皮肤，也就是保存了皮肤路径，就换一下皮肤
        String currentSkinPath = getSkinPath();
        if(!TextUtils.isEmpty(currentSkinPath) && mSkinResources != null) {
            // 切换一下
            skinView.skin();
        }
    }

    /**
     * 清空皮肤信息
     */
    public void clearSkin(){
        saveSkinPath("");
    }

}
