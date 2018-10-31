package cn.dankal.user.login;

import android.view.*;
import android.widget.*;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.user.R;

import static cn.dankal.basiclib.protocol.LoginProtocol.REGISTERENTEREMSIL;

@Route(path = REGISTERENTEREMSIL)
public class RegistrActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.TextView tvPhoneNum;
    private android.widget.EditText etPhoneNum;
    private android.view.View dividerPhone;
    private Button next_btn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_registr;
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
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etPhoneNum.getText().toString().trim().isEmpty()){
                    ARouter.getInstance().build(LoginProtocol.REGISTERVECODE).withString("emailAccount",etPhoneNum.getText().toString().trim()).navigation();
                }else{
                    Toast.makeText(RegistrActivity.this, "请填写正确的邮箱账号", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        tvPhoneNum = (TextView) findViewById(R.id.tv_phone_num);
        etPhoneNum = (EditText) findViewById(R.id.et_email_num);
        dividerPhone = (View) findViewById(R.id.divider_phone);
        next_btn=findViewById(R.id.bt_next);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("msg","onDestroy");
    }
}
