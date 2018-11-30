package cn.dankal.user.login;

import android.widget.*;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.user.R;

import static cn.dankal.basiclib.protocol.LoginProtocol.REGISTERENTEREMSIL;

@Route(path = REGISTERENTEREMSIL)
public class RegistrActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.TextView tvPhoneNum;
    private android.widget.EditText etPhoneNum;
    private android.view.View dividerPhone;
    private Button next_btn;
    private String type;
    private TextView titleText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_registr;
    }

    @Override
    protected void initComponents() {
        initView();
        type=getIntent().getStringExtra("type");
        if(type.equals("change_pwd")){
            titleText.setText("忘记密码");
        }
        backImg.setOnClickListener(v -> finish());
        next_btn.setOnClickListener(v -> {
            if(StringUtil.checkEmail(etPhoneNum.getText().toString())){
                ARouter.getInstance().build(LoginProtocol.REGISTERVECODE).withString("type",type).withString("emailAccount",etPhoneNum.getText().toString().trim()).navigation();
            }else{
                Toast.makeText(RegistrActivity.this, "请填写正确的邮箱账号", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        tvPhoneNum = findViewById(R.id.tv_phone_num);
        etPhoneNum = findViewById(R.id.et_email_num);
        dividerPhone = findViewById(R.id.divider_phone);
        next_btn=findViewById(R.id.bt_next);
        titleText = findViewById(R.id.title_text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
