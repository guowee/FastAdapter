package com.missile.fastadapter.refresh.constant;


public enum DimensionStatus {
    DefaultUnNotify(false),//默认值，但是还没通知确认
    Default(true);

    public final boolean notified;

    DimensionStatus(boolean notified) {
        this.notified = notified;
    }

    public DimensionStatus unNotify() {
        if (notified) {
            DimensionStatus prev = values()[ordinal() - 1];
            if (!prev.notified) {
                return prev;
            }
            return DefaultUnNotify;
        }
        return this;
    }
}
