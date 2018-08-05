package com.missile.fastadapter.refresh.internal;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.missile.fastadapter.refresh.inf.RefreshInternal;

public abstract class InternalAbstract extends RelativeLayout implements RefreshInternal{
    public InternalAbstract(Context context) {
        super(context);
    }

    public InternalAbstract(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InternalAbstract(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
