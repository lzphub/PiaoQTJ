package cn.xunzi.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.Indicator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import api.HomeServiceFactory;
import cn.xunzi.address.R;
import cn.xunzi.basiclib.adapter.Home_Rec_Adapter;
import cn.xunzi.basiclib.api.BaseApi;
import cn.xunzi.basiclib.banner.CardTransformer;
import cn.xunzi.basiclib.banner.CustomBanner;
import cn.xunzi.basiclib.banner.FixedSpeedScroller;
import cn.xunzi.basiclib.banner.ViewPagerAdapter;
import cn.xunzi.basiclib.base.fragment.BaseFragment;
import cn.xunzi.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.xunzi.basiclib.bean.HomeBannerBean;
import cn.xunzi.basiclib.bean.HomeRecBean;
import cn.xunzi.basiclib.bean.SystemMsgBean;
import cn.xunzi.basiclib.protocol.HomeProtocol;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.ToastUtils;
import cn.xunzi.home.activity.MessageActivity;
import cn.xunzi.home.persenter.HomeContact;
import cn.xunzi.home.persenter.HomePresenter;

public class HomeFragment extends BaseFragment implements HomeContact.pdView {
    private ViewPager banner;
    private RecyclerView homeRec;

    private int count = 0;

    private String[] text = {"如何赚佣", "推广码", "成为商家", "申请会员", "淘宝天猫", "消息", "赠品领取", "客服中心", "港货代购"};
    private int[] image = {R.mipmap.zhuangyong, R.mipmap.tuiguangma, R.mipmap.shangjia, R.mipmap.huiyuan, R.mipmap.taobao, R.mipmap.xiaoxi, R.mipmap.duobao, R.mipmap.kefu, R.mipmap.daigou};
    private List<Integer> bannerList = new ArrayList<>();
    private FixedIndicatorView singleTabScrollIndicatorView;
    private List<ImageView> imageViews;
    private ViewPagerAdapter mAdapter;

    private HomePresenter homePresenter;
    private List<HomeRecBean> homeRecBeans=new ArrayList<>();
    private int num = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initComponents() {

    }

    @Override
    protected void initComponents(View view) {
        initView(view);

        homePresenter = new HomePresenter();
        homePresenter.attachView(this);
        homePresenter.getBanner("1");

        Home_Rec_Adapter rec_adapter = new Home_Rec_Adapter();
        for (int i = 0; i < text.length; i++) {
            HomeRecBean homeRecBean = new HomeRecBean();
            homeRecBean.setImage(image[i]);
            homeRecBean.setText(text[i]);
//            rec_adapter.addData(homeRecBean);
            homeRecBeans.add(homeRecBean);
        }
        homeRec.setLayoutManager(new GridLayoutManager(getContext(), 3));
        homeRec.setNestedScrollingEnabled(false);
        homeRec.setHasFixedSize(true);
        homeRec.setAdapter(rec_adapter);

        rec_adapter.setOnRvItemClickListener(new OnRvItemClickListener<HomeRecBean>() {
            @Override
            public void onItemClick(View v, int position, HomeRecBean data) {
                switch (position) {
                    case 0:
                        ARouter.getInstance().build(HomeProtocol.ZHUANGYONG).navigation();
                        break;
                    case 1:
                        ARouter.getInstance().build(HomeProtocol.TUIGUANGAM).navigation();
                        break;
                    case 2:
                        ARouter.getInstance().build(HomeProtocol.SERVICE).navigation();
                        break;
                    case 3:
                        ARouter.getInstance().build(HomeProtocol.VIP).navigation();
                        break;
                    case 4:
                        if (checkPackage(getContext(), "com.taobao.taobao")) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            String url = "taobao://taobao.com";
                            Uri uri = Uri.parse(url);
                            intent.setData(uri);
                            startActivity(intent);
                        } else {
                            ToastUtils.showShort("未安装淘宝");
                        }
                        break;
                    case 5:
                        ARouter.getInstance().build(HomeProtocol.MESSAGE).navigation();
                        break;
                    case 6:
                        ARouter.getInstance().build(HomeProtocol.ADDRESSLIST).navigation();
                        break;
                    case 7:
                        ARouter.getInstance().build(HomeProtocol.SERVICE).navigation();
                        break;
                    case 8:
                        ARouter.getInstance().build(HomeProtocol.ONEMONEY).withInt("title", 1).navigation();
                        break;
                }
            }
        });
        HomeServiceFactory.getSysMsg("2").safeSubscribe(new AbstractDialogSubscriber<SystemMsgBean>(this) {
            @Override
            public void onNext(SystemMsgBean systemMsgBean) {
                if(systemMsgBean.getCode().equals("200")){
                    HomeRecBean homeRecBean=homeRecBeans.get(5);
                    if(null!=systemMsgBean.getData()){
                        homeRecBean.setNum(systemMsgBean.getData().getList().size()+"");
                    }else{
                        homeRecBean.setNum("0");
                    }
                    homeRecBeans.set(5,homeRecBean);
                    rec_adapter.updateData(homeRecBeans);
                }else{
                    ToastUtils.showShort(systemMsgBean.getMsg());
                }
            }
        });

    }

    public static boolean checkPackage(Context context, String packageName) {
        if (packageName == null || "".equals(packageName)) return false;
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

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
    public void getBannerSuccess(HomeBannerBean homeBannerBean) {

        singleTabScrollIndicatorView.setAdapter(new MyAdapter(homeBannerBean.getData().getList().size()));

        singleTabScrollIndicatorView.setCurrentItem(0, true);

        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                count = i;

                singleTabScrollIndicatorView.setCurrentItem(i % homeBannerBean.getData().getList().size(), true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        imageViews = new ArrayList<>();
        for (int i = 0; i < 200 * homeBannerBean.getData().getList().size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(getContext()).load(BaseApi.IMAGE_URL + homeBannerBean.getData().getList().get(i % homeBannerBean.getData().getList().size()).getImg()).into(imageView);
            imageViews.add(imageView);
            final int finalI = i;

        }
        mAdapter = new ViewPagerAdapter(getContext(), homeBannerBean.getData().getList(), imageViews);


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
//        banner.setPageTransformer(true, new CardTransformer());
        banner.setAdapter(mAdapter);

        count = 10;
        banner.setCurrentItem(count, false);

    }

    @Override
    public void getBannerFail(String msg) {

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

    private void initView(View view) {
        banner = (ViewPager) view.findViewById(R.id.banner);
        homeRec = (RecyclerView) view.findViewById(R.id.home_rec);
        singleTabScrollIndicatorView = view.findViewById(R.id.singleTab_scrollIndicatorView);
    }
}
