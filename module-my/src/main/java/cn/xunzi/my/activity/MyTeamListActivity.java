package cn.xunzi.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.List;

import cn.xunzi.basiclib.adapter.MyTeam2Adapter;
import cn.xunzi.basiclib.adapter.MyTeamAdapter;
import cn.xunzi.basiclib.base.BaseRvActivity;
import cn.xunzi.basiclib.base.activity.BaseRecyclerViewActivity;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.xunzi.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.xunzi.basiclib.bean.MyTeamBean;
import cn.xunzi.basiclib.common.OnFinishLoadDataListener;
import cn.xunzi.basiclib.widget.swipetoloadlayout.OnRefreshListener;
import cn.xunzi.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout;
import cn.xunzi.my.presenter.MyTeamConract;
import cn.xunzi.my.presenter.MyTeamConract.mtView;
import cn.xunzi.my.presenter.MyTeamPresenter;
import cn.xunzi.setting.R;

import static cn.xunzi.basiclib.protocol.MyProtocol.MYTEAMLIST;

@Route(path = MYTEAMLIST)
public class MyTeamListActivity extends BaseRvActivity implements MyTeamConract.mtView {

    private ImageView imBack;
    private MyTeamAdapter myTeamAdapter;
    private int type;
    private TextView tvTitle;
    private MyTeamPresenter myTeamPresenter;
    private MyTeam2Adapter myTeam2Adapter;
    private SwipeToLoadLayout swipeToloadLayout;
    private RecyclerView swipeTarget;

    @Override
    protected int getLayoutId() {
        type=getIntent().getIntExtra("type",0);
        return R.layout.activity_my_team_list;
    }

    @Override
    public void initComponents() {
        super.initComponents();
        initView();
        if(type==1){
            tvTitle.setText("一级会员");
        }else{
            tvTitle.setText("二级会员");
        }

        myTeamPresenter=new MyTeamPresenter();
        myTeamPresenter.attachView(this);
        myTeamPresenter.getData(type+"");
        swipeToloadLayout.setLoadMoreEnabled(false);

        swipeToloadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                myTeamPresenter.getData(type+"");
            }
        });

        imBack.setOnClickListener(view -> finish());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    private void initView() {
        imBack = findViewById(R.id.im_back);
        tvTitle = findViewById(R.id.tv_title);
        swipeToloadLayout = findViewById(R.id.swipe_toload_layout);
        swipeTarget = findViewById(R.id.swipe_target);
    }


    @Override
    public void getDataSuccess(List<MyTeamBean.DataEntity.ListOneEntity> listonrEntity) {
        myTeamAdapter=new MyTeamAdapter();
        myTeamAdapter.updateData(listonrEntity);
        recyclerView.setAdapter(myTeamAdapter);
        swipeToloadLayout.setRefreshing(false);
    }

    @Override
    public void getDataSuccess2(List<MyTeamBean.DataEntity.ListTwoEntity> listonrEntity2) {
        myTeam2Adapter=new MyTeam2Adapter();
        myTeam2Adapter.updateData(listonrEntity2);
       recyclerView.setAdapter(myTeam2Adapter);
       swipeToloadLayout.setRefreshing(false);
    }

    @Override
    public BaseRecyclerViewPresenter getPresenter() {
        return null;
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        return null;
    }
}
