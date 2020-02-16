package com.qiyei.architecture.viper.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qiyei.architecture.viper.data.entity.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by qiyei2015 on 2020/2/16.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private Context mContext;

    private List<Movie> mMovieList;

    private int mLayoutId;


    public MovieListAdapter(Context context,int layoutId) {
        mContext = context;
        mMovieList = new ArrayList<>();
        mLayoutId = layoutId;
    }

    public void setData(List<Movie> movieList){
        mMovieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutId,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
