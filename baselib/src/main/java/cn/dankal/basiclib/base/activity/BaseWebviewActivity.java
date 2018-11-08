package cn.dankal.basiclib.base.activity;

import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import cn.dankal.basiclib.R;
import cn.dankal.basiclib.R2;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.util.WebViewUtil;

public abstract class BaseWebviewActivity extends BaseActivity {
    @BindView(R2.id.wb)
    public WebView wb;
    public WebChromeClient webChromeClient;

    @Override
    public void initComponents() {
        WebViewUtil.initWebViewSettings(wb, this);
        webChromeClient = new WebChromeClient();
        initData();
    }


    @Override
    protected void onDestroy() {
        destoryWebview(wb);
        super.onDestroy();
    }

    protected void destoryWebview(WebView dkWebView) {
        if (dkWebView != null) {
            ViewGroup parent = (ViewGroup) dkWebView.getParent();
            if (parent != null) {
                parent.removeView(dkWebView);
            }
            dkWebView.removeAllViews();
            dkWebView.destroy();
        }
    }


    @Override
    protected void onPause() {
        ToastUtils.cancel();
        super.onPause();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_webview;
    }


    public void initData() {
        wb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showLoadingDialog();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                dismissLoadingDialog();
                super.onPageFinished(view, url);
            }
        });
        webChromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitleFromWebView(title);
            }

        };
        wb.setWebChromeClient(webChromeClient);
        if (getUrl() != null) {
            wb.loadUrl(getUrl());
        }
        if (getData() != null) {
            WebViewUtil.loadData(wb, getData());

        }
    }

    public String getUrl() {
        return null;
    }

    protected void loadData(String data) {
        if (StringUtil.isValid(data)) {
            WebViewUtil.loadData(wb, data);
        }
    }

    public String getData() {
        return null;
    }

    public void setTitleFromWebView(String title) {
    }

    @Override
    public void onBackPressed() {
        if (wb.canGoBack()) {
            wb.goBack();
            return;
        }
        finish();
    }
}
