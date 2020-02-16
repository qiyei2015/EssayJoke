package com.qiyei.architecture.viper.contract;

import androidx.lifecycle.LiveData;

import com.qiyei.architecture.viper.data.entity.Movie;

import java.util.List;
import java.util.Set;

/**
 * @author Created by qiyei2015 on 2020/2/16.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface MainContract {

    interface View {
        void showLoading();
        void hideLoading();
        void showMessage(String msg);
        void displayMovieList(List<Movie> movieList);
        void deleteMoviesClicked();
    }

    interface Presenter {
        void deleteMoviesClick(Set<Movie> selectedMovies);

        void onViewCreated();

        void onDestroy();

        void addMovieClick();
    }

    interface Interactor {
        LiveData<List<Movie>> loadMovieList();

        void delete(Movie movie);

        void getAllMovies();
    }

    interface InteractorOutput {
        void onQuerySuccess(List<Movie> data);

        void onQueryError();
    }

}
