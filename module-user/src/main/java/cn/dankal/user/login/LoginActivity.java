package cn.dankal.user.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.user.R;

import static cn.dankal.basiclib.protocol.LoginProtocol.LOGIN;

@Route(path = LOGIN)
public class LoginActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initComponents() {

    }

}
