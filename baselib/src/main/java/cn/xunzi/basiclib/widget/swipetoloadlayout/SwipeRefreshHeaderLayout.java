package cn.xunzi.basiclib.widget.swipetoloadlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import cn.xunzi.basiclib.R;

/**
 * Created by Aspsine on 2015/8/13.
 */
public class SwipeRefreshHeaderLayout extends FrameLayout implements SwipeRefreshTrigger, SwipeTrigger {

    GifView gifView;
    public SwipeRefreshHeaderLayout(Context context) {
        this(context, null);
    }

    public SwipeRefreshHeaderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRefreshHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gifView= findViewById(R.id.gvRefresh);
    }

    @Override
    public void onRefresh() {
        if (gifView!=null){
            gifView.play();
        }
    }

    @Override
    public void onPrepare() {
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        if (gifView!=null){
            gifView.pause();
        }
    }

    @Override
    public void onReset() {
        if (gifView!=null){
            gifView.play();
        }
    }
}
