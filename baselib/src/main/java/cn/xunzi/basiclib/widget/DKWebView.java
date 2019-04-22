package cn.xunzi.basiclib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by fred on 2017/5/25.
 */

public class DKWebView extends WebView {

    public DKWebView(Context context) {
        super(context.getApplicationContext());
    }

    public DKWebView(Context context, AttributeSet attrs) {
        super(context.getApplicationContext(), attrs);
    }

    public DKWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context.getApplicationContext(), attrs, defStyleAttr);
    }
}
