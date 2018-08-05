package com.missile.fastadapter.refresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v4.view.NestedScrollingParent;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.missile.fastadapter.R;
import com.missile.fastadapter.refresh.constant.DimensionStatus;
import com.missile.fastadapter.refresh.constant.RefreshState;
import com.missile.fastadapter.refresh.constant.SpinnerStyle;
import com.missile.fastadapter.refresh.impl.RefreshContentWrapper;
import com.missile.fastadapter.refresh.inf.RefreshContent;
import com.missile.fastadapter.refresh.inf.RefreshFooter;
import com.missile.fastadapter.refresh.inf.RefreshHeader;
import com.missile.fastadapter.refresh.inf.RefreshInternal;
import com.missile.fastadapter.refresh.inf.RefreshKernel;
import com.missile.fastadapter.refresh.inf.RefreshLayout;
import com.missile.fastadapter.refresh.inf.ScrollBoundaryDecider;
import com.missile.fastadapter.refresh.listener.OnLoadMoreListener;
import com.missile.fastadapter.refresh.listener.OnRefreshListener;
import com.missile.fastadapter.util.DensityUtil;

import java.util.logging.Handler;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class AgileRefreshLayout extends ViewGroup implements RefreshLayout, NestedScrollingParent {

    protected int mHeaderHeight;//头部高度 头部高度状态
    protected DimensionStatus mHeaderHeightStatus = DimensionStatus.DefaultUnNotify;
    protected int mFooterHeight;//底部高度 底部高度状态
    protected DimensionStatus mFooterHeightStatus = DimensionStatus.DefaultUnNotify;

    protected RefreshState mState = RefreshState.None;

    protected RefreshInternal mRefreshHeader;// Header 视图
    protected RefreshInternal mRefreshFooter;// Footer 视图
    protected RefreshContent mRefreshContent;// Content 视图
    protected int mHeaderBackgroundColor = 0;                   //为Header绘制纯色背景
    protected int mFooterBackgroundColor = 0;
    protected boolean mHeaderNeedTouchEventWhenRefreshing;      //为游戏Header提供独立事件
    protected boolean mFooterNeedTouchEventWhenLoading;

    protected ScrollBoundaryDecider mScrollBoundaryDecider;

    protected boolean mEnableLoadMoreWhenContentNotFull = true;//在内容不满一页的时候，是否可以上拉加载更多
    protected RefreshKernel mKernel = new RefreshKernelImpl();

    protected Handler mHandler;
    protected int mFixedHeaderViewId = View.NO_ID;
    protected int mFixedFooterViewId = View.NO_ID;
    protected boolean mEnableLoadMore = false;
    protected boolean mManualLoadMore = false;//是否手动设置过LoadMore，用于智能开启


    public AgileRefreshLayout(Context context) {
        this(context, null);
    }

    public AgileRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AgileRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setClipToPadding(false);

        DensityUtil density = new DensityUtil();
        mHeaderHeight = density.dip2px(100);
        mFooterHeight = density.dip2px(60);


        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AgileRefreshLayout);

        mHeaderHeight = ta.getDimensionPixelOffset(R.styleable.AgileRefreshLayout_srlHeaderHeight, mHeaderHeight);
        mFooterHeight = ta.getDimensionPixelOffset(R.styleable.AgileRefreshLayout_srlFooterHeight, mFooterHeight);

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        return super.drawChild(canvas, child, drawingTime);
    }

    @Override
    public ViewGroup getLayout() {
        return this;
    }

    @Override
    public RefreshState getState() {
        return mState;
    }

    @Override
    public RefreshLayout setRefreshHeader(RefreshHeader header) {
        return setRefreshHeader(header, MATCH_PARENT, WRAP_CONTENT);
    }

    @Override
    public RefreshLayout setRefreshHeader(RefreshHeader header, int width, int height) {
        if (mRefreshHeader != null) {
            super.removeView(mRefreshHeader.getView());
        }
        this.mRefreshHeader = header;
        this.mHeaderBackgroundColor = 0;
        this.mHeaderNeedTouchEventWhenRefreshing = false;
        this.mHeaderHeightStatus = mHeaderHeightStatus.unNotify();

        if (header.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
            super.addView(mRefreshHeader.getView(), 0, new LayoutParams(width, height));
        } else {
            super.addView(mRefreshHeader.getView(), width, height);
        }
        return this;
    }

    @Override
    public RefreshLayout setRefreshContent(View contentView) {
        return setRefreshContent(contentView, MATCH_PARENT, MATCH_PARENT);
    }

    @Override
    public RefreshLayout setRefreshContent(View contentView, int width, int height) {
        final View thisView = this;
        if (mRefreshContent != null) {
            super.removeView(mRefreshContent.getView());
        }
        super.addView(contentView, 0, new LayoutParams(width, height));
        if (mRefreshHeader != null && mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
            super.bringChildToFront(contentView);
            if (mRefreshFooter != null && mRefreshFooter.getSpinnerStyle() != SpinnerStyle.FixedBehind) {
                super.bringChildToFront(mRefreshFooter.getView());
            }
        } else if (mRefreshFooter != null && mRefreshFooter.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
            super.bringChildToFront(contentView);
            if (mRefreshHeader != null && mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
                super.bringChildToFront(mRefreshHeader.getView());
            }
        }

        mRefreshContent = new RefreshContentWrapper(contentView);
        if (mHandler != null) {
            View fixedHeaderView = mFixedHeaderViewId > 0 ? thisView.findViewById(mFixedHeaderViewId) : null;
            View fixedFooterView = mFixedFooterViewId > 0 ? thisView.findViewById(mFixedFooterViewId) : null;

            mRefreshContent.setScrollBoundaryDecider(mScrollBoundaryDecider);
            mRefreshContent.setEnableLoadMoreWhenContentNotFull(mEnableLoadMoreWhenContentNotFull);
            mRefreshContent.setUpComponent(mKernel, fixedHeaderView, fixedFooterView);
        }
        return this;
    }

    @Override
    public RefreshLayout setRefreshFooter(RefreshFooter footer) {
        return setRefreshFooter(footer, MATCH_PARENT, WRAP_CONTENT);
    }

    @Override
    public RefreshLayout setRefreshFooter(RefreshFooter footor, int width, int height) {
        if (mRefreshFooter != null) {
            super.removeView(mRefreshFooter.getView());
        }
        this.mRefreshFooter = footor;
        this.mFooterBackgroundColor = 0;
        this.mFooterNeedTouchEventWhenLoading = false;
        this.mFooterHeightStatus = mFooterHeightStatus.unNotify();
        this.mEnableLoadMore = !mManualLoadMore || mEnableLoadMore;
        if (mRefreshFooter.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
            super.addView(mRefreshFooter.getView(), 0, new LayoutParams(width, height));
        } else {
            super.addView(mRefreshFooter.getView(), width, height);
        }
        return this;
    }

    @Override
    public RefreshLayout setFooterHeight(float dp) {
        return null;
    }

    @Override
    public RefreshLayout setHeaderHeight(float dp) {
        return null;
    }

    @Override
    public RefreshLayout setOnRefreshListener(OnRefreshListener listener) {
        return null;
    }

    @Override
    public RefreshLayout setOnLoadMoreListener(OnLoadMoreListener listener) {
        return null;
    }

    @Override
    public RefreshLayout setScrollBoundaryDecider(ScrollBoundaryDecider boundaryDecider) {
        return null;
    }


    public class RefreshKernelImpl implements RefreshKernel {

    }
}
