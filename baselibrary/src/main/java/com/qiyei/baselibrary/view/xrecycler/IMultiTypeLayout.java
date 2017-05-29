package com.qiyei.baselibrary.view.xrecycler;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/19.
 * Version: 1.0
 * Description: RecyclerViewDe多种类型的Item布局
 */
public interface IMultiTypeLayout<T> {

    /**
     * 根据position处的数据返回相应的布局
     * @param t
     * @param position
     * @return
     */
    int getLayoutId(T t, int position);
}
