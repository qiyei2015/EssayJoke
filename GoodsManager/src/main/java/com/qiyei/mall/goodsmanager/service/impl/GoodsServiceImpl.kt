package com.qiyei.mall.goodsmanager.service.impl

import com.qiyei.framework.extend.baseRespConvert
import com.qiyei.mall.goodsmanager.data.bean.Category
import com.qiyei.mall.goodsmanager.data.bean.Goods
import com.qiyei.mall.goodsmanager.data.respository.CategoryRepository
import com.qiyei.mall.goodsmanager.data.respository.GoodsRepository
import com.qiyei.mall.goodsmanager.service.ICategoryService
import com.qiyei.mall.goodsmanager.service.IGoodsService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class GoodsServiceImpl @Inject constructor():IGoodsService {

    @Inject
    lateinit var mRepository: GoodsRepository

    override fun getGoodsList(categoryId: Int, pageNo: Int): Observable<MutableList<Goods>?> {
        return mRepository.getGoodsList(categoryId,pageNo).baseRespConvert()
    }

    override fun getGoodsListByKeyword(keyword: String, pageNo: Int): Observable<MutableList<Goods>?> {
        return mRepository.getGoodsListByKeyword(keyword,pageNo).baseRespConvert()
    }

    override fun getGoodsDetail(goodsId: Int): Observable<Goods> {
        return mRepository.getGoodsDetail(goodsId).baseRespConvert()
    }

}