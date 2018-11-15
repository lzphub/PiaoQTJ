package cn.dankal.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.IntentionDateBean;
import cn.dankal.basiclib.protocol.ProductProtocol;
import cn.dankal.my.presenter.IntentionDetailsContact;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYINTENTIONDETA;

@Route(path = MYINTENTIONDETA)
public class MyIntentionDetailsActivity extends BaseActivity implements IntentionDetailsContact.idView {

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
    private String intention_id;

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
                /**
                 * 需要携带产品id进行跳转
                 */
                ARouter.getInstance().build(ProductProtocol.PRODUCTDETA).navigation();
            }
        });
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        intentionImg = findViewById(R.id.intention_img);
        intentionName = findViewById(R.id.intention_name);
        intentionPrice = findViewById(R.id.intention_price);
        intentionState = findViewById(R.id.intention_state);
        contactsName = findViewById(R.id.contacts_name);
        contactsNumber = findViewById(R.id.contacts_number);
        contactsMail = findViewById(R.id.contacts_mail);
        intentionDeta = findViewById(R.id.intention_deta);
        intentionInfo = findViewById(R.id.intention_info);
    }

    @Override
    public void getDataSuccess(IntentionDateBean intentionDateBean) {
        intentionName.setText(intentionDateBean.getProduct_name());
        intentionPrice.setText(intentionDateBean.getProduct_price());
        intentionState.setText(intentionDateBean.getStatus()+"");
        Glide.with(this).load(intentionDateBean.getImages().get(0)).into(intentionImg);
        contactsName.setText(intentionDateBean.getContact_name());
        contactsMail.setText(intentionDateBean.getEmail());
        contactsNumber.setText(intentionDateBean.getContact_phone());
        intentionDeta.setText(intentionDateBean.getExtra_info());
        intention_id=intentionDateBean.getIntention_id();
    }
}
