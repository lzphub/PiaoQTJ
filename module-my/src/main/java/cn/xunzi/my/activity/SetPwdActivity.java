package cn.xunzi.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import api.MyServiceFactory;
import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.bean.ChangePwdBean;
import cn.xunzi.basiclib.bean.PostBean;
import cn.xunzi.basiclib.protocol.LoginProtocol;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.ActivityUtils;
import cn.xunzi.basiclib.util.ToastUtils;
import cn.xunzi.setting.R;

import static cn.xunzi.basiclib.protocol.MyProtocol.SETPWD;

@Route(path = SETPWD)
public class SetPwdActivity extends BaseActivity {

    private ImageView imBack;
    private EditText etOldpwd;
    private EditText etPwd;
    private EditText etSubpwd;
    private Button btSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_pwd;
    }

    @Override
    protected void initComponents() {
        initView();
        imBack.setOnClickListener(view -> finish());

        btSubmit.setOnClickListener(view -> {
            if(etOldpwd.getText().toString().trim().isEmpty()){
                ToastUtils.showShort("请输入原密码");
                return;
            }
            if (etPwd.getText().toString().isEmpty() || etSubpwd.getText().toString().isEmpty() || etPwd.getText().toString().trim().length() < 6 || etSubpwd.getText().toString().trim().length() < 6) {
                ToastUtils.showShort("请输入正确的新密码");
                return;
            }
            if (!etPwd.getText().toString().trim().equals(etSubpwd.getText().toString())) {
                ToastUtils.showShort("两次密码不同");
                return;
            }
            MyServiceFactory.updatePassword(XZUserManager.getUserInfo().getId() + "", etOldpwd.getText().toString().trim(),etPwd.getText().toString()).safeSubscribe(new AbstractDialogSubscriber<ChangePwdBean>(SetPwdActivity.this) {
                @Override
                public void onNext(ChangePwdBean postBean) {
                    ToastUtils.showShort(postBean.getDataMap().getMsg());
                    if (postBean.getDataMap().getCode().equals("200")) {
                        XZUserManager.resetUserInfo();
                        ActivityUtils.finishAllActivities();
                        ARouter.getInstance().build(LoginProtocol.USERSLOGIN).navigation();
                        ToastUtils.showShort("修改成功，请重新登录");
                    }
                }
            });
        });

    }

    private void initView() {
        imBack = findViewById(R.id.im_back);
        etOldpwd = findViewById(R.id.et_oldpwd);
        etPwd = findViewById(R.id.et_pwd);
        etSubpwd = findViewById(R.id.et_subpwd);
        btSubmit = findViewById(R.id.bt_submit);
    }
}
