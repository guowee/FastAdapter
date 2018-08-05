package com.uowee.fastadapter.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.missile.fastadapter.BaseQuickAdapter;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int PAGE_SIZE = 9;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PullToRefreshAdapter mAdapter;
    private int mNextRequestPage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        initSwipeRefreshLayout();
        initAdapter();
        refresh();
    }


    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void initAdapter() {
        mAdapter = new PullToRefreshAdapter(R.layout.card_view_item, null);
        mAdapter.openAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MainActivity.this, ((TextView) adapter.getViewByPosition(position, R.id.content)).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        mAdapter.bind2RecyclerView(mRecyclerView);
    }

    private void refresh() {
        mNextRequestPage = 1;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        new Request(mNextRequestPage, new RequestCallback() {
            @Override
            public void success(List<Status> data) {
                setData(true, data);
                mAdapter.setEnableLoadMore(true);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void fail(Exception e) {
                Toast.makeText(MainActivity.this, R.string.network_err, Toast.LENGTH_LONG).show();
                mAdapter.setEnableLoadMore(true);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }).start();
    }

    private void loadMore() {
        new Request(mNextRequestPage, new RequestCallback() {
            @Override
            public void success(List<Status> data) {
                setData(false, data);
            }

            @Override
            public void fail(Exception e) {
                mAdapter.loadMoreFail();
                Toast.makeText(MainActivity.this, R.string.network_err, Toast.LENGTH_LONG).show();
            }
        }).start();
    }

    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
            Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            mAdapter.loadMoreComplete();
        }
    }


}


interface RequestCallback {
    void success(List<Status> data);

    void fail(Exception e);
}


class Request extends Thread {
    private static final int PAGE_SIZE = 9;
    private int mPage;
    private RequestCallback mCallback;
    private Handler mHandler;


    private static boolean mFirstPageNoMore;
    private static boolean mFirstError = false;


    public Request(int page, RequestCallback callback) {
        mPage = page;
        mCallback = callback;
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (mPage == 2 && mFirstError) {
            mFirstError = false;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.fail(new RuntimeException("Fail"));
                }
            });
        } else {
            int size = PAGE_SIZE;
            if (mPage == 1) {
                if (mFirstPageNoMore) {
                    size = 2; //下拉加载2条
                }
                mFirstPageNoMore = !mFirstPageNoMore;
                if (!mFirstError) {
                    mFirstError = true;
                }
            } else if (mPage == 4) {
                size = 1;
            }


            final int dataSize = size;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.success(DataServer.getSampleData(dataSize));
                }
            });
        }


    }
}
