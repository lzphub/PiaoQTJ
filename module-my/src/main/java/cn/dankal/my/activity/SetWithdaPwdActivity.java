package cn.dankal.my.activity;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import api.MyServiceFactory;
import cn.dankal.basiclib.ResultCode;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.ActivityUtils;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.widget.GenDialog;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.SETWITHPWD;

@Route(path = SETWITHPWD)
public class SetWithdaPwdActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.TextView titleText;
    private android.widget.TextView tvPhoneNum;
    private android.widget.EditText etPhoneNum;
    private android.widget.TextView passwd;
    private android.widget.EditText etPasswd;
    private android.widget.Button btNext;
    private String email;
    private int code;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_withda_pwd;
    }

    @Override
    protected void initComponents() {
        initView();
        code = getIntent().getIntExtra("type", 0);
        email = getIntent().getStringExtra("email");
        backImg.setOnClickListener(v -> finish());
        btNext.setOnClickListener(v -> {
            if (etPhoneNum.getText().toString().trim().equals(etPasswd.getText().toString().trim())) {
                setPwd(etPasswd.getText().toString().trim());
            } else {
                ToastUtils.showShort("两次输入不一致");
            }

        });
    }

    private void setPwd(String pwd) {
        MyServiceFactory.setWithPwd(pwd).safeSubscribe(new AbstractDialogSubscriber<String>(this) {
            @Override
            public void onNext(String s) {
                GenDialog.CustomBuilder2 customBuilder2 = new GenDialog.CustomBuilder2(SetWithdaPwdActivity.this);
                customBuilder2.setContent(R.layout.finish_dialog);
                Dialog dialog1 = customBuilder2.create();
                Button ok_btn = dialog1.findViewById(R.id.ok_btn);
                ok_btn.setOnClickListener(v -> {
                    dialog1.dismiss();
                    ActivityUtils.finishActivity(SetPwdCodeActivity.class);
                    ActivityUtils.finishActivity(SetWithdaPwdActivity.class);
                    if (code == ResultCode.myEarCode) {
                        ARouter.getInstance().build(MyProtocol.WITHDRAWAL).navigation();
                    }
                });
                dialog1.show();
            }
        });
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        titleText = findViewById(R.id.title_text);
        tvPhoneNum = findViewById(R.id.tv_phone_num);
        etPhoneNum = findViewById(R.id.et_phone_num);
        passwd = findViewById(R.id.passwd);
        etPasswd = findViewById(R.id.et_passwd);
        btNext = findViewById(R.id.bt_next);
    }
}
