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
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.util.DisplayHelper;
import cn.dankal.basiclib.util.Logger;
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
    private ProductDataPresenter productDataPresenter=ProductDataPresenter.getPSPresenter();
    private TextView tvDetail;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    CharSequence charSequence = (CharSequence) msg.obj;
                    if (charSequence != null) {
                        tvDetail.setText(charSequence);
                        tvDetail.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_details;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initComponents() {
        initView();
        initSc();
        uuid=getIntent().getStringExtra("uuid");
        productDataPresenter.attachView(this);
        productDataPresenter.getData(uuid);
        purchaseBtn.setOnClickListener(v -> ARouter.getInstance().build(HomeProtocol.ENTERINTEN).withString("uuid",uuid).navigation());
        productVideo.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4",
                JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "产品视频");
        Glide.with(this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541421297535&di=7ada3a21db141cb769f8bce9d407686b&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Farchive%2Fdbfb6a5c4276ef323c6b140f40b688363f7c52dd.jpg")
                .into(productVideo.thumbImageView);
        serviceBtn.setOnClickListener(v -> ARouter.getInstance().build(HomeProtocol.SERVICE).navigation());
        backImg.setOnClickListener(v -> finish());
        collImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(collImg.isChecked()){
                    collImg.setBackgroundResource(R.mipmap.ic_home_details_like_click);
                    productDataPresenter.addCollection(uuid);
                }else{
                    collImg.setBackgroundResource(R.mipmap.ic_home_details_like_unclicked);
                    productDataPresenter.deleteCollection(uuid);
                }
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
        contentScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Logger.d("scroll", "x=" + scrollX + "    " + "y=" + scrollY + "   ox=" + oldScrollX + "    oy=" + oldScrollY);
                setTitleTextAlpha(scrollY, oldScrollY);
                if (scrollY > oldScrollY && scrollY > 200 && scrollY < 300) {
                    collImg.setVisibility(View.GONE);
                } else if (scrollY < oldScrollY && scrollY > 200 && scrollY < 300) {
                    collImg.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setTitleTextAlpha(int t, int oldt) {
        if (oldt < 500) {
            if (155 > t && t > 150 && t > oldt) {
                title.setTextColor(Color.parseColor("#11333333"));
            }
            if (160 > t && t > 155 && t > oldt) {
                title.setTextColor(Color.parseColor("#22333333"));
            }
            if (165 > t && t > 170 && t > oldt) {
                title.setTextColor(Color.parseColor("#33333333"));
            }
            if (180 > t && t > 175 && t > oldt) {
                title.setTextColor(Color.parseColor("#44333333"));
            }
            if (185 > t && t > 190 && t > oldt) {
                title.setTextColor(Color.parseColor("#55333333"));
            }
            if (200 > t && t > 195 && t > oldt) {
                title.setTextColor(Color.parseColor("#66333333"));
            }
            if (205 > t && t > 210 && t > oldt) {
                title.setTextColor(Color.parseColor("#77333333"));
            }
            if (220 > t && t > 215 && t > oldt) {
                title.setTextColor(Color.parseColor("#88333333"));
            }
            if (225 > t && t > 230 && t > oldt) {
                title.setTextColor(Color.parseColor("#99333333"));
            }
            if (240 > t && t > 235 && t > oldt) {
                title.setTextColor(Color.parseColor("#aa333333"));
            }
            if (245 > t && t > 250 && t > oldt) {
                title.setTextColor(Color.parseColor("#bb333333"));
            }
            if (260 > t && t > 255 && t > oldt) {
                title.setTextColor(Color.parseColor("#cc333333"));
            }
            if (265 > t && t > 270 && t > oldt) {
                title.setTextColor(Color.parseColor("#dd333333"));
            }
            if (280 > t && t > 275 && t > oldt) {
                title.setTextColor(Color.parseColor("#ee333333"));
            }
            if (t > 285 && t > oldt) {
                title.setTextColor(Color.parseColor("#333333"));
            }
            if (280 > t && t < 285 && t < oldt) {
                title.setTextColor(Color.parseColor("#ee333333"));
            }
            if (270 > t && t < 275 && t < oldt) {
                title.setTextColor(Color.parseColor("#dd333333"));
            }
            if (260 > t && t < 265 && t < oldt) {
                title.setTextColor(Color.parseColor("#cc333333"));
            }
            if (250 > t && t < 255 && t < oldt) {
                title.setTextColor(Color.parseColor("#bb333333"));
            }
            if (240 > t && t < 245 && t < oldt) {
                title.setTextColor(Color.parseColor("#aa333333"));
            }
            if (230 > t && t < 235 && t < oldt) {
                title.setTextColor(Color.parseColor("#99333333"));
            }
            if (220 > t && t < 225 && t < oldt) {
                title.setTextColor(Color.parseColor("#88333333"));
            }
            if (210 > t && t < 215 && t < oldt) {
                title.setTextColor(Color.parseColor("#77333333"));
            }
            if (200 > t && t < 205 && t < oldt) {
                title.setTextColor(Color.parseColor("#66333333"));
            }
            if (190 > t && t < 195 && t < oldt) {
                title.setTextColor(Color.parseColor("#55333333"));
            }
            if (180 > t && t < 185 && t < oldt) {
                title.setTextColor(Color.parseColor("#44333333"));
            }
            if (170 > t && t < 175 && t < oldt) {
                title.setTextColor(Color.parseColor("#33333333"));
            }
            if (160 > t && t < 165 && t < oldt) {
                title.setTextColor(Color.parseColor("#22333333"));
            }
            if (150 > t && t < 155 && t < oldt) {
                title.setTextColor(Color.parseColor("#11333333"));
            }
            if (140 > t && t < 145 && t < oldt) {
                title.setTextColor(Color.parseColor("#00333333"));
            }
        }
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
            img1.setSubtitleTitle(i + 1 + "/"+productDataBean.getImages().size());
            imgurl.add(img1);
        }
        banner.setDatas(imgurl).setPlay(true).setCardImageLoader((context, imageView, path) -> Glide.with(context).load(path).into(imageView)).start();
        productName.setText(productDataBean.getName());
        productPrice.setText("$"+productDataBean.getPrice());
        productContent.setText(productDataBean.getDescription());
        setActivityContent(productDataBean.getDetail());
        if(productDataBean.getIs_favourite()==1){
            collImg.setBackgroundResource(R.mipmap.ic_home_details_like_click);
            collImg.setChecked(true);
        }else {
            collImg.setBackgroundResource(R.mipmap.ic_home_details_like_unclicked);
            collImg.setChecked(false);
        }
    }

    //加载富文本
    private void setActivityContent(final String activityContent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Html.ImageGetter imageGetter = new Html.ImageGetter() {

                    @Override
                    public Drawable getDrawable(String source) {
                        Drawable drawable;
                        drawable = PicUtils.getImageNetwork(source);
                        if (drawable != null) {
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        } else if (drawable == null) {
                            return null;
                        }
                        return drawable;
                    }
                };
                CharSequence charSequence = Html.fromHtml(activityContent.trim(), imageGetter, null);
                Message ms = Message.obtain();
                ms.what = 1;
                ms.obj = charSequence;
                mHandler.sendMessage(ms);
            }
        }).start();
    }
}
