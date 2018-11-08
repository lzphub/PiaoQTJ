package cn.dankal.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.address.R;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.HomeProtocol;

import static cn.dankal.basiclib.protocol.HomeProtocol.ENTERINTEN;

@Route(path = ENTERINTEN)
public class EnterIntentionActivity extends BaseActivity {

    private android.widget.LinearLayout titleLl;
    private android.widget.ImageView backImg;
    private android.widget.Button submitBtn;
    private android.widget.TextView titleText;
    private android.widget.EditText contactsNameEt;
    private android.widget.EditText numberEt;
    private android.widget.EditText emailEt;
    private android.widget.EditText intentionEt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_enter_intention;
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
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(HomeProtocol.SUBMITINTENTION).navigation();
                finish();
            }
        });
    }

    private void initView() {
        titleLl =  findViewById(R.id.title_ll);
        backImg =  findViewById(R.id.back_img);
        numberEt = findViewById(R.id.number_et);
        submitBtn =  findViewById(R.id.submit_btn);
        titleText =  findViewById(R.id.title_text);
        contactsNameEt = findViewById(R.id.contacts_name_et);
        emailEt =  findViewById(R.id.email_et);
        intentionEt = findViewById(R.id.intention_et);
    }
}
