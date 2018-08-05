package com.missile.fastadapter.refresh.constant;


public enum RefreshState {

    None(0, false, false, false, false);


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
