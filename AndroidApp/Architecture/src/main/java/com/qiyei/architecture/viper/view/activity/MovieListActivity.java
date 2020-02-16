package com.qiyei.architecture.viper.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.qiyei.architecture.R;
import com.qiyei.architecture.viper.contract.MainContract;
import com.qiyei.architecture.viper.data.entity.Movie;
import com.qiyei.architecture.viper.presenter.MoviePresenter;
import com.qiyei.architecture.viper.view.adapter.MovieListAdapter;
import com.qiyei.sdk.https.dialog.LoadingManager;
import com.qiyei.sdk.util.ToastUtil;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2020/2/16.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MovieListActivity extends AppCompatActivity implements View.OnClickListener,MainContract.View {

    private static final String TAG = "MovieListActivity";

    private RecyclerView mRecyclerView;

    private MainContract.Presenter mPresenter;

    private MovieListAdapter mMovieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        mRecyclerView = findViewById(R.id.recycler_view);

        mMovieListAdapter = new MovieListAdapter(this,R.layout.recyclerview_item);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mMovieListAdapter);

        mPresenter = new MoviePresenter(this);
        mPresenter.onViewCreated();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_movie){
            mPresenter.addMovieClick();
        }
    }

    @Override
    public void showLoading() {
        mRecyclerView.setEnabled(false);
        LoadingManager.showDialog(getSupportFragmentManager(),TAG);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.setEnabled(true);
        LoadingManager.dismissDialog(getSupportFragmentManager(),TAG);
    }

    @Override
    public void showMessage(String msg) {
        ToastUtil.showLongToast(msg);
    }

    @Override
    public void displayMovieList(List<Movie> movieList) {
        mMovieListAdapter.setData(movieList);
    }

    @Override
    public void deleteMoviesClicked() {
        //mPresenter.deleteMoviesClick();
    }

}
