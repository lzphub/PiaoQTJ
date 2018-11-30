package cn.dankal.home.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.xuezj.cardbanner.CardBanner;
import com.xuezj.cardbanner.ImageData;
import com.xuezj.cardbanner.imageloader.CardImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.address.R;
import cn.dankal.basiclib.adapter.DemandRvAdapter;
import cn.dankal.basiclib.adapter.ProductRvAdapter;
import cn.dankal.basiclib.base.fragment.BaseFragment;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.DemandListbean;
import cn.dankal.basiclib.bean.ProductHomeListBean;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.bean.UserHomeBannerBean;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.ProductProtocol;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.util.image.PicUtils;
import cn.dankal.basiclib.widget.GenDialog;
import cn.dankal.basiclib.widget.swipetoloadlayout.OnLoadMoreListener;
import cn.dankal.basiclib.widget.swipetoloadlayout.OnRefreshListener;
import cn.dankal.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout;
import cn.dankal.home.persenter.ProductHomeContact;
import cn.dankal.home.persenter.ProductHomePresenter;

public class Home_fragment extends BaseFragment implements ProductHomeContact.phview {
    private android.widget.ImageView logoImg;
    private android.widget.ImageView searchImg;
    private android.widget.LinearLayout releaseLl;
    private android.widget.TextView newDemand;
    private android.support.v7.widget.RecyclerView demandList;
    private android.widget.TextView loadMore;
    private com.xuezj.cardbanner.CardBanner banner;
    private List<DemandListbean> demandListbeanList = new ArrayList<>();
    private List<ProductListBean> productListBeanList = new ArrayList<>();
    private TextView resText;
    private String identity;
    private android.support.v4.widget.NestedScrollView scroll;
    private int page = 1;
    private boolean isRefresh = true;
    private ProductHomePresenter productHomePresenter;
    private cn.dankal.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout swipeToloadLayout;
    private ProductRvAdapter productRvAdapter;
    private DemandRvAdapter demandRvAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initComponents() {

    }

    @Override
    protected void initComponents(View view) {
        initView(view);
        identity = SharedPreferencesUtils.getString(getContext(), "identity", "enterprise");
        initRv();
        SpannableString spannableString = null;
        if (identity.equals("enterprise")) {
            spannableString = new SpannableString("最新需求");
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.home_green));
            spannableString.setSpan(colorSpan, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            swipeToloadLayout.setRefreshEnabled(false);
            swipeToloadLayout.setLoadMoreEnabled(false);
        } else {
            spannableString = new SpannableString("LATEST PRODUCT");
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.home_green));
            spannableString.setSpan(colorSpan, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            resText.setText("PUBLISH A REQUEST");
            ViewGroup.LayoutParams layoutParams = releaseLl.getLayoutParams();
            layoutParams.width = 485;
            releaseLl.setLayoutParams(layoutParams);
            loadMore.setVisibility(View.GONE);
            logoImg.setImageResource(R.mipmap.pic_logo_english);
        }
        newDemand.setText(spannableString);

        loadMore.setOnClickListener(v -> ARouter.getInstance().build(HomeProtocol.HOMEDEMANDLIST).navigation());
        searchImg.setOnClickListener(v -> ARouter.getInstance().build(HomeProtocol.HOMESEARCH).navigation());
        releaseLl.setOnClickListener(v -> {
            if (identity.equals("enterprise")) {
                ARouter.getInstance().build(HomeProtocol.HOMERELEASE).navigation();
            } else {
                ARouter.getInstance().build(HomeProtocol.POSTREQUEST).navigation();
            }
        });

    }


    private void initView(View view) {
        logoImg = (ImageView) view.findViewById(R.id.logo_img);
        searchImg = (ImageView) view.findViewById(R.id.search_img);
        releaseLl = (LinearLayout) view.findViewById(R.id.release_ll);
        newDemand = (TextView) view.findViewById(R.id.new_demand);
        demandList = (RecyclerView) view.findViewById(R.id.demand_list);
        loadMore = (TextView) view.findViewById(R.id.load_more);
        banner = (CardBanner) view.findViewById(R.id.banner);
        resText = (TextView) view.findViewById(R.id.res_text);
        swipeToloadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipe_toload_layout);
    }

    /*
     * 初始化RecyclerView
     * */
    private void initRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        demandList.setLayoutManager(linearLayoutManager);
        demandList.setNestedScrollingEnabled(false);
        demandList.setHasFixedSize(true);
        productHomePresenter = ProductHomePresenter.getPresenter();
        productHomePresenter.attachView(this);
        if (identity.equals("enterprise")) {
            productHomePresenter.getEngData(1,3);
            productHomePresenter.getEngBanner();
        } else {
            productRvAdapter = new ProductRvAdapter();
            productHomePresenter.getData(page, 10);
            productHomePresenter.getBanner();

            swipeToloadLayout.setOnRefreshListener(() -> {
                page = 1;
                isRefresh = true;
                productHomePresenter.getData(page, 10);
            });
            swipeToloadLayout.setOnLoadMoreListener(() -> {
                page++;
                isRefresh = false;
                productHomePresenter.getData(page, 10);
            });
        }
    }

    @Override
    public void getDataSuccess(ProductHomeListBean productListBean) {

        if (isRefresh) {
            productRvAdapter.clearData();
            swipeToloadLayout.setRefreshing(false);
        } else {
            swipeToloadLayout.setLoadingMore(false);
        }
        productRvAdapter.addMore(productListBean.getData());
        demandList.setAdapter(productRvAdapter);
        demandList.setNestedScrollingEnabled(false);
        demandList.setHasFixedSize(true);
        productRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<ProductHomeListBean.DataBean>() {
            @Override
            public void onItemClick(View v, int position, ProductHomeListBean.DataBean data) {
                ARouter.getInstance().build(ProductProtocol.PRODUCTDETA).withString("uuid", data.getUuid()).navigation();
            }
        });
    }

    @Override
    public void getBannerSuccess(UserHomeBannerBean userHomeBannerBean) {
        List<ImageData> imgurl = new ArrayList<>();
        for (int i = 0; i < userHomeBannerBean.getCarousels().size(); i++) {
            ImageData img1 = new ImageData();
            img1.setImage(PicUtils.getUrl(userHomeBannerBean.getCarousels().get(i).getImage()));
            imgurl.add(img1);
        }
        banner.setDatas(imgurl).setCardImageLoader((context, imageView, path) -> Glide.with(context).load(path).into(imageView)).start();
        banner.setOnItemClickListener(position -> {
            Uri uri = Uri.parse("http://" + userHomeBannerBean.getCarousels().get(position).getJump_url());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }

    @Override
    public void getEngDataSuccess(DemandListbean demandListbean) {
        demandRvAdapter = new DemandRvAdapter();
        demandRvAdapter.addMore(demandListbean.getData());
        demandList.setAdapter(demandRvAdapter);
        demandList.setNestedScrollingEnabled(false);
        demandList.setHasFixedSize(true);
        demandRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<DemandListbean.DataBean>() {
            @Override
            public void onItemClick(View v, int position, DemandListbean.DataBean data) {
                ARouter.getInstance().build(HomeProtocol.DEMANDDETA).withSerializable("demandData",data).navigation();
            }
        });
    }

}
