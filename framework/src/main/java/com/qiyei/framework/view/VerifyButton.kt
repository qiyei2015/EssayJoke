package com.qiyei.framework.view

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.Button
import com.qiyei.framework.R

/**
 * @author Created by qiyei2015 on 2018/9/30.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 验证码按钮
 */
class VerifyButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Button(context, attrs, defStyleAttr) {

    private val mHandler:Handler
    /**
     * 默认超时时间60秒
     */
    private var mCount = 60
    /**
     * 验证点击事件接口
     */
    private var mOnVerifyClickListener:OnVerifyClickListener? = null

    init {
        this.text = "获取验证码"
        mHandler = Handler()
    }

    /**
     * 计数器减小人物
     */
    private val mCountDownTask = object :Runnable{
        override fun run() {
            //引用外部类
            this@VerifyButton.text = mCount.toString() + "s"
            this@VerifyButton.setBackgroundColor(resources.getColor(R.color.common_disable))
            this@VerifyButton.setTextColor(resources.getColor(R.color.common_white))
            //禁止使能
            this@VerifyButton.isEnabled = false
            if (mCount > 0){
                //1 秒一个计数
                mHandler.postDelayed(this,1000)
            }else{
                resetCounter()
            }
            mCount--
        }
    }

    /**
     * 开始
     */
    fun start(){
        mHandler.post(mCountDownTask)
        mOnVerifyClickListener?.onClick()
    }

    /**
     * 重置
     * @param text 可变参数
     */
    fun resetCounter(vararg text:String){
        this.isEnabled = true
        if (text.isNotEmpty() && "" != text[0]){
            this.text = text[0]
        }else {
            this.text = "重获验证码"
        }

        setBackgroundColor(resources.getColor(R.color.transparent))
        setTextColor(resources.getColor(R.color.common_blue))
        mCount = 60
    }

    /**
     * 设置监听器
     */
    fun setOnVerifyClickListener(listener:OnVerifyClickListener){
        this.mOnVerifyClickListener = listener
    }

    /**
     * 点击事件接口
     */
    interface OnVerifyClickListener{
        fun onClick()
    }
}