package cn.xunzi.basiclib.widget.wheelview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

public class MyNestedScrollView extends NestedScrollView {

    //对外暴露的一个ScrollView监听的接口
    public MyScrollViewListener myScrollViewListener = null;
    public MyNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnMyScrollListener(MyScrollViewListener myScrollViewListener) {
        this.myScrollViewListener = myScrollViewListener;
    }


    //重写onScrollChanged的ScrollView监听
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        /**
         * x:    当前横向滑动距离
         * y:    当前纵向滑动距离
         *oldx:  之前横向滑动距离
         *oldy:  之前纵向滑动距离
         */
        if (myScrollViewListener != null) {
            //这里判断向上或向下滑动是因为后面要使用到
            if (oldy < y)//向上滑动
                myScrollViewListener.onMyScrollView(y, oldy, true);
            else if (oldy > y)//向下滑动
                myScrollViewListener.onMyScrollView(y, oldy, false);
        }
    }


    public interface MyScrollViewListener {
        void onMyScrollView(int y, int oldy, boolean isUp);
    }
}
