package cn.xunzi.home.fragment;

import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
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

import cn.xunzi.address.R;
import cn.xunzi.basiclib.api.BaseApi;
import cn.xunzi.basiclib.banner.CustomBanner;
import cn.xunzi.basiclib.banner.FixedSpeedScroller;
import cn.xunzi.basiclib.banner.ViewPagerAdapter;
import cn.xunzi.basiclib.base.fragment.BaseFragment;
import cn.xunzi.basiclib.bean.HomeBannerBean;
import cn.xunzi.basiclib.protocol.TaskProtocol;
import cn.xunzi.home.persenter.HomeContact;
import cn.xunzi.home.persenter.HomePresenter;

public class RenwuFragment extends BaseFragment implements HomeContact.pdView {
    private ViewPager banner;
    private ImageView ivRenwudat;
    private ImageView ivTijiaorenwu;
    private ImageView ivRenwujilu;

    private List<ImageView> imageViews;
    private ViewPagerAdapter mAdapter;

    private List<Integer> bannerList=new ArrayList<>();
    private FixedIndicatorView singleTabScrollIndicatorView;
    private int count = 0;

    private HomePresenter homePresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_renwu;
    }

    @Override
    protected void initComponents() {

    }

    @Override
    protected void initComponents(View view) {
        initView(view);
        homePresenter=new HomePresenter();
        homePresenter.attachView(this);
        homePresenter.getBanner("2");
        ivRenwudat.setOnClickListener(view1 -> ARouter.getInstance().build(TaskProtocol.TASKDATING).navigation());
        ivTijiaorenwu.setOnClickListener(view12 -> ARouter.getInstance().build(TaskProtocol.SUBMITTASK).navigation());
        ivRenwujilu.setOnClickListener(view13 -> ARouter.getInstance().build(TaskProtocol.TASKRECORD).navigation());

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
            Glide.with(getContext()).load(BaseApi.IMAGE_URL+homeBannerBean.getData().getList().get(i % homeBannerBean.getData().getList().size()).getImg()).into(imageView);
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
        banner.setAdapter(mAdapter);

        count = 10;
        banner.setCurrentItem(count, false);

    }

    @Override
    public void getBannerFail(String msg) {

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
        banner = view.findViewById(R.id.banner);
        ivRenwudat = view.findViewById(R.id.iv_renwudat);
        ivTijiaorenwu = view.findViewById(R.id.iv_tijiaorenwu);
        ivRenwujilu = view.findViewById(R.id.iv_renwujilu);
        singleTabScrollIndicatorView = view.findViewById(R.id.singleTab_scrollIndicatorView);
    }
}
