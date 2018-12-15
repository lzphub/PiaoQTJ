package cn.dankal.home.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.EventLog;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.shizhefei.view.indicator.Indicator;
import com.xuezj.cardbanner.CardBanner;
import com.xuezj.cardbanner.ImageData;
import com.xuezj.cardbanner.imageloader.CardImageLoader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.dankal.address.R;
import cn.dankal.basiclib.CardTransformer;
import cn.dankal.basiclib.FixedSpeedScroller;
import cn.dankal.basiclib.adapter.DemandRvAdapter;
import cn.dankal.basiclib.adapter.ProductRvAdapter;
import cn.dankal.basiclib.adapter.ViewPagerAdapter;
import cn.dankal.basiclib.base.fragment.BaseFragment;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.DemandListbean;
import cn.dankal.basiclib.bean.ProductHomeListBean;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.bean.UserHomeBannerBean;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.ProductProtocol;
import cn.dankal.basiclib.util.DisplayHelper;
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
    private TextView resText;
    private String identity;
    private int page = 1;
    private boolean isRefresh = true;
    private ProductHomePresenter productHomePresenter;
    private cn.dankal.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout swipeToloadLayout;
    private ProductRvAdapter productRvAdapter;
    private DemandRvAdapter demandRvAdapter;
    private LinearLayout releaseLl2;
    private TextView resText2;
    private android.support.v4.view.ViewPager banner;
    private com.shizhefei.view.indicator.FixedIndicatorView singleTabScrollIndicatorView;
    private List<ImageView> imageViews;
    private ViewPagerAdapter mAdapter;
    private int count = 0;
    private Context context;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initComponents() {

    }

    @Override
    protected void initComponents(View view) {
        context = getActivity();
        initView(view);
        identity = SharedPreferencesUtils.getString(getContext(), "identity", "enterprise");
        initRv();
        SpannableString spannableString = null;
        if ("enterprise".equals(identity)) {
            spannableString = new SpannableString("最新需求");
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.home_green));
            spannableString.setSpan(colorSpan, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            swipeToloadLayout.setLoadMoreEnabled(false);
            releaseLl.setVisibility(View.VISIBLE);
            releaseLl2.setVisibility(View.GONE);
        } else {
            spannableString = new SpannableString("LATEST PRODUCT");
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.home_green));
            spannableString.setSpan(colorSpan, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            resText.setText("PUBLISH A REQUEST");
            loadMore.setVisibility(View.GONE);
            logoImg.setImageResource(R.mipmap.pic_logo_english);
            releaseLl2.setVisibility(View.VISIBLE);
            releaseLl.setVisibility(View.VISIBLE);
        }
        newDemand.setText(spannableString);

        loadMore.setOnClickListener(v -> ARouter.getInstance().build(HomeProtocol.HOMEDEMANDLIST).navigation());
        searchImg.setOnClickListener(v -> ARouter.getInstance().build(HomeProtocol.HOMESEARCH).navigation());
        releaseLl.setOnClickListener(v -> {
            ARouter.getInstance().build(HomeProtocol.HOMERELEASE).navigation();
        });
        releaseLl2.setOnClickListener(v -> ARouter.getInstance().build(HomeProtocol.POSTREQUEST).navigation());

    }


    private void initView(View view) {
        logoImg = view.findViewById(R.id.logo_img);
        searchImg = view.findViewById(R.id.search_img);
        releaseLl = view.findViewById(R.id.release_ll);
        newDemand = view.findViewById(R.id.new_demand);
        demandList = view.findViewById(R.id.demand_list);
        loadMore = view.findViewById(R.id.load_more);
        resText = view.findViewById(R.id.res_text);
        swipeToloadLayout = view.findViewById(R.id.swipe_toload_layout);
        releaseLl2 = view.findViewById(R.id.release_ll2);
        resText2 = view.findViewById(R.id.res_text2);
        banner = view.findViewById(R.id.banner);
        singleTabScrollIndicatorView = view.findViewById(R.id.singleTab_scrollIndicatorView);
    }

    private class MyAdapter extends Indicator.IndicatorAdapter {

        private final int count;

        public MyAdapter(int count) {
            super();
            this.count = count;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.tab_guide, parent, false);
            }
            return convertView;
        }
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
        if ("enterprise".equals(identity)) {
            productHomePresenter.getEngData(page, 3);
            productHomePresenter.getEngBanner();
            swipeToloadLayout.setOnRefreshListener(() -> {
                page = 1;
                isRefresh = true;
                productHomePresenter.getEngData(page, 3);
            });
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
        if (productListBean.getData() != null && productListBean.getData().size() < 10) {
            swipeToloadLayout.setLoadMoreEnabled(false);
        }
    }

    @Override
    public void getBannerSuccess(UserHomeBannerBean userHomeBannerBean) {

        singleTabScrollIndicatorView.setAdapter(new MyAdapter(userHomeBannerBean.getCarousels().size()));

        singleTabScrollIndicatorView.setCurrentItem(0, true);

        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                count = i;
                singleTabScrollIndicatorView.setCurrentItem(i % userHomeBannerBean.getCarousels().size(), true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        imageViews = new ArrayList<>();
        for (int i = 0; i < 200 * userHomeBannerBean.getCarousels().size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load(PicUtils.getUrl(userHomeBannerBean.getCarousels().get(i % userHomeBannerBean.getCarousels().size()).getImage())).into(imageView);
            imageViews.add(imageView);
            final int finalI = i;
            imageView.setOnClickListener(v -> {
                Uri uri = Uri.parse("http://" + userHomeBannerBean.getCarousels().get(finalI % userHomeBannerBean.getCarousels().size()).getJump_url());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            });
        }
        mAdapter = new ViewPagerAdapter(context, userHomeBannerBean.getCarousels(), imageViews);


        banner.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    try {
                        Field field = ViewPager.class.getDeclaredField("mScroller");
                        field.setAccessible(true);
                        FixedSpeedScroller scroller = new FixedSpeedScroller(banner.getContext(), new AccelerateInterpolator());
                        field.set(banner, scroller);
                        scroller.setmDuration(100);
                    } catch (Exception e) {
                    }
                    break;
            }
            return false;
        });

        banner.setOffscreenPageLimit(4);
        banner.setPageMargin(5);
        banner.setPageTransformer(true, new CardTransformer());
        banner.setAdapter(mAdapter);

        count = 10;
        banner.setCurrentItem(count, false);

