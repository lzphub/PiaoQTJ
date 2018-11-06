package cn.dankal.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.SETWITHPEDCODE;

@Route(path = SETWITHPEDCODE)
public class SetPwdCodeActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.TextView titleText;
    private android.widget.TextView tips;
    private android.widget.TextView tvPhoneNum;
    private android.widget.Button getVeCode;
    private android.widget.EditText etEmailNum;
    private android.widget.Button btNext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_pwd_code;
    }

    @Override
    protected void initComponents() {
        initView();
        int code=getIntent().getIntExtra("type",0);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.SETWITHPWD).withInt("type",code).navigation();
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        titleText = (TextView) findViewById(R.id.title_text);
        tips = (TextView) findViewById(R.id.tips);
        tvPhoneNum = (TextView) findViewById(R.id.tv_phone_num);
        getVeCode = (Button) findViewById(R.id.getVeCode);
        etEmailNum = (EditText) findViewById(R.id.et_email_num);
        btNext = (Button) findViewById(R.id.bt_next);
    }
}
