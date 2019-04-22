package cn.xunzi.basiclib.widget.titlebar;

import android.view.View;
import android.widget.TextView;

import cn.xunzi.basiclib.R;

/**
 * description: 左返回键、中间标题 的标题栏，透明的
 * TitleBarUtils.init(Activity activity, int layoutResId,boolean isThrough)
 * @author xunzi Android Developer
 * @since 2018/7/3
 */

public class SingleTextTitleTransparent implements ITitleBar {

    String title;

    public SingleTextTitleTransparent(String title) {
        this.title = title;
    }

    @Override
    public int getViewResId() {
        return R.layout.layout_single_text_title_transparent;
    }

    @Override
    public void onBindTitleBar(View titleView) {
        TextView tv = titleView.findViewById(R.id.tv_title);
        tv.setText(title);
    }


}
