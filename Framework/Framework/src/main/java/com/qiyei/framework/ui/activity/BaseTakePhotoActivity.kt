package com.qiyei.framework.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import com.bigkoo.alertview.AlertView
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.TResult
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.sdk.log.LogManager
import com.qiyei.sdk.util.TimeUtil
import java.io.File

/**
 * @author Created by qiyei2015 on 2018/10/4.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
abstract class BaseTakePhotoActivity<T:BasePresenter<*>> :BaseMVPActivity<T>(), TakePhoto.TakeResultListener {

    /**
     * TakePhoto
     */
    protected lateinit var mTakePhoto: TakePhoto
    /**
     * 临时图片文件
     */
    protected lateinit var mTempImageFile:File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTakePhoto = TakePhotoImpl(this,this)
        mTakePhoto.onCreate(savedInstanceState)
    }

    override fun getTAG(): String {
        return BaseTakePhotoActivity::class.java.simpleName
    }

    override fun takeSuccess(result: TResult?) {
       LogManager.i(getTAG(),"takeSuccess,result:" + result?.image?.originalPath)
    }

    override fun takeCancel() {
        LogManager.i(getTAG(),"takeCancel")
    }

    override fun takeFail(result: TResult?, msg: String?) {
        LogManager.e(getTAG(),"takeFail,msg:$msg")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode,resultCode,data)
    }

    /**
     * 创建临时图片文件
     */
    protected fun createTempFile(){
        val tempFileName = "${TimeUtil.getCurrentTime()}.png"
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            this.mTempImageFile = File(Environment.getExternalStorageDirectory(),tempFileName)
            return
        }

        this.mTempImageFile = File(filesDir,tempFileName)
    }

    /**
     * 弹出头像对话框
     */
    protected fun showTakePhotoDialog(){
        AlertView.Builder().setContext(this)
                .setStyle(AlertView.Style.ActionSheet)
                .setTitle("选择操作")
                .setMessage(null)
                .setCancelText("取消")
                .setDestructive("拍照", "相册")
                .setOthers(null)
                .setOnItemClickListener { o, position ->
                    mTakePhoto.onEnableCompress(CompressConfig.ofDefaultConfig(),false)
                    when(position){
                        0 -> {
                            createTempFile()
                            mTakePhoto.onPickFromCapture(Uri.fromFile(mTempImageFile))
                        }
                        1 -> {
                            mTakePhoto.onPickFromGallery()
                        }
                    }
                }
                .build()
                .show()
    }
}