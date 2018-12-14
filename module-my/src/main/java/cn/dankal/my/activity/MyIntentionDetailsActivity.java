package cn.dankal.my.activity;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.IntentionDateBean;
import cn.dankal.basiclib.protocol.ProductProtocol;
import cn.dankal.basiclib.util.StateUtil;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.image.PicUtils;
import cn.dankal.my.presenter.IntentionDetailsContact;
import cn.dankal.my.presenter.MyIntentionDetailsPresenter;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYINTENTIONDETA;

/**
 * 我的意向详情
 */
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
    private MyIntentionDetailsPresenter myIntentionDetailsPresenter=MyIntentionDetailsPresenter.getPSPresenter();
    private TextView tvTitle;
    private String product_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_intention_details;
    }

    @Override
    protected void initComponents() {
        initView();
        intention_id=getIntent().getStringExtra("intention_id");
        product_id=getIntent().getStringExtra("product_id");
        myIntentionDetailsPresenter.attachView(this);
        myIntentionDetailsPresenter.getData(intention_id);
        backImg.setOnClickListener(v -> finish());

        SpannableString spannableString=new SpannableString("PURCHASE \n INTENTION DETAILS");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.login_btn_bg));
        spannableString.setSpan(colorSpan, spannableString.length()-7, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvTitle.setText(spannableString);

        intentionInfo.setOnClickListener(v -> {
            ARouter.getInstance().build(ProductProtocol.PRODUCTDETA).withString("uuid",product_id).navigation();
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
        tvTitle = findViewById(R.id.tv_title);
    }

    @Override
    public void getDataSuccess(IntentionDateBean intentionDateBean) {
        intentionName.setText(intentionDateBean.getProduct_name());
        intentionPrice.setText("$"+StringUtil.isDigits(intentionDateBean.getProduct_price()));
        intentionState.setText(StateUtil.intentionStatus(intentionDateBean.getStatus()));
        Glide.with(this).load(PicUtils.getUrl(intentionDateBean.getImages().get(0))).into(intentionImg);
        contactsName.setText(intentionDateBean.getContact_name());
        contactsMail.setText(intentionDateBean.getEmail());
        contactsNumber.setText(intentionDateBean.getContact_phone());
        intentionDeta.setText(intentionDateBean.getExtra_info());
        intention_id=intentionDateBean.getIntention_id();
    }
}
