package cn.xunzi.basiclib.base.fragmentactivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * description: 不可滑动的ViewPager
 *
 * @author vane
 * @since 2018/3/16
 */

public class UnSlidableViewPager extends ViewPager {

    public UnSlidableViewPager(@NonNull Context context) {
        super(context);
    }

    public UnSlidableViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }


}
