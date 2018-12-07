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

import api.MyServiceFactory;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.AboutUsBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.image.PicUtils;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.ABOUTUS;

/**
 * 关于我们
 */
@Route(path = ABOUTUS)
public class AboutUsActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private String type;
    private TextView tvTitle;
    private android.webkit.WebView webContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initComponents() {
        initView();
        type = SharedPreferencesUtils.getString(this, "identity", "user");
        backImg.setOnClickListener(v -> finish());
        if (type.equals("user")) {
            getData();
            tvTitle.setText("ABOUT US");
        } else {
            engGetData();
        }
    }

    //用户端获取数据
    private void getData() {
        MyServiceFactory.getAboutUs().safeSubscribe(new AbstractDialogSubscriber<AboutUsBean>(this) {
            @Override
            public void onNext(AboutUsBean aboutUsBean) {
                webContent.loadDataWithBaseURL(null, aboutUsBean.getValue(), "text/html", "UTF-8", null);
            }
        });
    }

    //工程师端获取数据
    private void engGetData() {
        MyServiceFactory.engGetAboutus().safeSubscribe(new AbstractDialogSubscriber<AboutUsBean>(this) {
            @Override
            public void onNext(AboutUsBean aboutUsBean) {
                webContent.loadDataWithBaseURL(null, aboutUsBean.getValue(), "text/html", "UTF-8", null);

            }
        });
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        tvTitle = findViewById(R.id.tv_title);
        webContent = findViewById(R.id.web_content);

        WebSettings webSettings = webContent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                imgReset(view);
            }
        });
    }

    private void imgReset(WebView webView) {
        webView.loadUrl("javascript:(function(){" + "var objs = document.getElementsByTagName('img'); " + "for(var i=0;i<objs.length;i++)  " + "{" + "var img = objs[i];   " + "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " + "}" + "})()");
    }
}
