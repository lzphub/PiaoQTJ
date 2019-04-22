package cn.xunzi.basiclib.widget.titlebar;

import android.view.View;
import android.widget.TextView;

import cn.xunzi.basiclib.R;

/**
 * description: 左返回键、中间标题 的标题栏
 *
 * @author xunzi Android Developer
 * @since 2018/7/3
 */

public class SingleTextTitle implements ITitleBar {

    String title;
    private View titleView;

    public SingleTextTitle(String title) {
        this.title = title;
    }

    @Override
    public int getViewResId() {
        return R.layout.layout_single_text_title;
    }

    @Override
    public void onBindTitleBar(View titleView) {
        this.titleView=titleView;
        TextView tv = titleView.findViewById(R.id.tv_title);
        tv.setText(title);
    }

    public View getTitleView() {
        return titleView;
    }
}
