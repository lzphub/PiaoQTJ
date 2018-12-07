package cn.dankal.my.activity;


import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.COMPORDATA;

/**
 * 常见问题详情
 */
@Route(path = COMPORDATA)
public class ComPorDataActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private WebView title;
    private String content;

    private TextView tvTitle;


    @Override
    protected int getLayoutId() {
        content = getIntent().getStringExtra("data");
        return R.layout.activity_com_por_data;
    }

    @Override
    protected void initComponents() {
        initView();
        backImg.setOnClickListener(v -> finish());
        String type = SharedPreferencesUtils.getString(this, "identity", "user");
        if (type.equals("user")) {
            tvTitle.setText("FAQ");
        }
    }

    private void initView() {
        title = findViewById(R.id.title);
        backImg = findViewById(R.id.back_img);
        tvTitle = findViewById(R.id.tv_title);

        WebSettings webSettings = title.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        title.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                imgReset(view);
            }
        });
        title.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
    }

    private void imgReset(WebView webView) {
        webView.loadUrl("javascript:(function(){" + "var objs = document.getElementsByTagName('img'); " + "for(var i=0;i<objs.length;i++)  " + "{" + "var img = objs[i];   " + "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " + "}" + "})()");
    }
}
