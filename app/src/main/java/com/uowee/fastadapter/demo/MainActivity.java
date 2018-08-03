package com.uowee.fastadapter.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.missile.fastadapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<HomeItem> homeItems;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        getHomeItems();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        MyAdapter adapter = new MyAdapter(R.layout.item_home, homeItems);
        adapter.setDuration(1000);
        adapter.openAnimation(BaseQuickAdapter.SCALEIN);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MainActivity.this, ((TextView) adapter.getViewByPosition(position, R.id.content)).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        adapter.bind2RecyclerView(mRecyclerView);
    }

    private void getHomeItems() {
        homeItems = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            homeItems.add(new HomeItem(R.mipmap.ic_launcher, "RecyclerView Item-----" + i));
        }
    }
}
