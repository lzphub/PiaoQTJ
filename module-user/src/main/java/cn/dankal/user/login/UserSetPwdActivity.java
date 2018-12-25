package cn.dankal.user.login;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import org.json.JSONException;
import org.json.JSONObject;

import api.UserServiceFactory;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.domain.HttpStatusCode;
import cn.dankal.basiclib.exception.LocalException;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.user.R;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static cn.dankal.basiclib.protocol.LoginProtocol.USERSETPWD;
import static cn.dankal.basiclib.util.AppUtils.finishAllActivities;

@Route(path = USERSETPWD)
public class UserSetPwdActivity extends BaseActivity {

    private ImageView backImg;
    private TextView tvPhoneNum;
    private EditText etPhoneNum;
    private TextView passwd;
    private EditText etPasswd;
    private Button bt_next;
    private TextView titleText;
    private String type = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_pwd;
    }

    @Override
    protected void initComponents() {
        initView();
        type = getIntent().getStringExtra("type");
        titleText.setText("FAST REGISTEATION");
        tvPhoneNum.setText("PASSWORD");
        etPhoneNum.setHint("PLEASE WNTER YOUR PASSWORD");
        passwd.setText("CONFIRM YOUR PASSWORD");
        etPasswd.setHint("PLEASE ENTER YOUR PASSWORD AGAIN");
        bt_next.setText("CONTINUE");
        backImg.setOnClickListener(v -> finish());
        bt_next.setOnClickListener(v -> {
            if (etPhoneNum.getText().toString().trim() == null || etPasswd.getText().toString().trim() == null) {
                ToastUtils.showShort("PLEASE INPUT YOUR PASSWORD");
            } else {
                if (etPhoneNum.getText().toString().trim().equals(etPasswd.getText().toString().trim()) && StringUtil.isValid(etPhoneNum.getText().toString().trim())) {
                    if ("change_pwd".equals(type)) {
                        changePwd(getIntent().getStringExtra("emailAccount"), etPhoneNum.getText().toString().trim());
                    } else {
                        setPasswd(getIntent().getStringExtra("emailAccount"), etPhoneNum.getText().toString().trim());
                    }
                } else {
                    ToastUtils.showShort("PLEASE CONFIRM PASSWORD CONSISTENCY");
                }
            }
        });
    }

    private void changePwd(String email, String pwd) {
        UserServiceFactory.changePwd(email, pwd).safeSubscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Toast.makeText(UserSetPwdActivity.this, "REGISTERED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                ARouter.getInstance().build(LoginProtocol.USERSLOGIN).navigation();
                finishAllActivities();
            }

            @Override
            public void onError(Throwable e) {
                dismissLoadingDialog();
                if (e instanceof LocalException) {
                    LocalException exception = (LocalException) e;
                    if (exception.getMsg().equals("password不能为空")) {
                        ToastUtils.showShort("PLEASE INPUT YOUR PASSWORD");
                    }
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }


    private void setPasswd(String email, String pwd) {
        UserServiceFactory.resetPassword(email, pwd).safeSubscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Toast.makeText(UserSetPwdActivity.this, "REGISTERED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                ARouter.getInstance().build(LoginProtocol.USERSLOGIN).navigation();
                finishAllActivities();
            }

            @Override
            public void onError(Throwable e) {
                dismissLoadingDialog();
                if (e instanceof LocalException) {
                    LocalException exception = (LocalException) e;
                    if (exception.getMsg().equals("此邮箱已被注册")) {
                        ToastUtils.showShort("This email address has been registered");
                    } else if (exception.getMsg().equals("password不能为空")) {
                        ToastUtils.showShort("PLEASE INPUT YOUR PASSWORD");
                    }else if(exception.getMsg().equals("网络错误")){
                        ToastUtils.showShort("Network error");
                    }
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        tvPhoneNum = findViewById(R.id.tv_phone_num);
        etPhoneNum = findViewById(R.id.et_phone_num);
        passwd = findViewById(R.id.passwd);
        etPasswd = findViewById(R.id.et_passwd);
        bt_next = findViewById(R.id.bt_next);
        titleText = findViewById(R.id.title_text);
    }
}
