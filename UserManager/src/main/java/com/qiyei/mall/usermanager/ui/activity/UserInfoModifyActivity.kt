package com.qiyei.mall.usermanager.ui.activity



import android.os.Bundle
import android.view.View
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.mall.usermanager.R
import com.qiyei.mall.usermanager.data.bean.UserInfo
import com.qiyei.mall.usermanager.injection.component.DaggerUserManagerComponent
import com.qiyei.mall.usermanager.injection.module.UploadManagerModule
import com.qiyei.mall.usermanager.injection.module.UserManagerModule
import com.qiyei.mall.usermanager.mvp.presenter.UserInfoModifyPresenter
import com.qiyei.mall.usermanager.mvp.view.IUserInfoModifyView
import kotlinx.android.synthetic.main.activity_user_info_modify.*
import com.bigkoo.alertview.AlertView
import com.qiniu.android.storage.UploadManager
import com.qiyei.framework.titlebar.CommonTitleBar
import org.jetbrains.anko.toast


class UserInfoModifyActivity : BaseMVPActivity<UserInfoModifyPresenter>(),IUserInfoModifyView {

    /**
     * 用户信息
     */
    private lateinit var mUserInfo: UserInfo
    /**
     * 用户头像url
     */
    private lateinit var mUserIconUrl:String
    /**
     * 七牛上传
     */
    private val mUploadManager:UploadManager by lazy { UploadManager() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info_modify)
//        mUserInfo = intent.extras["userInfo"] as UserInfo
        initView()
    }

    override fun initComponentInject() {
        //注入点
        DaggerUserManagerComponent.builder()
                .activityComponent(mActivityComponet)
                .userManagerModule(UserManagerModule())
                .uploadManagerModule(UploadManagerModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.mUserIconRelativeLayout ->{
                showAvatarDialog()
            }
        }
    }

    override fun onUploadTokenResult(result: String) {

    }

    override fun onModifyUserInfoResult(userInfo: UserInfo) {

    }

    /**
     * 初始化View
     */
    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle("个人信息")
                .setRightText("保存").setRightClickListener{
                    mPresenter.modifyUserInfo(mUserIconUrl,mNickNameEditText.text.toString(),
                            if (mGenderMaleRadioButton.isChecked) "0" else "1",
                            mUserSignEditText.text.toString())
                }
                .build()
        mUserIconRelativeLayout.setOnClickListener(this)
    }

    /**
     * 弹出头像对话框
     */
    private fun showAvatarDialog(){
        AlertView.Builder().setContext(this)
                .setStyle(AlertView.Style.ActionSheet)
                .setTitle("选择操作")
                .setMessage(null)
                .setCancelText("取消")
                .setDestructive("拍照", "相册")
                .setOthers(null)
                .setOnItemClickListener { o, position ->
                    when(position){
                        0 -> {
                            toast("拍照")
                        }
                        1 -> {
                            toast("从相册选择")
                        }
                    }
                }
                .build()
                .show()
    }

}
