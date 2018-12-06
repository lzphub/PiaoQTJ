package cn.dankal.my.activity;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.image.PicUtils;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.COMPORDATA;

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
        backImg = findViewById(R.id.back_img);
        title = findViewById(R.id.title);
        tvTitle = findViewById(R.id.tv_title);
        WebSettings webSettings=title.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        title.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                imgReset(view);
            }
        });
        title.loadDataWithBaseURL(null,content, "text/html", "UTF-8", null);
    }
    private void imgReset(WebView webView) {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }
}
