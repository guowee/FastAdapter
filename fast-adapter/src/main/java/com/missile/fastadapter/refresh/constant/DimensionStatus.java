package com.missile.fastadapter.refresh.constant;


public enum DimensionStatus {
    DefaultUnNotify(false),//默认值，但是还没通知确认
    Default(true),//默认值
    XmlWrapUnNotify(false),//Xml计算，但是还没通知确认
    XmlWrap(true),//Xml计算
    XmlExactUnNotify(false),//Xml 的view 指定，但是还没通知确认
    XmlExact(true),//Xml 的view 指定
    XmlLayoutUnNotify(false),//Xml 的layout 中指定，但是还没通知确认
    XmlLayout(true),//Xml 的layout 中指定
    CodeExactUnNotify(false),//代码指定，但是还没通知确认
    CodeExact(true),//代码指定
    DeadLockUnNotify(false),//锁死，但是还没通知确认
    DeadLock(true);//锁死

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

    /**
     * 转换为通知状态
     *
     * @return 通知状态
     */
    public DimensionStatus notified() {
        if (!notified) {
            return values()[ordinal() + 1];
        }
        return this;
    }

    /**
     * 是否可以被新的状态替换
     *
     * @param status 新转台
     * @return 小于等于
     */
    public boolean canReplaceWith(DimensionStatus status) {
        return ordinal() < status.ordinal() || ((!notified || CodeExact == this) && ordinal() == status.ordinal());
    }


}
