package cn.xunzi.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.xunzi.address.R;
import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.protocol.HomeProtocol;

import static cn.xunzi.basiclib.protocol.HomeProtocol.PAYMODE;

@Route(path = PAYMODE)
public class PayActivity extends BaseActivity {

    private ImageView ivBack;
    private RelativeLayout llAlipay;
    private Button btSubmit;
    private TextView tvPrice;
    private String type;
    private TextView tvTip;
    private int level;
    private int types;

    @Override
    protected int getLayoutId() {
        type = getIntent().getStringExtra("type");
        return R.layout.activity_pay;
    }

    @Override
    protected void initComponents() {
        initView();
        level = Integer.valueOf(XZUserManager.getUserInfo().getRoleType());
        ivBack.setOnClickListener(view -> finish());
        if (type.equals("vip")) {
            tvPrice.setText("¥ 99");
            if (level > 0) {
                btSubmit.setBackgroundResource(R.drawable.login_btn_bg_b7);
            }
        } else {
            tvPrice.setText("¥ 999");
            if (level > 1) {
                btSubmit.setBackgroundResource(R.drawable.login_btn_bg_b7);
            }
        }
        if (type.equals("vip") && level == 1) {
            btSubmit.setEnabled(false);
        } else if (type.equals("svip") && level == 2) {
            btSubmit.setEnabled(false);
        } else if (type.equals("vip") && level == 2) {
            btSubmit.setEnabled(false);
        } else if (type.equals("svip") && level == 1) {

        }

        switch (level) {
            case 0:
                tvTip.setText("");
                break;
            case 1:
                tvTip.setText("您已是VIP会员！");
                break;
            case 2:
                tvTip.setText("您已是超级VIP会员！");
                break;
        }

        if(type.equals("vip")){
            types=1;
        }else{
            types=2;
        }
        btSubmit.setOnClickListener(view -> ARouter.getInstance().build(HomeProtocol.PAYDATAS).withInt("type",types).navigation());

    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        llAlipay = findViewById(R.id.ll_alipay);
        btSubmit = findViewById(R.id.bt_submit);
        tvPrice = findViewById(R.id.tv_price);
        tvTip = findViewById(R.id.tv_tip);
    }
}
