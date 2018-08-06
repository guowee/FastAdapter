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
import com.missile.fastadapter.refresh.api.RefreshLayout;
import com.missile.fastadapter.refresh.listener.OnLoadMoreListener;
import com.missile.fastadapter.refresh.listener.OnRefreshListener;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RefreshLayout mSwipeRefreshLayout;
    private PullToRefreshAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        initSwipeRefreshLayout();
        initAdapter();
    }


    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout layout) {
                mSwipeRefreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mAdapter.getItemCount() < 2) {
                            mAdapter.replaceData(DataServer.getSampleMovies());
                        }
                        mSwipeRefreshLayout.finishRefresh();
                    }
                }, 2000);
            }
        });

        mSwipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout layout) {
                mAdapter.addData(DataServer.getSampleMovies());
                mSwipeRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        });

    }

    private void initAdapter() {
        mAdapter = new PullToRefreshAdapter(R.layout.card_view_item);
        mAdapter.replaceData(DataServer.getSampleMovies());
        mAdapter.openAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MainActivity.this, ((TextView) adapter.getViewByPosition(position, R.id.lmi_title)).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.bind2RecyclerView(mRecyclerView);
    }
}

