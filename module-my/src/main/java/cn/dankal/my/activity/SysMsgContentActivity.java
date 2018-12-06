package cn.dankal.my.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dankal.basiclib.base.activity.BaseStateActivity;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.setting.R;
import cn.dankal.setting.R2;

import static cn.dankal.basiclib.protocol.MyProtocol.SYSTEMNEWSCONTENT;

@Route(path = SYSTEMNEWSCONTENT)
public class SysMsgContentActivity extends BaseStateActivity {

    @BindView(R2.id.back_img)
    ImageView backImg;
    @BindView(R2.id.title_text)
    TextView titleText;
    @BindView(R2.id.web_content)
    WebView webContent;
    private String content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sys_msg_content;
    }

    @Override
    protected void initComponents() {
        if("user".equals(SharedPreferencesUtils.getString(this,"identity","user"))){
            titleText.setText("SYSTEM MESSAGES");
        }
        content=getIntent().getStringExtra("content");
        WebSettings webSettings=webContent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webContent.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                imgReset(view);
            }
        });
        webContent.loadDataWithBaseURL(null,content, "text/html", "UTF-8", null);

    }

    @Override
    public Object contentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
