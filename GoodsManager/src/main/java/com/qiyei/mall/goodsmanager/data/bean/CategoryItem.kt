package com.qiyei.mall.goodsmanager.data.bean

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
data class CategoryItem(val id: Int, //分类ID
                   val categoryName: String, //分类名称
                   val categoryIcon: String = "", //分类图标
                   val parentId: Int, //分类 父级ID
                   var isSelected: Boolean = false//是否被选中
)