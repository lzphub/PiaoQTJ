package cn.xunzi.user.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import api.UserServiceFactory;
import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.bean.LoginBean;
import cn.xunzi.basiclib.bean.RegisterBean;
import cn.xunzi.basiclib.exception.LocalException;
import cn.xunzi.basiclib.pojo.UserResponseBody;
import cn.xunzi.basiclib.protocol.HomeProtocol;
import cn.xunzi.basiclib.protocol.LoginProtocol;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.StringUtil;
import cn.xunzi.basiclib.util.ToastUtils;
import cn.xunzi.user.R;
import cn.xunzi.user.presenter.LoginPresenter;

import static cn.xunzi.basiclib.protocol.LoginProtocol.USERSLOGIN;

@Route(path = USERSLOGIN)
public class LoginActivity extends BaseActivity {

    private ImageView logo;
    private RelativeLayout rlContent;
    private RelativeLayout rlLogin;
    private LinearLayout llPhone;
    private EditText etLoginPhone;
    private LinearLayout llPwd;
    private EditText etLoginPwd;
    private TextView tvFogPwd;
    private LinearLayout llRegistPhone;
    private EditText etRegistPhone;
    private LinearLayout llRegistYzm;
    private EditText etRegistYzm;
    private Button tvGetcode;
    private LinearLayout llRegistPwd;
    private EditText etRegistPwd;
    private LinearLayout llRegistSubpwd;
    private EditText etRegistSubpwd;
    private LinearLayout llRegistYqm;
    private EditText etRegistYqm;
    private TextView tvLogin;
    private TextView tvRegist;
    private ImageView ivLogin;
    private ImageView ivRegist;
    private Button btLogin;
    private TextView tvToRegist;
    private RelativeLayout rlRegist;
    private TextView tvTip;
    private LoginPresenter loginPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initComponents() {
        initView();
        tvRegist.setOnClickListener(view -> {
            ivLogin.setVisibility(View.GONE);
            ivRegist.setVisibility(View.VISIBLE);
            rlRegist.setVisibility(View.VISIBLE);
            rlLogin.setVisibility(View.GONE);
            btLogin.setText("注册");
            tvToRegist.setText("立即登录");
            tvTip.setText("已有账号,");
        });

        tvLogin.setOnClickListener(view -> {
            ivLogin.setVisibility(View.VISIBLE);
            ivRegist.setVisibility(View.GONE);
            rlRegist.setVisibility(View.GONE);
            rlLogin.setVisibility(View.VISIBLE);
            btLogin.setText("登录");
            tvToRegist.setText("立即注册");
            tvTip.setText("还没有账号？");
        });

        tvToRegist.setOnClickListener(view -> {
            if (tvToRegist.getText().toString().equals("立即注册")) {
                ivLogin.setVisibility(View.GONE);
                ivRegist.setVisibility(View.VISIBLE);
                rlRegist.setVisibility(View.VISIBLE);
                rlLogin.setVisibility(View.GONE);
                btLogin.setText("注册");
                tvToRegist.setText("立即登录");
                tvTip.setText("已有账号,");
            } else {
                ivLogin.setVisibility(View.VISIBLE);
                ivRegist.setVisibility(View.GONE);
                rlRegist.setVisibility(View.GONE);
                rlLogin.setVisibility(View.VISIBLE);
                btLogin.setText("登录");
                tvToRegist.setText("立即注册");
                tvTip.setText("还没有账号？");
            }
        });

        tvGetcode.setOnClickListener(view -> {
            loginPresenter = new LoginPresenter(LoginActivity.this);
            loginPresenter.getCode(etRegistPhone.getText().toString().trim(), tvGetcode, 1 + "");
        });

        tvFogPwd.setOnClickListener(view -> ARouter.getInstance().build(LoginProtocol.FORGETPWD).navigation());

        btLogin.setOnClickListener(view -> {
            if (btLogin.getText().toString().equals("登录")) {
                String tel = etLoginPhone.getText().toString().trim();
                String pwd = etLoginPwd.getText().toString().trim();
                if (tel.isEmpty()) {
                    ToastUtils.showShort("请填写手机号");
                    return;
                }
                if (pwd.isEmpty()) {
                    ToastUtils.showShort("请填写密码");
                    return;
                }
                UserServiceFactory.login(tel, pwd).safeSubscribe(new AbstractDialogSubscriber<UserResponseBody>(LoginActivity.this) {
                    @Override
                    public void onNext(UserResponseBody userResponseBody) {
                        if (userResponseBody.getCode().equals("200")) {
                            XZUserManager.saveUserInfo(userResponseBody);
                            ARouter.getInstance().build(HomeProtocol.HOME).navigation();
                            finish();
                        } else {
                            ToastUtils.showShort(userResponseBody.getMsg());
                        }
                    }
                });

            } else {
                String tel = etRegistPhone.getText().toString().trim();
                String code = etRegistYzm.getText().toString().trim();
                String pwd = etRegistPwd.getText().toString().trim();
                String subpwd = etRegistSubpwd.getText().toString().trim();
                String invateCode = etRegistYqm.getText().toString().trim();
                if (!StringUtil.checkPhone(tel)) {
                    ToastUtils.showShort("请填写正确手机号");
                    return;
                }
                if (code.isEmpty()) {
                    ToastUtils.showShort("请填写验证码");
                    return;
                }
                if(!StringUtil.checkPasswd(pwd) || !StringUtil.checkPasswd(subpwd)){
                    ToastUtils.showShort("密码中不能含有特殊符号");
                    return;
                }
                if (pwd.isEmpty()) {
                    ToastUtils.showShort("请填写密码");
                    return;
                }
                if (pwd.length() < 6) {
                    ToastUtils.showShort("密码应为6-16位字符");
                    return;
                }
                if (!pwd.equals(subpwd)) {
                    ToastUtils.showShort("请确认两次密码输入相同");
                    return;
                }
                if (invateCode.isEmpty()) {
                    ToastUtils.showShort("请输入邀请码");
                    return;
                }

                UserServiceFactory.redister(tel, code, pwd, invateCode).safeSubscribe(new AbstractDialogSubscriber<RegisterBean>(LoginActivity.this) {
                    @Override
                    public void onNext(RegisterBean registerBean) {
                        dismissLoadingDialog();
                        if (registerBean.getCode().equals("200")) {
                            ivLogin.setVisibility(View.VISIBLE);
                            ivRegist.setVisibility(View.GONE);
                            rlRegist.setVisibility(View.GONE);
                            rlLogin.setVisibility(View.VISIBLE);
                            btLogin.setText("登录");
                            tvToRegist.setText("立即注册");
                            tvTip.setText("还没有账号？");
                            ToastUtils.showShort("注册成功");
                            etRegistPhone.setText("");
                            etRegistPwd.setText("");
                            etRegistSubpwd.setText("");
                            etRegistYqm.setText("");
                            etRegistYzm.setText("");
                        }else{
                            ToastUtils.showShort(registerBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoadingDialog();
                        if (e instanceof LocalException) {
                            LocalException exception = (LocalException) e;
                            ToastUtils.showShort(exception.getMsg());
                        }
                    }
                });
            }
        });
    }


    private void initView() {
        logo = findViewById(R.id.logo);
        rlContent = findViewById(R.id.rl_content);
        rlLogin = findViewById(R.id.rl_login);
        llPhone = findViewById(R.id.ll_phone);
        etLoginPhone = findViewById(R.id.et_login_phone);
        llPwd = findViewById(R.id.ll_pwd);
        etLoginPwd = findViewById(R.id.et_login_pwd);
        tvFogPwd = findViewById(R.id.tv_fog_pwd);
        llRegistPhone = findViewById(R.id.ll_regist_phone);
        etRegistPhone = findViewById(R.id.et_regist_phone);
        etRegistYzm = findViewById(R.id.et_regist_yzm);
        tvGetcode = findViewById(R.id.tv_getcode);
        llRegistPwd = findViewById(R.id.ll_regist_pwd);
        etRegistPwd = findViewById(R.id.et_regist_pwd);
        llRegistSubpwd = findViewById(R.id.ll_regist_subpwd);
        etRegistSubpwd = findViewById(R.id.et_regist_subpwd);
        llRegistYqm = findViewById(R.id.ll_regist_yqm);
        etRegistYqm = findViewById(R.id.et_regist_yqm);
        tvLogin = findViewById(R.id.tv_login);
        tvRegist = findViewById(R.id.tv_regist);
        ivLogin = findViewById(R.id.iv_login);
        ivRegist = findViewById(R.id.iv_regist);
        btLogin = findViewById(R.id.bt_login);
        tvToRegist = findViewById(R.id.tv_to_regist);
        rlRegist = findViewById(R.id.rl_regist);
        tvTip = findViewById(R.id.tv_tip);
    }
}
