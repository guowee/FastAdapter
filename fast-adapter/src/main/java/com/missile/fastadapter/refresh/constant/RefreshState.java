package com.missile.fastadapter.refresh.constant;


public enum RefreshState {

    None(0, false, false, false, false),
    PullDownToRefresh(1, true, false, false, false), PullUpToLoad(2, true, false, false, false),
    PullDownCanceled(1, false, false, false, false), PullUpCanceled(2, false, false, false, false),
    ReleaseToRefresh(1, true, false, false, false), ReleaseToLoad(2, true, false, false, false),
    ReleaseToTwoLevel(1, true, false, false, true), TwoLevelReleased(1, false, false, false, true),
    RefreshReleased(1, false, false, false, false), LoadReleased(2, false, false, false, false),
    Refreshing(1, false, true, false, false), Loading(2, false, true, false, false), TwoLevel(1, false, true, false, true),
    RefreshFinish(1, false, false, true, false), LoadFinish(2, false, false, true, false), TwoLevelFinish(1, false, false, true, true);


    public final boolean isHeader;
    public final boolean isFooter;
    public final boolean isTwoLevel;
    public final boolean isDragging;
    public final boolean isOpening;
    public final boolean isFinishing;


    RefreshState(int role, boolean dragging, boolean opening, boolean finishing, boolean twoLevel) {
        this.isHeader = role == 1;
        this.isFooter = role == 2;
        this.isDragging = dragging;
        this.isTwoLevel = twoLevel;
        this.isOpening = opening;
        this.isFinishing = finishing;
    }

    public RefreshState toHeader() {
        return this;
    }

    public RefreshState toFooter() {
        return this;
    }


}
