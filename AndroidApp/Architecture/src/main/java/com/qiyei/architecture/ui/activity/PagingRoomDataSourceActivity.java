package com.qiyei.architecture.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.qiyei.architecture.R;
import com.qiyei.architecture.ui.adapter.PageRoomDataSourceAdapter;
import com.qiyei.framework.database.room.AppDatabase;
import com.qiyei.framework.database.room.User;
import com.qiyei.framework.database.room.UserDao;
import com.qiyei.sdk.database.DatabaseManager;

import java.util.List;

public class PagingRoomDataSourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paging_room_data_source);

        PageRoomDataSourceAdapter adapter = new PageRoomDataSourceAdapter();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        UserDao userDao = DatabaseManager.getInstance().getRoomDatabase(AppDatabase.class).userDao();
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(20)    // 分页加载的数量
                .setEnablePlaceholders(true) // 是否启用占位符
                .setInitialLoadSizeHint(20) // 初次加载的数量
                .setPrefetchDistance(10)    //预取数据的距离
                .build();

        LiveData<PagedList<User>> data = new LivePagedListBuilder<Integer, User>(userDao.getAllByUid(),config)
                .build();

        data.observe(this, new Observer<PagedList<User>>() {
            @Override
            public void onChanged(PagedList<User> users) {
                adapter.submitList(users);
            }
        });
    }

}
