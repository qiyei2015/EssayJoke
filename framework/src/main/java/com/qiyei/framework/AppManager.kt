package com.qiyei.framework

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

/**
 * @author Created by qiyei2015 on 2018/9/28.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: Activity管理
 */
class AppManager private constructor(){

    private val activityStack:Stack<Activity> = Stack()

    companion object {
        val instance:AppManager by lazy{
            AppManager()
        }
    }

    /**
     * 添加Activity
     */
    fun addActivity(activity: Activity){
        activityStack.add(activity)
    }

    /**
     * 移除Activity
     */
    fun removeActivity(activity: Activity){
        activityStack.remove(activity)
        activity.finish()
    }

    /**
     * 获取当前栈顶Activity
     */
    fun currentActivity():Activity{
        val activity = activityStack.peek()
        return activity
    }


    /**
     * 清除所有Activity
     */
    fun finishAllActivity(){
        for (activity in activityStack){
            activity.finish()
        }
        activityStack.clear()
    }

    /**
     * 退出应用程序
     */
    fun exitApplication(context:Context){
        finishAllActivity()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.killBackgroundProcesses(context.packageName)
        System.exit(0)
    }
}