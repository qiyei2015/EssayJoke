package com.qiyei.architecture.viper.presenter;

import android.util.Log;

import androidx.lifecycle.Observer;

import com.qiyei.architecture.viper.contract.MainContract;
import com.qiyei.architecture.viper.data.entity.Movie;
import com.qiyei.architecture.viper.interactor.MovieInteractor;
import com.qiyei.architecture.viper.view.activity.MovieListActivity;

import java.util.List;
import java.util.Set;

/**
 * @author Created by qiyei2015 on 2020/2/16.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MoviePresenter implements MainContract.Presenter,MainContract.InteractorOutput {

    private static final String TAG = "MoviePresenter";


    private MainContract.View mView;

    private MainContract.Interactor mInteractor;

    public MoviePresenter(MainContract.View view) {
        mView = view;
        mInteractor = new MovieInteractor();
    }

    @Override
    public void deleteMoviesClick(Set<Movie> selectedMovies) {
        for (Movie movie : selectedMovies){
            mInteractor.delete(movie);
        }
    }

    @Override
    public void onViewCreated() {
        mView.showLoading();
        mInteractor.loadMovieList().observe((MovieListActivity) mView, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.i(TAG,"onChanged ");
                if (movies != null){
                    onQuerySuccess(movies);
                } else {
                    onQueryError();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        mView = null;
        mInteractor = null;
    }

    @Override
    public void addMovieClick() {
        //通过Router跳转到另外一个界面
    }

    @Override
    public void onQuerySuccess(List<Movie> data) {
        mView.hideLoading();
        mView.displayMovieList(data);
    }

    @Override
    public void onQueryError() {
        mView.hideLoading();
        mView.showMessage("数据加载错误");
    }
}
