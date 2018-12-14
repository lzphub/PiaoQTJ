package com.example.module_product;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.module_product.presenter.ProductDataContact;
import com.example.module_product.presenter.ProductDataPresenter;
import com.xuezj.cardbanner.CardBanner;
import com.xuezj.cardbanner.ImageData;
import com.xuezj.cardbanner.imageloader.CardImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.adapter.OnlyImgRvAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.ProductDataBean;
import cn.dankal.basiclib.exception.LocalException;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.util.DisplayHelper;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.util.image.PicUtils;
import cn.dankal.basiclib.widget.swipetoloadlayout.util.DensityUtil;
import cn.dankal.basiclib.widget.wheelview.MyNestedScrollView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static cn.dankal.basiclib.protocol.ProductProtocol.PRODUCTDETA;

@Route(path = PRODUCTDETA)
public class ProductDetailsActivity extends BaseActivity implements ProductDataContact.pdView {

    private android.widget.ImageView backImg;
    private CheckBox collImg;
    private android.widget.Button serviceBtn;
    private android.widget.Button purchaseBtn;
    private com.xuezj.cardbanner.CardBanner banner;
    private android.widget.TextView productPrice;
    private android.widget.TextView productName;
    private android.widget.TextView productContent;
    private List<String> stringList = new ArrayList<>();
    private android.support.v4.widget.NestedScrollView contentScroll;
    private RelativeLayout titleRl;
    private fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard productVideo;
    private TextView title;
    private String uuid;
    private ProductDataPresenter productDataPresenter = ProductDataPresenter.getPSPresenter();
    private WebView tvDetail;
    private boolean allow = true;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_details;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initComponents() {
        initView();
        initSc();
        uuid = getIntent().getStringExtra("uuid");
        purchaseBtn.setOnClickListener(v -> {
            if(allow){
                ARouter.getInstance().build(HomeProtocol.ENTERINTEN).withString("uuid", uuid).navigation();
            }else{
                ToastUtils.showShort("The product is off the shelf");
            }
        });
        productDataPresenter.attachView(this);
        productDataPresenter.getData(uuid);
        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541421297535&di=7ada3a21db141cb769f8bce9d407686b&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Farchive%2Fdbfb6a5c4276ef323c6b140f40b688363f7c52dd.jpg").into(productVideo.thumbImageView);
        serviceBtn.setOnClickListener(v -> ARouter.getInstance().build(HomeProtocol.SERVICE).navigation());
        backImg.setOnClickListener(v -> finish());
        collImg.setOnClickListener(v -> {
            if (collImg.isChecked()) {
                collImg.setBackgroundResource(R.mipmap.ic_home_details_like_click);
                productDataPresenter.addCollection(uuid);
            } else {
                collImg.setBackgroundResource(R.mipmap.ic_home_details_like_unclicked);
                productDataPresenter.deleteCollection(uuid);
            }
        });
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        collImg = findViewById(R.id.coll_img);
        serviceBtn = findViewById(R.id.service_btn);
        purchaseBtn = findViewById(R.id.purchase_btn);
        banner = findViewById(R.id.banner);
        productPrice = findViewById(R.id.product_price);
        productName = findViewById(R.id.product_name);
        productContent = findViewById(R.id.product_content);
        contentScroll = findViewById(R.id.content_scroll);
        titleRl = findViewById(R.id.title_rl);
        productVideo = findViewById(R.id.product_video);
        title = findViewById(R.id.title);
        tvDetail = findViewById(R.id.tv_detail);
        WebSettings webSettings = tvDetail.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        tvDetail.setWebViewClient(new WebViewClient() {
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

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayerStandard.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initSc() {
        contentScroll.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

            if (scrollY > oldScrollY && scrollY > 100 ) {
                titleRl.setElevation((float)25);
                title.setTextColor(Color.parseColor("#333333"));
            } else if (scrollY < oldScrollY && scrollY < 100 ) {
                titleRl.setElevation((float)0);
                title.setTextColor(Color.parseColor("#00333333"));
            }
            if (scrollY > oldScrollY && scrollY > 200 && scrollY < 300) {
                collImg.setVisibility(View.GONE);
            } else if (scrollY < oldScrollY && scrollY > 200 && scrollY < 300) {
                collImg.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        banner.stopAutoPlay();
    }

    @Override
    public void getDataSuccess(ProductDataBean productDataBean) {
        List<ImageData> imgurl = new ArrayList<>();
        for (int i = 0; i < productDataBean.getImages().size(); i++) {
            ImageData img1 = new ImageData();
            img1.setImage(PicUtils.getUrl(productDataBean.getImages().get(i)));
            img1.setSubtitleTitle(i + 1 + "/" + productDataBean.getImages().size());
            imgurl.add(img1);
        }
        if(productDataBean.getVedio()!=null){
            productVideo.setUp(PicUtils.getUrl(productDataBean.getVedio()), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, productDataBean.getVedioname());
        }else{
            productVideo.setVisibility(View.GONE);
        }
        banner.setDatas(imgurl).setPlay(true).setCardImageLoader((context, imageView, path) -> Glide.with(context).load(path).into(imageView)).start();
        productName.setText(productDataBean.getName());

        productPrice.setText("$"+ StringUtil.isDigits(productDataBean.getPrice()));

        productContent.setText(productDataBean.getDescription());
        tvDetail.loadDataWithBaseURL(null, productDataBean.getDetail(), "text/html", "UTF-8", null);
        if (productDataBean.getIs_favourite() == 1) {
            collImg.setBackgroundResource(R.mipmap.ic_home_details_like_click);
            collImg.setChecked(true);
        } else {
            collImg.setBackgroundResource(R.mipmap.ic_home_details_like_unclicked);
            collImg.setChecked(false);
        }
    }

    @Override
    public void getDataFail(LocalException exception) {
        allow=false;
        purchaseBtn.setBackgroundColor(Color.parseColor("#f6f6f6"));
        dismissLoadingDialog();
        if (exception.getMsg().equals("网络错误")){
            ToastUtils.showShort("NetWork error");
        }
    }

}
