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

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.util.ActivityUtils;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_withda_pwd;
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
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenDialog.CustomBuilder2 customBuilder2 = new GenDialog.CustomBuilder2(SetWithdaPwdActivity.this);
                customBuilder2.setContent(R.layout.finish_dialog);
                Dialog dialog1 = customBuilder2.create();
                Button ok_btn=dialog1.findViewById(R.id.ok_btn);
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                        ActivityUtils.finishActivity(SetPwdCodeActivity.class);
                        ActivityUtils.finishActivity(SetWithdaPwdActivity.class);
                    }
                });
                dialog1.show();
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        titleText = (TextView) findViewById(R.id.title_text);
        tvPhoneNum = (TextView) findViewById(R.id.tv_phone_num);
        etPhoneNum = (EditText) findViewById(R.id.et_phone_num);
        passwd = (TextView) findViewById(R.id.passwd);
        etPasswd = (EditText) findViewById(R.id.et_passwd);
        btNext = (Button) findViewById(R.id.bt_next);
    }
}
