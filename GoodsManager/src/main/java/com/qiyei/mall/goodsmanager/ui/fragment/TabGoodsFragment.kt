package com.qiyei.mall.goodsmanager.ui.fragment


import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kennyc.view.MultiStateView
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.framework.ui.fragment.BaseMVPFragment
import com.qiyei.mall.goodsmanager.R
import com.qiyei.mall.goodsmanager.common.GoodsConstant
import com.qiyei.mall.goodsmanager.common.startLoading
import com.qiyei.mall.goodsmanager.data.bean.Category
import com.qiyei.mall.goodsmanager.injection.component.DaggerCategoryComponent
import com.qiyei.mall.goodsmanager.injection.module.CategoryModule
import com.qiyei.mall.goodsmanager.mvp.presenter.CategoryManagerPresenter
import com.qiyei.mall.goodsmanager.mvp.view.ICategoryManagerView
import com.qiyei.mall.goodsmanager.ui.activity.GoodsListActivity
import com.qiyei.mall.goodsmanager.ui.adapter.FirstCategoryAdapter
import com.qiyei.mall.goodsmanager.ui.adapter.SecondCategoryAdapter
import kotlinx.android.synthetic.main.fragment_category.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class TabGoodsFragment : BaseMVPFragment<CategoryManagerPresenter>(),ICategoryManagerView {

    /**
     * 一级分类Adapter
     */
    private lateinit var mFirstCategoryAdapter:FirstCategoryAdapter
    /**
     * 二级Adapter
     */
    private lateinit var mSecondCategoryAdapter: SecondCategoryAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadData()
    }

    /**
     * 依赖注入
     */
    override fun initComponentInject() {
        DaggerCategoryComponent.builder()
                .activityComponent(mActivityComponent)
                .categoryModule(CategoryModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun getTAG(): String {
        return TabGoodsFragment::class.java.simpleName
    }

    /**
     * presenter层回调
     */
    override fun onCategoryResult(result: MutableList<Category>?) {
        if (result == null || result.size == 0){
            toast("获取分类失败")
            //没有数据
            mTopCategoryImageView.visibility = View.INVISIBLE
            mCategoryTitleTextView.visibility = View.INVISIBLE
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
            return
        }
        //一级目录
        if (result[0].parentId == 0){
            result[0].isSelected = true
            mFirstCategoryAdapter.datas = result
            mPresenter.getCategory(result[0].id)
        }else {
            mTopCategoryImageView.visibility = View.VISIBLE
            mCategoryTitleTextView.visibility = View.VISIBLE
            mSecondCategoryAdapter.datas = result
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        }
    }

    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle(getString(R.string.goods_category))
                .setLeftViewVisible(false)
                .build()

        mFirstCategoryAdapter = FirstCategoryAdapter(context!!, arrayListOf())
        val layoutLayout = LinearLayoutManager(context)
        layoutLayout.orientation = LinearLayoutManager.VERTICAL
        mFirstCategoryRecyclerView.layoutManager = layoutLayout
        mFirstCategoryRecyclerView.adapter = mFirstCategoryAdapter
        mFirstCategoryAdapter.setOnItemClickListener { view, t, position ->
            for (category in mFirstCategoryAdapter.datas) {
                category.isSelected = t.id == category.id
            }
            mFirstCategoryAdapter.notifyDataSetChanged()
            loadData(t.id)
        }

        mSecondCategoryAdapter = SecondCategoryAdapter(context!!, arrayListOf())
        val girdLayout = GridLayoutManager(context,3)
        mSecondCategoryRecyclerView.layoutManager = girdLayout
        mSecondCategoryRecyclerView.adapter = mSecondCategoryAdapter
        mSecondCategoryAdapter.setOnItemClickListener { view, t, position ->
            startActivity<GoodsListActivity>(GoodsConstant.KEY_CATEGORY_ID to t.id)
        }

    }

    private fun loadData(parentId:Int = 0){
        if (parentId != 0){
            mMultiStateView.startLoading()
        }
        mPresenter.getCategory(parentId)
    }

}
