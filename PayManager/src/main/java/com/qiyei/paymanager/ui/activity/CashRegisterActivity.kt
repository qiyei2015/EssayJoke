package com.qiyei.paymanager.ui.activity


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.framework.util.YuanFenConverter
import com.qiyei.paymanager.R
import com.qiyei.paymanager.injection.component.DaggerPayManagerComponent
import com.qiyei.paymanager.injection.module.PayManagerModule
import com.qiyei.paymanager.mvp.presenter.CashRegisterPresenter
import com.qiyei.paymanager.mvp.view.ICashRegisterView
import com.qiyei.router.path.RouteMall
import com.qiyei.router.provider.ProviderConstant
import com.qiyei.sdk.log.LogManager
import kotlinx.android.synthetic.main.activity_cash_register.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

@Route(path = RouteMall.PayManager.cash_pay)
class CashRegisterActivity : BaseMVPActivity<CashRegisterPresenter>(),ICashRegisterView {

    companion object {
        const val RESULT_STATUS = "resultStatus"
        const val PAY_SUCCESS_CODE = "9000"
    }

    /**
     * 订单id
     */
    private var mOrderId:Int = 0
    /**
     * 订单总价格
     */
    private var mTotalPrice:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_register)
        //运行在沙箱环境
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)
        initData()
        initView()
    }

    override fun initComponentInject() {
        DaggerPayManagerComponent.builder()
                .activityComponent(mActivityComponent)
                .payManagerModule(PayManagerModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onClick(view: View) {
        super.onClick(view)
        when(view.id){
            R.id.mPayButton -> {
                gotoPay()
            }
        }
    }

    override fun onPaySignCallback(sign: String) {
        LogManager.i(getTAG(), "onPaySignCallback,sign:$sign")
        //后台运行
        doAsync {
            val result:Map<String,String>  = PayTask(mActivityComponent.activity()).payV2(sign,true)
            //UI
            uiThread {
                if (PAY_SUCCESS_CODE.equals(result[RESULT_STATUS])){
                    mPresenter.payOrder(mOrderId)
                }else{
                    toast("支付失败${result["memo"]}")
                }
            }
        }

    }

    override fun onPayOrderCallback(result: Boolean) {
        if (result){
            toast("支付成功")
        }else {
            toast("支付失败")
        }
    }

    private fun initData(){
        mOrderId = intent.getIntExtra(ProviderConstant.KEY_ORDER_ID,0)
        mTotalPrice = intent.getLongExtra(ProviderConstant.KEY_ORDER_PRICE,0)
    }

    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle(getString(R.string.cash_pay))
                .build()
        mOrderIdTextView.text = "订单号:$mOrderId"
        mTotalPriceTextView.text = YuanFenConverter.changeF2YWithUnit(mTotalPrice)
        mPayButton.setOnClickListener(this)
    }

    /**
     * 去支付
     */
    private fun gotoPay(){
        when(mPayRadioGroup.checkedRadioButtonId){
            R.id.mAlipayRadioButton ->  {
                toast("支付宝支付")
                mPresenter.getPaySign(mOrderId,mTotalPrice)
            }
            R.id.mWeixinPayRadioButton ->  {
                toast("微信支付")
            }
            R.id.mBankCardRadioButton ->  {
                toast("银行卡支付")
            }
        }
    }
}
