package cn.dankal.home.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dankal.address.R;
import cn.dankal.address.R2;
import cn.dankal.basiclib.adapter.DemandRvAdapter;
import cn.dankal.basiclib.base.BaseRvActivity;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.DemandListbean;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.home.persenter.DemandListPersenter;

import static cn.dankal.basiclib.protocol.HomeProtocol.HOMEDEMANDLIST;

@Route(path = HOMEDEMANDLIST)
public class DemandListActivity extends BaseRvActivity<DemandListbean> {

    private DemandRvAdapter demandRvAdapter;
    private ImageView backImg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_demand_list;
    }

    @Override
    public void initComponents() {
        super.initComponents();
        initView();
        backImg.setOnClickListener(v -> finish());
    }

    @Override
    public BaseRecyclerViewPresenter getPresenter() {
        return new DemandListPersenter();
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        demandRvAdapter = new DemandRvAdapter();
        demandRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<DemandListbean.DataBean>() {
            @Override
            public void onItemClick(View v, int position, DemandListbean.DataBean data) {
                ARouter.getInstance().build(HomeProtocol.DEMANDDETA).withString("project_uuid", data.getUuid()).withString("time",data.getCreate_time()).navigation();
            }
        });
        return demandRvAdapter;
    }


    private void initView() {
        backImg = findViewById(R.id.back_img);
    }
}
