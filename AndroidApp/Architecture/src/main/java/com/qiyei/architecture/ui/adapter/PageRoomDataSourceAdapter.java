package com.qiyei.architecture.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.qiyei.architecture.R;
import com.qiyei.framework.database.room.User;

/**
 * @author Created by qiyei2015 on 2019/12/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class PageRoomDataSourceAdapter extends PagedListAdapter<User,PageRoomDataSourceAdapter.ViewHolder> {

    public PageRoomDataSourceAdapter() {
        super(new Diff());
    }

    @NonNull
    @Override
    public PageRoomDataSourceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_my_page_data_source,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PageRoomDataSourceAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameView;
        TextView idView;
        TextView descriptionView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name);
            idView = itemView.findViewById(R.id.id);
            descriptionView = itemView.findViewById(R.id.description);
        }

        void bind(User user){
            idView.setText("id:" + user.getUid());
            nameView.setText("name:" + user.getFirstName());
            descriptionView.setText("description:" + user.getSex());
        }
    }

    static class Diff extends DiffUtil.ItemCallback<User>{
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getUid() == newItem.getUid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    }
}
