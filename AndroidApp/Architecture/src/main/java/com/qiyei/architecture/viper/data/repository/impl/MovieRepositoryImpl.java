package com.qiyei.architecture.viper.data.repository.impl;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.qiyei.architecture.viper.data.entity.Movie;
import com.qiyei.architecture.viper.data.repository.IMovieRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by qiyei2015 on 2020/2/16.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MovieRepositoryImpl implements IMovieRepository {


    @Override
    public LiveData<List<Movie>> getSavedMovies() {
        MediatorLiveData<List<Movie>> liveData = new MediatorLiveData<>();
        List<Movie> movieList = new ArrayList<>();

        for (int i = 0 ;i < 100;i++){
            movieList.add(new Movie());
        }
        liveData.postValue(movieList);
        return liveData;
    }

    @Override
    public void delete(Movie movie) {

    }
}
