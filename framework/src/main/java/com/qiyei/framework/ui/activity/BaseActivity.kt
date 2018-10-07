package com.qiyei.framework.ui.activity



import android.os.Bundle
import android.view.View
import com.qiyei.framework.AppManager
import com.qiyei.framework.titlebar.CommonTitleBar
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.jetbrains.anko.toast

/**
 * @author Created by qiyei2015 on 2018/9/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 所有Activity基类
 */
abstract class BaseActivity :RxAppCompatActivity(), View.OnClickListener{

    private var preTime:Long = 0

    /**
     * 通用标题栏
     */
    protected var mTitleBar: CommonTitleBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance.addActivity(this)
    }

    override fun onDestroy() {
        AppManager.instance.removeActivity(this)
        super.onDestroy()
    }

    override fun onClick(view: View) {

    }

    /**
     * 用于日志调试TAG
     */
    abstract fun getTAG():String

    /**
     * 监听返回键，双击退出APP
     */
    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if (time - preTime > 2000){
            preTime = time
            toast("再按一次退出")
        }else {
            AppManager.instance.exitApplication(this)
        }
    }

}