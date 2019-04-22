package cn.xunzi.my.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.base.callback.DKCallBackBooleanObject;
import cn.xunzi.basiclib.bean.BanlanceBean;
import cn.xunzi.basiclib.protocol.MyProtocol;
import cn.xunzi.basiclib.util.BankUtil;
import cn.xunzi.basiclib.util.ToastUtils;
import cn.xunzi.basiclib.widget.GenDialog;
import cn.xunzi.basiclib.widget.PasswordDialog;
import cn.xunzi.my.presenter.MyWithConract;
import cn.xunzi.my.presenter.MyWithPresenter;
import cn.xunzi.setting.R;

import static cn.xunzi.basiclib.protocol.MyProtocol.WITH;

@Route(path = WITH)
public class WithActivity extends BaseActivity implements MyWithConract.mtView {

    private ImageView imBack;
    private TextView tvDeta;
    private RelativeLayout rlAddCard;
    private TextView tvCard;
    private TextView tvMoney;
    private EditText etMoney;
    private Button btWith;
    private boolean isWithPwd = true;
    private TextView tvTip;
    private MyWithPresenter myWithPresenter;
    private String bankNo, bankId;
    private int banlance;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_with;
    }

    @Override
    protected void initComponents() {
        initView();
        myWithPresenter = new MyWithPresenter();
        myWithPresenter.attachView(this);
        myWithPresenter.getBanlance(XZUserManager.getUserInfo().getId() + "");
        imBack.setOnClickListener(view -> finish());
        rlAddCard.setOnClickListener(view -> {
            Intent intent = new Intent(WithActivity.this, MyCardActivity.class);
            intent.putExtra("type", "with");
            startActivityForResult(intent, 20);
        });
        tvDeta.setOnClickListener(view -> ARouter.getInstance().build(MyProtocol.WITHDETA).navigation());
        btWith.setOnClickListener(view -> {
            if (!XZUserManager.getUserInfo().getPayCode().toString().trim().isEmpty()) {
                if (tvCard.getText().equals("选择银行卡")) {
                    ToastUtils.showShort("请选择银行卡");
                    return;
                }
                if (etMoney.getText().toString().isEmpty()) {
                    ToastUtils.showShort("请填写金额");
                    return;
                }
                if(((float)banlance) < ((float) Integer.valueOf(etMoney.getText().toString()))){
                    ToastUtils.showShort("可提现金额不足");
                    return;
                }
                if(Integer.valueOf(etMoney.getText().toString())%100!=0){
                    ToastUtils.showShort("提现金额必须为100的倍数");
                    return;
                }
                PasswordDialog dialog = new PasswordDialog();
                Bundle bundle = new Bundle();
                bundle.putString("title", "请输入提现密码");
                bundle.putString("error_toast", "请输入6位密码");
                bundle.putString("bank", tvCard.getText().toString() + "（" + bankNo.substring(bankNo.length() - 4) + "）");
                bundle.putString("cash_money", "¥ " + etMoney.getText().toString().trim());
                dialog.setArguments(bundle);
                dialog.setConfirmOrderCallback((DKCallBackBooleanObject<String>) (type, passwd) -> {
                    dialog.dismiss();
                    if (passwd.equals(XZUserManager.getUserInfo().getPayCode())) {
                        myWithPresenter.withBanlance(XZUserManager.getUserInfo().getId() + "", etMoney.getText().toString().trim(), bankId, passwd);
                    }else{
                        dialog.dismiss();
                        ToastUtils.showShort("提现密码错误");
                    }
                });
                dialog.setOnItemClickLitener(() -> startActivityForResult(new Intent(WithActivity.this, MyCardActivity.class).putExtra("type", "with"), 20));
                dialog.show(getSupportFragmentManager(), "PasswordDialog");
            } else {
                GenDialog.CustomBuilder2 customBuilder2 = new GenDialog.CustomBuilder2(WithActivity.this);
                Dialog dialog1;
                customBuilder2.setContent(R.layout.setpwd_dialog);
                dialog1 = customBuilder2.create();
                Button btSetPwd = dialog1.findViewById(R.id.bt_setwithpwd);
                dialog1.show();
                btSetPwd.setOnClickListener(view1 -> {
                    ARouter.getInstance().build(MyProtocol.SETWITHPWD).navigation();
                    dialog1.dismiss();
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            if (requestCode == 20) {
                String three = data.getStringExtra("name");
                bankNo = data.getStringExtra("no");
                bankId = data.getStringExtra("id");
                tvCard.setText(three);
            }
        }
    }

    private void initView() {
        imBack = findViewById(R.id.im_back);
        tvDeta = findViewById(R.id.tv_deta);
        rlAddCard = findViewById(R.id.rl_add_card);
        tvCard = findViewById(R.id.tv_card);
        tvMoney = findViewById(R.id.tv_money);
        etMoney = findViewById(R.id.et_money);
        btWith = findViewById(R.id.bt_with);
        tvTip = findViewById(R.id.tv_tip);
    }

    @Override
    public void getBanlanceSuccess(BanlanceBean banlanceBean) {
        tvMoney.setText(banlanceBean.getData().getBalance() + "");
        tvTip.setText("备注：您提现的每一笔现金，平台将收取" + banlanceBean.getData().getFee() + "手续费");
        banlance=banlanceBean.getData().getBalance();
    }

    @Override
    public void getWithSuccess() {
        myWithPresenter.getBanlance(XZUserManager.getUserInfo().getId() + "");
        etMoney.setText("");
        ToastUtils.showShort("申请提交成功");
    }

    @Override
    public void getWithFail() {
        ToastUtils.showShort("申请提交失败");
    }
}
