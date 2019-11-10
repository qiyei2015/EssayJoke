package com.qiyei.framework.ui.activity



import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.qiyei.framework.AppManager
import com.qiyei.framework.titlebar.CommonTitleBar
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity

import org.jetbrains.anko.find
import org.jetbrains.anko.toast

/**
 * @author Created by qiyei2015 on 2018/9/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 所有Activity基类
 */
abstract class BaseActivity : RxAppCompatActivity(), View.OnClickListener{

    private var preTime:Long = 0

    /**
     * 通用标题栏
     */
    protected var mTitleBar: CommonTitleBar? = null
    /**
     * Context
     */
    protected lateinit var mContext:Context
    /**
     * AppContext
     */
    protected lateinit var mAppContext:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        mAppContext = applicationContext
        ARouter.getInstance().inject(this)
        AppManager.instance.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.removeActivity(this)
    }

    override fun onClick(view: View) {

    }

    /**
     * 用于日志调试TAG
     */
    open fun getTAG():String{
       return this::class.java.simpleName
    }


//    override fun onBackPressed() {
//        val time = System.currentTimeMillis()
//        if (time - preTime > 2000){
//            preTime = time
//            toast("再按一次退出")
//        }else {
//            AppManager.instance.exitApplication(this)
//        }
//    }

    //获取Window中视图content
    val contentView:View
        get() {
            val content = find<FrameLayout>(android.R.id.content)
            return content.getChildAt(0)
        }
}