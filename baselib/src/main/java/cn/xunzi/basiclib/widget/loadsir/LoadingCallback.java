package cn.xunzi.basiclib.widget.loadsir;

import android.content.Context;
import android.view.View;

import cn.xunzi.basiclib.R;
import cn.xunzi.basiclib.widget.loadsir.callback.Callback;


/**
 * @author leaflc
 */

public class LoadingCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_loading;
    }

    @Override
    public boolean getSuccessVisible() {
        return super.getSuccessVisible();
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
