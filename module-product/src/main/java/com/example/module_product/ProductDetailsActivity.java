package com.example.module_product;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.xuezj.cardbanner.CardBanner;
import com.xuezj.cardbanner.ImageData;
import com.xuezj.cardbanner.imageloader.CardImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.adapter.OnlyImgRvAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.util.DisplayHelper;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.widget.swipetoloadlayout.util.DensityUtil;
import cn.dankal.basiclib.widget.wheelview.MyNestedScrollView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static cn.dankal.basiclib.protocol.ProductProtocol.PRODUCTDETA;

@Route(path = PRODUCTDETA)
public class ProductDetailsActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.ImageView collImg;
    private android.widget.Button serviceBtn;
    private android.widget.Button purchaseBtn;
    private com.xuezj.cardbanner.CardBanner banner;
    private android.widget.TextView productPrice;
    private android.widget.TextView productName;
    private android.widget.TextView productContent;
    private android.support.v7.widget.RecyclerView contentImgRv;
    private List<String> stringList = new ArrayList<>();
    private android.support.v4.widget.NestedScrollView contentScroll;
    private android.widget.RelativeLayout titleRl;
    private fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard productVideo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_details;
    }

    @Override
    protected void initComponents() {
        initView();
        initBanner();
        initRv();
        initSc();
        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(HomeProtocol.ENTERINTEN).navigation();
            }
        });
        productVideo.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4",
                JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "产品视频");
        Glide.with(this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541421297535&di=7ada3a21db141cb769f8bce9d407686b&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Farchive%2Fdbfb6a5c4276ef323c6b140f40b688363f7c52dd.jpg")
                .into(productVideo.thumbImageView);
        serviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(HomeProtocol.SERVICE).navigation();
            }
        });

    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        collImg = (ImageView) findViewById(R.id.coll_img);
        serviceBtn = (Button) findViewById(R.id.service_btn);
        purchaseBtn = (Button) findViewById(R.id.purchase_btn);
        banner = (CardBanner) findViewById(R.id.banner);
        productPrice = (TextView) findViewById(R.id.product_price);
        productName = (TextView) findViewById(R.id.product_name);
        productContent = (TextView) findViewById(R.id.product_content);
        contentImgRv = (RecyclerView) findViewById(R.id.content_img_rv);
        contentScroll = findViewById(R.id.content_scroll);
        titleRl = (RelativeLayout) findViewById(R.id.title_rl);
        productVideo = (JCVideoPlayerStandard) findViewById(R.id.product_video);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initSc(){
        contentScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Logger.d("scroll","x="+scrollX+"    "+"y="+scrollY+"   ox="+oldScrollX+"    oy="+oldScrollY);
            }
        });
    }

    private void initRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        contentImgRv.setLayoutManager(linearLayoutManager);
        for (int i = 0; i < 3; i++) {
            stringList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541421297535&di=7ada3a21db141cb769f8bce9d407686b&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Farchive%2Fdbfb6a5c4276ef323c6b140f40b688363f7c52dd.jpg");
        }
        OnlyImgRvAdapter onlyImgRvAdapter = new OnlyImgRvAdapter();
        onlyImgRvAdapter.addMore(stringList);
        contentImgRv.setAdapter(onlyImgRvAdapter);
        contentImgRv.setNestedScrollingEnabled(false);
        contentImgRv.setHasFixedSize(true);
    }

    private void initBanner() {
        List<ImageData> imgurl = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ImageData img1 = new ImageData();
            img1.setImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541421297535&di=7ada3a21db141cb769f8bce9d407686b&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Farchive%2Fdbfb6a5c4276ef323c6b140f40b688363f7c52dd.jpg");
            img1.setSubtitleTitle(i + 1 + "/4");
            imgurl.add(img1);
        }
        banner.setDatas(imgurl).setPlay(true).setCardImageLoader(new CardImageLoader() {
            @Override
            public void load(Context context, ImageView imageView, Object path) {
                Glide.with(context).load(path).into(imageView);
            }
        }).start();
    }
}
