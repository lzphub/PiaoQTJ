package cn.dankal.my.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.callback.DKCallBackBooleanObject;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.ActivityUtils;
import cn.dankal.basiclib.util.BankUtil;
import cn.dankal.basiclib.util.LangHelper;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.widget.GenDialog;
import cn.dankal.basiclib.widget.PasswordDialog;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.WITHDRAWAL;
import static cn.dankal.my.activity.MyEarningsActivity.threshold;

@Route(path = WITHDRAWAL)
public class WithdrawalActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.RelativeLayout addCard;
    private android.widget.EditText moenyEt;
    private android.widget.TextView availableBalance;
    private android.widget.TextView allWith;
    private android.widget.Button withBtn;

    private String cardNum = "6222600260001072444";
    private String bankUUID = "0";
    private String cashMoney = "100";
    private String kBean;
    private String poundage;
    private double leftMoney;
    private TextView bankCardText;
    private TextView rmbLogo;
    private TextView rmbLogo2;
    private ImageView deleteImage;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawal;
    }

    @Override
    protected void initComponents() {
        initView();
        kBean=getIntent().getStringExtra("balance");
        availableBalance.setText("可用余额 "+kBean+"元");
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.CHECKCARD).navigation();
            }
        });
        allWith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moenyEt.setText(kBean);
                moenyEt.requestFocus();
                moenyEt.setSelection(moenyEt.getText().toString().length());
            }
        });
        withBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashMoney = moenyEt.getText().toString();
                if (!StringUtil.isValid(cashMoney)) {
                    ToastUtils.showShort("请填写提现金额");
                    return;
                }
                if (Double.valueOf(cashMoney) < threshold) {
                    ToastUtils.showShort("最低提现金额为"
                            + LangHelper.regularizePrice(threshold) + "元");
                    return;
                }
                if (checkMoney()) {
                    return;
                }
//                if ("添加银行卡".equals(bankCardText.getText())) {
//                    ToastUtils.showShort("请选择银行卡");
//                    return;
//                }
                showPwdDialog();
            }
        });
        moenyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cashMoney = moenyEt.getText().toString();
                if (!StringUtil.isValid(cashMoney)) {
                    rmbLogo2.setVisibility(View.INVISIBLE);
                    rmbLogo.setVisibility(View.VISIBLE);
                    deleteImage.setVisibility(View.GONE);
                    moenyEt.setTextSize(15);
                    return;
                }
                rmbLogo.setVisibility(View.INVISIBLE);
                rmbLogo2.setVisibility(View.VISIBLE);
                deleteImage.setVisibility(View.VISIBLE);
                moenyEt.setTextSize(49);
                if(checkMoney()){
                    availableBalance.setText("金额已超过可提现金额");
                    availableBalance.setTextColor(getResources().getColor(R.color.colorf5a623));
                    allWith.setTextColor(getResources().getColor(R.color.colored5855));
                    allWith.setEnabled(false);
                    withBtn.setEnabled(false);
                    withBtn.setBackgroundResource(R.drawable.login_btn_bg_b7);
                }else{
                    availableBalance.setText("可用余额 "+kBean+"元");
                    availableBalance.setTextColor(getResources().getColor(R.color.color66));
                    allWith.setTextColor(getResources().getColor(R.color.color528e1a));
                    allWith.setEnabled(true);
                    withBtn.setEnabled(true);
                    withBtn.setBackgroundResource(R.drawable.login_btn_bg);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moenyEt.setText("");
            }
        });
    }


    /**
     * 检查余额
     *
     * @return
     */
    private boolean checkMoney() {
        if (Float.parseFloat(cashMoney) > Float.parseFloat(kBean)) {
            ToastUtils.showShort("余额不足");
            return true;
        }
        return false;
    }

    private void showPwdDialog(){
        PasswordDialog dialog = new PasswordDialog();
        Bundle bundle = new Bundle();
        bundle.putString("title", "请输入提现密码");
        bundle.putString("error_toast", "请输入6位密码");
        bundle.putString("bank", BankUtil.getname(cardNum) + "（" + cardNum.substring(cardNum.length() - 4) + "）");
        bundle.putString("cash_money", "￥" + cashMoney);
        dialog.setArguments(bundle);
        dialog.setConfirmOrderCallback(new DKCallBackBooleanObject<String>() {
            @Override
            public void action(int type, String passwd) {
                dialog.dismiss();
                showOkDialog();
            }
        });
        dialog.show(getSupportFragmentManager(), "PasswordDialog");
    }

    private void showOkDialog(){
        GenDialog.CustomBuilder2 customBuilder2 = new GenDialog.CustomBuilder2(WithdrawalActivity.this);
        Dialog dialog1;
        if(false){
            customBuilder2.setContent(R.layout.with_finish_dialog);
            dialog1 = customBuilder2.create();
            Button ok_btn=dialog1.findViewById(R.id.ok_btn);
            ok_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                    finish();
                }
            });
        }else{
            customBuilder2.setContent(R.layout.with_fail_dialog);
            dialog1 = customBuilder2.create();
            Button for_btn=dialog1.findViewById(R.id.forgot_btn);
            for_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                    ARouter.getInstance().build(MyProtocol.SETWITHPEDCODE).navigation();
                }
            });
            Button retry_btn=dialog1.findViewById(R.id.retry_btn);
            retry_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                    showPwdDialog();
                }
            });
        }
        dialog1.show();
    }
    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        addCard = (RelativeLayout) findViewById(R.id.add_card);
        moenyEt = (EditText) findViewById(R.id.moeny_et);
        availableBalance = (TextView) findViewById(R.id.available_balance);
        allWith = (TextView) findViewById(R.id.all_with);
        withBtn = (Button) findViewById(R.id.with_btn);
        bankCardText = (TextView) findViewById(R.id.bank_card_text);
        rmbLogo = (TextView) findViewById(R.id.rmb_logo);
        rmbLogo2 = (TextView) findViewById(R.id.rmb_logo2);
        deleteImage = (ImageView) findViewById(R.id.delete_image);
    }
}
