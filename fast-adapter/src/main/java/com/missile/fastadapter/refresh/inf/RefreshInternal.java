package com.missile.fastadapter.refresh.inf;

import android.view.View;

import com.missile.fastadapter.refresh.constant.SpinnerStyle;
import com.missile.fastadapter.refresh.listener.OnStateChangedListener;


public interface RefreshInternal extends OnStateChangedListener {

    View getView();

    SpinnerStyle getSpinnerStyle();


}
