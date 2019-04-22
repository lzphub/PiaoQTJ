package cn.xunzi.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.xunzi.address.R;
import cn.xunzi.basiclib.base.activity.BaseActivity;

import static cn.xunzi.basiclib.protocol.HomeProtocol.SYSTEMMSG;

@Route(path = SYSTEMMSG)
public class SystemMsgActivity extends BaseActivity {

    private android.widget.ImageView ivBack;
    private TextView webContent;

    private String fText="&lt;p&gt;&lt;b&gt;&amp;nbsp; &amp;nbsp; &amp;nbsp; &amp;nbsp; &amp;nbsp; &amp;nbsp; &amp;nbsp; &amp;nbsp; &amp;nbsp; &amp;nbsp; &amp;nbsp; &amp;nbsp; &amp;nbsp; &amp;nbsp; &amp;nbsp; &amp;nbsp; &amp;nbsp; 平台用户邀请好友享豪礼&lt;/b&gt;&lt;/p&gt;&lt;p&gt;测试测试&lt;/p&gt;";

    @Override
    protected int getLayoutId() {
        fText=getIntent().getStringExtra("data");
        return R.layout.activity_system_msg;
    }

    @Override
    protected void initComponents() {
        initView();
        ivBack.setOnClickListener(view -> finish());

        webContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        webContent.setText(fText);

//        WebSettings webSettings = webContent.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setSupportZoom(true);
//        webContent.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                imgReset(view);
//            }
//        });
//        webContent.loadDataWithBaseURL(null, fText, "text/html", "UTF-8", null);
    }

    private void imgReset(WebView webView) {
        webView.loadUrl("javascript:(function(){" + "var objs = document.getElementsByTagName('img'); " + "for(var i=0;i<objs.length;i++)  " + "{" + "var img = objs[i];   " + "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " + "}" + "})()");
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        webContent = findViewById(R.id.web_content);
    }
}
