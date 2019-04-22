package cn.xunzi.user.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import api.UserServiceFactory;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.bean.PostBean;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.ToastUtils;
import cn.xunzi.user.R;
import cn.xunzi.user.presenter.LoginPresenter;

import static cn.xunzi.basiclib.protocol.LoginProtocol.FORGETPWD;

@Route(path = FORGETPWD)
public class ForPwdActivity extends BaseActivity {

    private ImageView imBack;
    private EditText etPhone;
    private Button tvGetCode;
    private EditText etCode;
    private EditText etPwd;
    private EditText etSubPwd;
    private Button btResPwd;

    private LoginPresenter loginPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_for_pwd;
    }

    @Override
    protected void initComponents() {
        initView();
        imBack.setOnClickListener(view -> finish());

        tvGetCode.setOnClickListener(view -> {
            loginPresenter=new LoginPresenter(ForPwdActivity.this);
            loginPresenter.getCode(etPhone.getText().toString().trim(),tvGetCode,"2");
        });

        btResPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tel=etPhone.getText().toString().trim();
                String code=etCode.getText().toString().trim();
                String pwd=etPwd.getText().toString().trim();
                String subpwd=etSubPwd.getText().toString().trim();
                if(tel.isEmpty()){
                    ToastUtils.showShort("请输入手机号");
                    return;
                }
                if(code.isEmpty()){
                    ToastUtils.showShort("请输入验证码");
                    return;
                }
                if(pwd.isEmpty()||subpwd.isEmpty()){
                    ToastUtils.showShort("请输入密码");
                    return;
                }
                if(pwd.length()<6){
                    ToastUtils.showShort("请输入符合标准的密码");
                    return;
                }
                if(!pwd.equals(subpwd)){
                    ToastUtils.showShort("请确认两次输入密码相同");
                    return;
                }
                UserServiceFactory.forpwd(tel,code,pwd).safeSubscribe(new AbstractDialogSubscriber<PostBean>(ForPwdActivity.this) {
                    @Override
                    public void onNext(PostBean postBean) {
                        if(postBean.getCode().equals("200")){
                            finish();
                            ToastUtils.showShort("密码修改成功,请登录");
                        }else{
                            ToastUtils.showShort(postBean.getMsg());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
            }
        });
    }

    private void initView() {
        imBack = findViewById(R.id.im_back);
        etPhone = findViewById(R.id.et_phone);
        tvGetCode = findViewById(R.id.tv_getCode);
        etCode = findViewById(R.id.et_code);
        etPwd = findViewById(R.id.et_pwd);
        etSubPwd = findViewById(R.id.et_sub_pwd);
        btResPwd = findViewById(R.id.bt_res_pwd);
    }
}
