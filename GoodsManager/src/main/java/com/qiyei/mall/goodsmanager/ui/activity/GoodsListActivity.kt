package com.qiyei.mall.goodsmanager.ui.activity



import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.kennyc.view.MultiStateView
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.mall.goodsmanager.R
import com.qiyei.mall.goodsmanager.common.GoodsConstant
import com.qiyei.mall.goodsmanager.common.startLoading
import com.qiyei.mall.goodsmanager.data.bean.Goods
import com.qiyei.mall.goodsmanager.injection.component.DaggerGoodsComponent
import com.qiyei.mall.goodsmanager.injection.module.GoodsModule
import com.qiyei.mall.goodsmanager.mvp.presenter.GoodsListPresenter
import com.qiyei.mall.goodsmanager.mvp.view.IGoodsListView
import com.qiyei.mall.goodsmanager.ui.adapter.GoodsListAdapter
import com.qiyei.sdk.log.LogManager
import kotlinx.android.synthetic.main.activity_goods_list.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class GoodsListActivity : BaseMVPActivity<GoodsListPresenter>(),IGoodsListView, BGARefreshLayout.BGARefreshLayoutDelegate{

    /**
     * 适配器
     */
    private lateinit var mGoodsListAdapter:GoodsListAdapter
    /**
     * 商品分类id
     */
    private var mCategoryId:Int = 1

    /**
     * 当前页
     */
    private var mCurrentPage:Int = 1
    /**
     * 最大页
     */
    private var mMaxPage:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_list)
        mCategoryId = intent.getIntExtra(GoodsConstant.KEY_CATEGORY_ID, 1)
        initView()
        initRefreshLayout()
        loadData(mCategoryId)
    }

    override fun initComponentInject() {
        DaggerGoodsComponent.builder()
                .activityComponent(mActivityComponent)
                .goodsModule(GoodsModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun getTAG(): String {
        return GoodsListActivity::class.java.simpleName
    }

    override fun onGoodsListResult(list: MutableList<Goods>?) {
        mRefreshLayout.endRefreshing()
        mRefreshLayout.endLoadingMore()
        if (list == null || list.size == 0){
            toast("数据错误")
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
            return
        }
        mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        mMaxPage = list[0].maxPage
        //第一页,直接设置进去
        if (mCurrentPage == 1){
            mGoodsListAdapter.datas = list
        }else {
            //添加到数据后面，直接显示
            mGoodsListAdapter.datas.addAll(list)
            mGoodsListAdapter.notifyDataSetChanged()
        }
    }

    /**
     * 开始下来刷新
     */
    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
        mCurrentPage = 1
        loadData(intent.getIntExtra(GoodsConstant.KEY_CATEGORY_ID, 1))
        LogManager.i(getTAG(),"onBGARefreshLayoutBeginRefreshing")
    }

    /**
     * 开始上拉加载更多
     */
    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        if (mCurrentPage < mMaxPage){
            mCurrentPage++
            loadData(mCategoryId)
            true
        }
        toast("没有数据了")
        return false
    }


    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle(getString(R.string.goods_list))
                .build()

        mGoodsListRecyclerView.layoutManager = GridLayoutManager(this,2)
        mGoodsListAdapter = GoodsListAdapter(this, arrayListOf())
        mGoodsListRecyclerView.adapter = mGoodsListAdapter
        mGoodsListAdapter.setOnItemClickListener { view, item, position ->
            startActivity<GoodsDetailActivity>(GoodsConstant.KEY_GOODS_ID to item.id)
        }
    }

    /**
     * 初始化刷新视图
     */
    private fun initRefreshLayout() {
        mRefreshLayout.setDelegate(this)
        val viewHolder = BGANormalRefreshViewHolder(this, true)
        viewHolder.setLoadMoreBackgroundColorRes(R.color.common_bg)
        viewHolder.setRefreshViewBackgroundColorRes(R.color.common_bg)
        mRefreshLayout.setRefreshViewHolder(viewHolder)
    }

    private fun loadData(categoryId:Int){
        mMultiStateView.startLoading()
        mPresenter.getGoodsList(categoryId, mCurrentPage)
    }

}
