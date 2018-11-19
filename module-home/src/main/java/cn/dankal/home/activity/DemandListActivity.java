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

    private List<DemandListbean> demandListbeanList = new ArrayList<>();
    private DemandRvAdapter demandRvAdapter;
    private ImageView backImg;
    private TextView titleText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_demand_list;
    }

//    @Override
//    public void initComponents() {
//        initView();
//        backImg.setOnClickListener(v -> finish());
//        for (int i = 0; i < 10; i++) {
//            DemandListbean demandListbean = new DemandListbean();
//            demandListbeanList.add(demandListbean);
//        }
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        demandList.setLayoutManager(linearLayoutManager);
//        demandRvAdapter.addMore(demandListbeanList);
//        demandList.setAdapter(demandRvAdapter);
//        demandRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<DemandListbean>() {
//            @Override
//            public void onItemClick(View v, int position, DemandListbean data) {
//                ARouter.getInstance().build(HomeProtocol.DEMANDDETA).navigation();
//            }
//        });
//        demandList.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if(!demandList.canScrollVertically(1)){
//                    demandRvAdapter.addMore(demandListbeanList);
//                }
//                Logger.d("rec",demandList.canScrollVertically(1)+"");
//
//            }
//        });
//    }

    @Override
    public BaseRecyclerViewPresenter getPresenter() {
        return new DemandListPersenter();
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        demandRvAdapter = new DemandRvAdapter();
        return demandRvAdapter;
    }


    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        titleText = (TextView) findViewById(R.id.title_text);
    }
}
