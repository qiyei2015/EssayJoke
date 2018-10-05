package com.qiyei.mall.usermanager.ui.activity



import android.os.Bundle
import android.view.View
import com.qiyei.mall.usermanager.R
import com.qiyei.mall.usermanager.data.bean.UserInfo
import com.qiyei.mall.usermanager.injection.component.DaggerUserManagerComponent
import com.qiyei.mall.usermanager.injection.module.UploadManagerModule
import com.qiyei.mall.usermanager.injection.module.UserManagerModule
import com.qiyei.mall.usermanager.mvp.presenter.UserInfoModifyPresenter
import com.qiyei.mall.usermanager.mvp.view.IUserInfoModifyView
import kotlinx.android.synthetic.main.activity_user_info_modify.*
import com.jph.takephoto.model.TResult
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UploadManager
import com.qiyei.framework.constant.MallConstant
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.framework.ui.activity.BaseTakePhotoActivity
import com.qiyei.sdk.image.ImageManager
import com.qiyei.sdk.log.LogManager
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.util.concurrent.TimeUnit


class UserInfoModifyActivity : BaseTakePhotoActivity<UserInfoModifyPresenter>(),IUserInfoModifyView {

    /**
     * 用户信息
     */
    private lateinit var mUserInfo: UserInfo
    /**
     * 用户本地头像url
     */
    private lateinit var mLocalAvatarUrl:String
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

    override fun getTAG(): String {
        return UserInfoModifyActivity::class.java.simpleName
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
                showTakePhotoDialog()
            }
        }
    }

    /**
     * 图片选择回调
     */
    override fun takeSuccess(result: TResult?) {
        mLocalAvatarUrl = result?.image?.compressPath?:""
        mPresenter.getUploadToken()
        LogManager.i(getTAG(),"mLocalAvatarUrl:$mLocalAvatarUrl")
    }

    /**
     * token获取回调
     */
    override fun onUploadTokenResult(result: String) {
        LogManager.i(getTAG(),"onUploadTokenResult:$result")
        //上传
        mUploadManager.put(mLocalAvatarUrl, null,result,object : UpCompletionHandler{
            override fun complete(key: String?, info: ResponseInfo?, response: JSONObject?) {
                mUserIconUrl = MallConstant.IMAGE_SERVER_ADDRESS + response?.get("hash")
                LogManager.i(getTAG(),"mUserIconUrl:$mUserIconUrl")

                Observable.timer(0,TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            ImageManager.getInstance().loadImage(mUserIconCircleImageView,mLocalAvatarUrl)
                        }
//                mUserIconCircleImageView.post{
//                    ImageManager.getInstance().loadImage(mUserIconCircleImageView,mLocalAvatarUrl)
//                }
            }
        },null)
    }

    override fun onModifyUserInfoResult(userInfo: UserInfo) {
        toast("修改用户信息成功")
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

}
