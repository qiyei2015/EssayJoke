package com.qiyei.architecture.viper.interactor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.qiyei.architecture.viper.contract.MainContract;
import com.qiyei.architecture.viper.data.entity.Movie;
import com.qiyei.architecture.viper.data.repository.IMovieRepository;
import com.qiyei.architecture.viper.data.repository.impl.MovieRepositoryImpl;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2020/2/16.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MovieInteractor implements MainContract.Interactor {

    private MediatorLiveData<List<Movie>> movieList = new MediatorLiveData<>();

    private IMovieRepository repository = new MovieRepositoryImpl();

    public MovieInteractor() {
        getAllMovies();
    }

    @Override
    public LiveData<List<Movie>> loadMovieList() {
        return movieList;
    }

    @Override
    public void delete(Movie movie) {
        repository.delete(movie);
    }

    @Override
    public void getAllMovies() {
        movieList.addSource(repository.getSavedMovies(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                //更新数据
                movieList.postValue(movies);
            }
        });
    }

}
