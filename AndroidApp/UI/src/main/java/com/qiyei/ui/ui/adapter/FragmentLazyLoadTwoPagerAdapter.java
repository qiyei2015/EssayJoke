package com.qiyei.ui.ui.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.qiyei.ui.ui.fragment.FragmentLazyLoadOne;
import com.qiyei.ui.ui.fragment.FragmentLazyLoadTwo;

import org.jetbrains.annotations.NotNull;

/**
 * @author Created by qiyei2015 on 2018/6/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: https://andyjennifer.com/2020/01/19/Androidx%E4%B8%8BFragment%E7%9A%84%E6%87%92%E5%8A%A0%E8%BD%BD/
 */
public class FragmentLazyLoadTwoPagerAdapter extends FragmentPagerAdapter {

    /**
     *
     * @param fm
     */
    @Deprecated
    public FragmentLazyLoadTwoPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    /**
     * 懒加载时需要指定behavior 为BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
     * @param fm
     * @param behavior
     */
    public FragmentLazyLoadTwoPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new MyFragment("懒加载方式2 ViewPager ->>" + position);
    }

    @Override
    public int getCount() {
        return 100;
    }

    public static class MyFragment extends FragmentLazyLoadTwo {

        public MyFragment(@NotNull String name) {
            super(name);
        }

        @Override
        public void lazyInit() {

        }
    }
}
