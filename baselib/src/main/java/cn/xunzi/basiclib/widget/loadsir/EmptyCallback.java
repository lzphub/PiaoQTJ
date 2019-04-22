package cn.xunzi.basiclib.widget.loadsir;


import cn.xunzi.basiclib.R;
import cn.xunzi.basiclib.widget.loadsir.callback.Callback;

public class EmptyCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_empty;
    }
}
