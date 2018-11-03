package cn.dankal.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.address.R;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.HomeProtocol;

import static cn.dankal.basiclib.protocol.HomeProtocol.CLAIMDEMAND;
import static cn.dankal.basiclib.protocol.HomeProtocol.DEMANDDETA;

@Route(path = DEMANDDETA)
public class DemandDetailsActivity extends BaseActivity {

    private android.widget.Button claimBtn;
    private android.widget.ImageView backImg;
    private android.widget.TextView demandTitle;
    private android.widget.TextView demandPrice;
    private android.widget.TextView demandTime;
    private android.widget.TextView demandData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_demand_details;
    }

    @Override
    protected void initComponents() {
        initView();
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        claimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(HomeProtocol.CLAIMDEMAND).navigation();
            }
        });
    }

    private void initView() {
        claimBtn = (Button) findViewById(R.id.claim_btn);
        backImg = (ImageView) findViewById(R.id.back_img);
        demandTitle = (TextView) findViewById(R.id.demand_title);
        demandPrice = (TextView) findViewById(R.id.demand_price);
        demandTime = (TextView) findViewById(R.id.demand_time);
        demandData = (TextView) findViewById(R.id.demand_data);
    }
}
