package cn.xunzi.basiclib.widget.titlebar;

import android.view.View;

/**
 * @author xunzi Android Developer
 * @since 2018/7/3
 */

public interface ITitleBar {

    /**
     * 设置标题栏的布局id
     *
     * @return 标题栏的布局id
     */
    int getViewResId();

    /**
     * 设置完成后的TitleBar
     */
    void onBindTitleBar(View titleView);

}
