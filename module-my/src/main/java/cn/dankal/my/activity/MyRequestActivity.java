package cn.dankal.my.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.adapter.MyRequestRvAdapter;
import cn.dankal.basiclib.base.activity.BaseStateActivity;
import cn.dankal.basiclib.base.recyclerview.del.DeleteRecyclerView;
import cn.dankal.basiclib.base.recyclerview.del.OnDelItemClickListener;
import cn.dankal.basiclib.bean.MyRequestBean;
import cn.dankal.basiclib.bean.RequestDataBean;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.widget.swipetoloadlayout.OnLoadMoreListener;
import cn.dankal.basiclib.widget.swipetoloadlayout.OnRefreshListener;
import cn.dankal.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout;
import cn.dankal.my.entity.TabEntity;
import cn.dankal.my.fragment.MyRequestFragment;
import cn.dankal.my.presenter.MyRequestPresenter;
import cn.dankal.my.presenter.RequestContact;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYREQUEST;

@Route(path = MYREQUEST)
public class MyRequestActivity extends BaseStateActivity implements RequestContact.RequestView {

    private ImageView backImg;
    private MyRequestRvAdapter myRequestRvAdapter;
    private MyRequestPresenter myRequestPresenter = MyRequestPresenter.getPSPresenter();
    private DeleteRecyclerView swipeTarget;
    private List<MyRequestBean.databean> myRequestBeans = new ArrayList<>();
    private cn.dankal.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout swipeToloadLayout;
    private int pageIndex = 1;
    private boolean isUpdateList = false;
    private boolean isRefresh = true;
    private String[] tab_titel2={"FINISH","SUBMITTED","IN PROGRESS","RECEIVED","UNDELIVERED"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private com.flyco.tablayout.SlidingTabLayout tabTitle;
    private android.support.v4.view.ViewPager tabViewpager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_request;
    }

    @Override
    public void initComponents() {
        initView();
        myRequestPresenter.attachView(this);
        backImg.setOnClickListener(v -> finish());
        for(int i=0;i<tab_titel2.length;i++){
            mFragments.add(new MyRequestFragment());
        }
        tabViewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),mFragments,tab_titel2));
        tabTitle.setViewPager(tabViewpager);
        tabTitle.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        swipeTarget.setAdapter(myRequestRvAdapter);
        swipeTarget.setDelOnItemClickListener(new OnDelItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ARouter.getInstance().build(MyProtocol.MYREQUESTDETA).withString("demand_id",myRequestRvAdapter.getDatas().get(position).getDemand_id()).navigation();
            }

            @Override
            public void onDeleteClick(int position) {
                myRequestPresenter.delete(myRequestRvAdapter.getDatas().get(position).getDemand_id());
            }
        });
        swipeToloadLayout.setOnLoadMoreListener(() -> {
            pageIndex++;
            isRefresh = false;
            myRequestPresenter.getData(pageIndex, 10);
        });
        swipeToloadLayout.setOnRefreshListener(() -> {
            pageIndex = 1;
            isRefresh = true;
            myRequestPresenter.getData(pageIndex, 10);
        });

        myRequestPresenter.getData(pageIndex, 10);

    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);

        tabTitle = (SlidingTabLayout) findViewById(R.id.tab_title);
        tabViewpager = (ViewPager) findViewById(R.id.tab_viewpager);
        swipeToloadLayout = findViewById(R.id.swipe_toload_layout);
        swipeTarget = findViewById(R.id.swipe_target);

        myRequestRvAdapter = new MyRequestRvAdapter();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void obtainData() {
        super.obtainData();
    }



    @Override
    public Object contentView() {
        return swipeToloadLayout;
    }

    @Override
    public void getDataSuccess(MyRequestBean myRequestBean) {
        if (myRequestBeans != null) {
            myRequestBeans.clear();
        }
        if (isRefresh) {
            myRequestRvAdapter.clearData();
            swipeToloadLayout.setRefreshing(false);
        } else {
            swipeToloadLayout.setLoadingMore(false);
        }
        myRequestBeans.addAll(myRequestBean.getData());
        myRequestRvAdapter.addMore(myRequestBeans);
    }

    @Override
    public void updata(MyRequestBean myRequestBean) {
        if (myRequestBeans != null) {
            myRequestBeans.clear();
        }
        myRequestBeans.addAll(myRequestBean.getData());
        myRequestRvAdapter.updateData(myRequestBeans);
    }

    @Override
    public void getRequestDataSuccess(RequestDataBean databean) {

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragments;
        private String[] mTitles;

        public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> mFragments, String[] mTitles) {
            super(fm);
            this.mFragments=mFragments;
            this.mTitles=mTitles;
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

    }
}
