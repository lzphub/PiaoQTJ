package cn.dankal.user.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.user.R;

import static cn.dankal.basiclib.protocol.LoginProtocol.REGISTERENTEREMSIL;
import static cn.dankal.basiclib.protocol.LoginProtocol.REGISTERUSER;

@Route(path = REGISTERUSER)
public class UserRegistrActivity extends BaseActivity {


    private ImageView backImg;
    private TextView titleText;
    private TextView tvPhoneNum;
    private EditText etEmailNum;
    private Button btNext;
    private String type="";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_registr;
    }

    @Override
    protected void initComponents() {
        initView();
        titleText.setText("FAST REGISTRATION");
        tvPhoneNum.setText("EMAIL ACCOUNTS");
        etEmailNum.setHint("PLEASE FILL OUT YOUR EMAIL ACCOUNT");
        btNext.setText("COUNTINUE");
        type=getIntent().getStringExtra("type");
        backImg.setOnClickListener(v -> finish());
        btNext.setOnClickListener(v -> {
            if(StringUtil.checkEmail(etEmailNum.getText().toString())){
                ARouter.getInstance().build(LoginProtocol.USERREGISTERVECODE).withString("emailAccount",etEmailNum.getText().toString().trim()).withString("type",type).navigation();
                finish();
            }else{
                Toast.makeText(UserRegistrActivity.this, "Please fill in the correct email account", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        titleText = (TextView) findViewById(R.id.title_text);
        tvPhoneNum = (TextView) findViewById(R.id.tv_phone_num);
        etEmailNum = (EditText) findViewById(R.id.et_email_num);
        btNext = (Button) findViewById(R.id.bt_next);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
