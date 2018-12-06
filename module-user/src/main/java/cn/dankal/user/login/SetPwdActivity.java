package cn.dankal.user.login;

import android.content.pm.PackageManager;
import android.widget.*;
import android.view.*;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import api.UserServiceFactory;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.exception.LocalException;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
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
    private String type;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_pwd;
    }

    @Override
    protected void initComponents() {
        initView();
        type=getIntent().getStringExtra("type");
        backImg.setOnClickListener(v -> finish());
        bt_next.setOnClickListener(v -> {
            String email=getIntent().getStringExtra("emailAccount");
            String pwd=etPhoneNum.getText().toString().trim();
            String pwd2=etPasswd.getText().toString().trim();
            if(pwd==null || pwd2==null){
                ToastUtils.showShort("请输入密码");
                return;
            }
            if(!pwd2.equals(pwd)){
                ToastUtils.showShort("请确认密码一致");
                return;
            }
            if(type.equals("sign_up")){
                UserServiceFactory.engResetPassword(email, pwd).safeSubscribe(new AbstractDialogSubscriber<String>(SetPwdActivity.this) {
                    @Override
                    public void onNext(String s) {
                        Toast.makeText(SetPwdActivity.this, getResources().getString(R.string.setpwdOk), Toast.LENGTH_SHORT).show();
                        finishAllActivities();
                        ARouter.getInstance().build(LoginProtocol.ENTERPRISELOGIN).navigation();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof LocalException) {
                            LocalException exception = (LocalException) e;
                            //401 重新获取access token , 如果还返回412 就是refresh token 也失效了。需要重新登录
                                ToastUtils.showShort(((LocalException) e).getMsg());
                        }
                    }
                });
            }else{
                UserServiceFactory.engChangePwd(email,pwd).safeSubscribe(new AbstractDialogSubscriber<String>(SetPwdActivity.this) {
                    @Override
                    public void onNext(String s) {
                        Toast.makeText(SetPwdActivity.this, getResources().getString(R.string.setpwdOk), Toast.LENGTH_SHORT).show();
                        finishAllActivities();
                        ARouter.getInstance().build(LoginProtocol.ENTERPRISELOGIN).navigation();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof LocalException) {
                            LocalException exception = (LocalException) e;
                            ToastUtils.showShort((exception.getMsg()));
                        }
                    }
                });
            }


        });
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        tvPhoneNum = findViewById(R.id.tv_phone_num);
        etPhoneNum = findViewById(R.id.et_phone_num);
        dividerPhone = findViewById(R.id.divider_phone);
        passwd = findViewById(R.id.passwd);
        etPasswd = findViewById(R.id.et_passwd);
        dividerPasswd = findViewById(R.id.divider_passwd);
        bt_next = findViewById(R.id.bt_next);
    }
}
