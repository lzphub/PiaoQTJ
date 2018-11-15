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
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.ProductProtocol;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.widget.GenDialog;

public class Home_fragment extends BaseFragment {
    private android.widget.ImageView logoImg;
    private android.widget.ImageView searchImg;
    private android.widget.LinearLayout releaseLl;
    private android.widget.TextView newDemand;
    private android.support.v7.widget.RecyclerView demandList;
    private android.widget.TextView loadMore;
    private com.xuezj.cardbanner.CardBanner banner;
    private List<DemandListbean> demandListbeanList = new ArrayList<>();
    private List<ProductListBean> productListBeanList=new ArrayList<>();
    private TextView resText;
    private String identity;
    private android.support.v4.widget.NestedScrollView scroll;

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
//        initRv();
        SpannableString spannableString = null;
        if (identity.equals("enterprise")) {
            spannableString = new SpannableString("最新需求");
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.home_green));
            spannableString.setSpan(colorSpan, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
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
            }else{
                ARouter.getInstance().build(HomeProtocol.POSTREQUEST).navigation();
            }
        });

        initBanner();

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
    }

    /*
     * 初始化RecyclerView
     * */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        demandList.setLayoutManager(linearLayoutManager);
        demandList.setNestedScrollingEnabled(false);
        demandList.setHasFixedSize(true);
        if(identity.equals("enterprise")){
            for (int i = 0; i < 3; i++) {
                DemandListbean demandListbean = new DemandListbean();
                demandListbeanList.add(demandListbean);
            }
            DemandRvAdapter demandRvAdapter = new DemandRvAdapter();
            demandRvAdapter.addMore(demandListbeanList);
            demandList.setAdapter(demandRvAdapter);
            demandRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<DemandListbean>() {
                @Override
                public void onItemClick(View v, int position, DemandListbean data) {
                    ARouter.getInstance().build(HomeProtocol.DEMANDDETA).navigation();
                }
            });
        }else{
//            for(int i=0;i<10;i++){
//                ProductListBean productListBean=new ProductListBean();
//                productListBeanList.add(productListBean);
//            }
//            ProductRvAdapter productRvAdapter=new ProductRvAdapter();
//            productRvAdapter.addMore(productListBeanList);
//            demandList.setAdapter(productRvAdapter);
//            demandList.setNestedScrollingEnabled(false);
//            demandList.setHasFixedSize(true);
//            productRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<ProductListBean>() {
//                @Override
//                public void onItemClick(View v, int position, ProductListBean data) {
//                    ARouter.getInstance().build(ProductProtocol.PRODUCTDETA).navigation();
//                }
//            });
//            scroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    Logger.d("recv",scroll.canScrollVertically(1)+"");
//                    if(!scroll.canScrollVertically(1)){
//                        productRvAdapter.addMore(productListBeanList);
//                    }
//
//                }
//            });
        }

    }

    /*
     * 初始化轮播图
     * */
    private void initBanner() {
        List<ImageData> imgurl = new ArrayList<>();
        ImageData img1 = new ImageData();
        img1.setImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541421297535&di=7ada3a21db141cb769f8bce9d407686b&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Farchive%2Fdbfb6a5c4276ef323c6b140f40b688363f7c52dd.jpg");
        imgurl.add(img1);
        imgurl.add(img1);
        imgurl.add(img1);
        banner.setDatas(imgurl).setCardImageLoader(new CardImageLoader() {
            @Override
            public void load(Context context, ImageView imageView, Object path) {
                Glide.with(context).load(path).into(imageView);
            }
        }).start();
        banner.setOnItemClickListener(new CardBanner.OnItemClickListener() {
            @Override
            public void onItem(int position) {
                Uri uri = Uri.parse("https://www.baidu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

}