//        downTimer2.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        downTimer2.start();
    }


    //轮播图定时
    CountDownTimer downTimer2 = new CountDownTimer(1000000, 3000) {
        @Override
        public void onTick(long millisUntilFinished) {
            try {
                Field field = ViewPager.class.getDeclaredField("mScroller");
                field.setAccessible(true);
                FixedSpeedScroller scroller = new FixedSpeedScroller(banner.getContext(), new AccelerateInterpolator());
                field.set(banner, scroller);
                scroller.setmDuration(1000);
            } catch (Exception e) {

            }
            banner.setCurrentItem(count, true);
            count++;
        }

        @Override
        public void onFinish() {
            downTimer2.start();
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        downTimer2.cancel();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void getEngDataSuccess(DemandListbean demandListbean) {
        demandRvAdapter = new DemandRvAdapter();
        demandRvAdapter.addMore(demandListbean.getData());
        demandList.setAdapter(demandRvAdapter);
        demandList.setNestedScrollingEnabled(false);
        demandList.setHasFixedSize(true);
        swipeToloadLayout.setRefreshing(false);
        demandRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<DemandListbean.DataBean>() {
            @Override
            public void onItemClick(View v, int position, DemandListbean.DataBean data) {
                ARouter.getInstance().build(HomeProtocol.DEMANDDETA).withString("project_uuid", data.getUuid()).withString("time", data.getCreate_time()).navigation();
            }
        });
    }

}
