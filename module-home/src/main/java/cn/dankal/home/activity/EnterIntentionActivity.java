package cn.dankal.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.nio.file.Path;

import api.ProductServiceFactory;
import cn.dankal.address.R;
import cn.dankal.basiclib.DKUserManager;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.ToastUtils;

import static cn.dankal.basiclib.protocol.HomeProtocol.ENTERINTEN;

@Route(path = ENTERINTEN)
public class EnterIntentionActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.Button submitBtn;
    private android.widget.EditText contactsNameEt;
    private android.widget.EditText numberEt;
    private android.widget.EditText emailEt;
    private android.widget.EditText intentionEt;
    private String uuid;
    private TextView titleText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_enter_intention;
    }

    @Override
    protected void initComponents() {
        initView();
        uuid=getIntent().getStringExtra("uuid");
        backImg.setOnClickListener(v -> finish());
        submitBtn.setOnClickListener(v -> {

            String name=contactsNameEt.getText().toString().trim();
            String number=numberEt.getText().toString().trim();
            String email= emailEt.getText().toString().trim();
            String content=intentionEt.getText().toString().trim();



            if(name.isEmpty()){
                ToastUtils.showShort("The name cannot be empty");
                return;
            }
            if(number.isEmpty()){
                ToastUtils.showShort("The number cannot be empty");
                return;
            }

            if(email.isEmpty()){
                ToastUtils.showShort("The email cannot be empty");
                return;
            }


            if(content.isEmpty()){
                ToastUtils.showShort("The detailed intention cannot be empty");
                return;
            }

           postIntention(uuid,name,number,email,content);
        });
    }

    private void initView() {
        backImg =  findViewById(R.id.back_img);
        numberEt = findViewById(R.id.number_et);
        submitBtn =  findViewById(R.id.submit_btn);
        contactsNameEt = findViewById(R.id.contacts_name_et);
        emailEt =  findViewById(R.id.email_et);
        intentionEt = findViewById(R.id.intention_et);
        titleText = findViewById(R.id.title_text);

        SpannableString spannableString = new SpannableString("POST REQUEST");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.home_green));
        spannableString.setSpan(colorSpan, 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        titleText.setText(spannableString);
    }


    private void postIntention(String uuid,String name,String number,String email,String content){
        ProductServiceFactory.postPurchase(uuid, name, number, email, content).safeSubscribe(new AbstractDialogSubscriber<String>(this) {
            @Override
            public void onNext(String s) {
                ARouter.getInstance().build(HomeProtocol.SUBMITINTENTION).navigation();
                finish();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastUtils.showShort(e.getMessage());
            }
        });
    }
}
