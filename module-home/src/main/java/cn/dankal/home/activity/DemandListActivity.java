package cn.dankal.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.dankal.address.R;
import cn.dankal.basiclib.base.BaseRvActivity;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;

import static cn.dankal.basiclib.protocol.HomeProtocol.HOMEDEMANDLIST;

@Route(path = HOMEDEMANDLIST)
public class DemandListActivity extends BaseRvActivity {

    private android.widget.ImageView backImg;
    private android.support.v7.widget.RecyclerView demandList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_demand_list;
    }

    @Override
    public void initComponents() {
        initView();
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        demandList = (RecyclerView) findViewById(R.id.demand_list);
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
