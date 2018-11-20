package cn.dankal.my.activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.basiclib.ResultCode;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.BankCardListBean;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.ActivityUtils;
import cn.dankal.basiclib.util.BankUtil;
import cn.dankal.basiclib.widget.GenDialog;
import cn.dankal.my.presenter.BankCardContact;
import cn.dankal.my.presenter.BankCardPersenter;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.BINDCARD;

@Route(path = BINDCARD)
public class BankCardActivity extends BaseActivity implements BankCardContact.bcView {

    private android.widget.ImageView backImg;
    private android.widget.TextView enterCardId;
    private android.widget.EditText cardIdEt;
    private android.widget.EditText nameEt;
    private android.widget.EditText cardIdentityEt;
    private android.widget.EditText phoneNumEt;
    private android.widget.EditText codeVeEt;
    private android.widget.Button getVeCodeText;
    private android.widget.Button submitBtn;
    private BankCardPersenter bankCardPersenter = BankCardPersenter.getBankCardPersenter();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bank_card;
    }

    @Override
    protected void initComponents() {
        initView();
        bankCardPersenter.attachView(this);
        backImg.setOnClickListener(v -> finish());
        submitBtn.setOnClickListener(v -> {
            String bankname= BankUtil.getname(cardIdEt.getText().toString());
            bankCardPersenter.bindCard(cardIdEt.getText().toString().trim(), nameEt.getText().toString().trim(), cardIdentityEt.getText().toString().trim(), phoneNumEt.getText().toString().trim(),bankname, codeVeEt.getText().toString().trim());
        });
        getVeCodeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankCardPersenter.sendCode(getVeCodeText, phoneNumEt.getText().toString().trim());
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        enterCardId = (TextView) findViewById(R.id.enter_card_id);
        cardIdEt = (EditText) findViewById(R.id.card_id_et);
        nameEt = (EditText) findViewById(R.id.name_et);
        cardIdentityEt = (EditText) findViewById(R.id.card_identity_et);
        phoneNumEt = (EditText) findViewById(R.id.phone_num_et);
        codeVeEt = (EditText) findViewById(R.id.code_ve_et);
        getVeCodeText = findViewById(R.id.getVeCode_text);
        submitBtn = (Button) findViewById(R.id.submit_btn);
    }

    @Override
    public void bindCardSuccess() {
        GenDialog.CustomBuilder2 customBuilder2 = new GenDialog.CustomBuilder2(BankCardActivity.this);
        customBuilder2.setContent(R.layout.finish_dialog);
        Dialog dialog1 = customBuilder2.create();
        TextView context = dialog1.findViewById(R.id.content_tv);
        context.setText("银行卡添加成功");
        Button ok_btn = dialog1.findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(v1 -> {
            dialog1.dismiss();
            finish();
            ActivityUtils.finishActivity(WithdrawalActivity.class);
            ARouter.getInstance().build(MyProtocol.WITHDRAWAL).navigation();
        });

        dialog1.show();
    }

    @Override
    public void bindCardFail() {
        GenDialog.CustomBuilder2 customBuilder2 = new GenDialog.CustomBuilder2(BankCardActivity.this);
        customBuilder2.setContent(R.layout.finish_dialog);
        Dialog dialog1 = customBuilder2.create();
        TextView context = dialog1.findViewById(R.id.content_tv);
        context.setText("银行卡添加失败\n请填写正确信息");
        ImageView title_img = dialog1.findViewById(R.id.image_title);
        title_img.setImageResource(R.mipmap.pic_profit_recharge_fail);
        Button ok_btn = dialog1.findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(v12 -> dialog1.dismiss());
        dialog1.show();
    }

    @Override
    public void getBankCardListSuccess(BankCardListBean bankCardListBean) {

    }
}
