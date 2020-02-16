package com.qiyei.architecture.viper.data.repository;

import androidx.lifecycle.LiveData;

import com.qiyei.architecture.viper.data.entity.Movie;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2020/2/16.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface IMovieRepository {

    /**
     * 获取所有已保存的
     * @return
     */
    LiveData<List<Movie>> getSavedMovies();

    /**
     * 删除电影
     * @param movie
     */
    void delete(Movie movie);
}
