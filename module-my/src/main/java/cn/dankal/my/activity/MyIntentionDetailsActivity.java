package cn.dankal.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.ProductProtocol;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYINTENTIONDETA;

@Route(path = MYINTENTIONDETA)
public class MyIntentionDetailsActivity extends BaseActivity {

    private ImageView backImg;
    private ImageView intentionImg;
    private TextView intentionName;
    private TextView intentionPrice;
    private TextView intentionState;
    private TextView contactsName;
    private TextView contactsNumber;
    private TextView contactsMail;
    private TextView intentionDeta;
    private RelativeLayout intentionInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_intention_details;
    }

    @Override
    protected void initComponents() {
        initView();
        backImg.setOnClickListener(v -> finish());
        intentionInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ProductProtocol.PRODUCTDETA).navigation();
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        intentionImg = (ImageView) findViewById(R.id.intention_img);
        intentionName = (TextView) findViewById(R.id.intention_name);
        intentionPrice = (TextView) findViewById(R.id.intention_price);
        intentionState = (TextView) findViewById(R.id.intention_state);
        contactsName = (TextView) findViewById(R.id.contacts_name);
        contactsNumber = (TextView) findViewById(R.id.contacts_number);
        contactsMail = (TextView) findViewById(R.id.contacts_mail);
        intentionDeta = (TextView) findViewById(R.id.intention_deta);
        intentionInfo = (RelativeLayout) findViewById(R.id.intention_info);
    }
}
