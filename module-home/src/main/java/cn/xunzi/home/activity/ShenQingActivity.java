package cn.xunzi.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.xunzi.address.R;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.protocol.HomeProtocol;

import static cn.xunzi.basiclib.protocol.HomeProtocol.VIP;

@Route(path = VIP)
public class ShenQingActivity extends BaseActivity {

    private ImageView ivBack;
    private RelativeLayout rlVip;
    private RelativeLayout rlSvip;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shen_qing;
    }

    @Override
    protected void initComponents() {
        initView();
        ivBack.setOnClickListener(view -> finish());
        rlSvip.setOnClickListener(view -> ARouter.getInstance().build(HomeProtocol.PAYMODE).withString("type","svip").navigation());
        rlVip.setOnClickListener(view -> ARouter.getInstance().build(HomeProtocol.PAYMODE).withString("type","vip").navigation());

    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        rlVip = findViewById(R.id.rl_vip);
        rlSvip = findViewById(R.id.rl_svip);
    }
}
