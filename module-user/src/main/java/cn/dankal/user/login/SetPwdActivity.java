package cn.dankal.user.login;

import android.widget.*;
import android.view.*;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.user.R;

import static cn.dankal.basiclib.protocol.LoginProtocol.SETPWD;
import static cn.dankal.basiclib.util.AppUtils.finishAllActivities;

@Route(path = SETPWD)
public class SetPwdActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.TextView tvPhoneNum;
    private android.widget.EditText etPhoneNum;
    private android.view.View dividerPhone;
    private android.widget.TextView passwd;
    private android.widget.EditText etPasswd;
    private android.view.View dividerPasswd;
    private android.widget.Button bt_next;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_pwd;
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
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SetPwdActivity.this, getResources().getString(R.string.setpwdOk), Toast.LENGTH_SHORT).show();
                finishAllActivities();
                ARouter.getInstance().build(LoginProtocol.ENTERPRISELOGIN).navigation();
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        tvPhoneNum = (TextView) findViewById(R.id.tv_phone_num);
        etPhoneNum = (EditText) findViewById(R.id.et_phone_num);
        dividerPhone = (View) findViewById(R.id.divider_phone);
        passwd = (TextView) findViewById(R.id.passwd);
        etPasswd = (EditText) findViewById(R.id.et_passwd);
        dividerPasswd = (View) findViewById(R.id.divider_passwd);
        bt_next = (Button) findViewById(R.id.bt_next);
    }
}
