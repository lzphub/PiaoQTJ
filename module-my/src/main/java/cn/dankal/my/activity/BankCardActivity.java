package cn.dankal.my.activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.basiclib.ResultCode;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.ActivityUtils;
import cn.dankal.basiclib.widget.GenDialog;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.BINDCARD;

@Route(path = BINDCARD)
public class BankCardActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.TextView enterCardId;
    private android.widget.EditText cardIdEt;
    private android.widget.EditText nameEt;
    private android.widget.EditText cardIdentityEt;
    private android.widget.EditText phoneNumEt;
    private android.widget.EditText codeVeEt;
    private android.widget.TextView getVeCodeText;
    private android.widget.Button submitBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bank_card;
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
                GenDialog.CustomBuilder2 customBuilder2 = new GenDialog.CustomBuilder2(BankCardActivity.this);
                customBuilder2.setContent(R.layout.finish_dialog);
                Dialog dialog1 = customBuilder2.create();
                if(true){
                    TextView context=dialog1.findViewById(R.id.content_tv);
                    context.setText("银行卡添加成功");
                    Button ok_btn=dialog1.findViewById(R.id.ok_btn);
                    ok_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                            finish();
                            ActivityUtils.finishActivity(WithdrawalActivity.class);
                            ARouter.getInstance().build(MyProtocol.WITHDRAWAL).navigation();
                        }
                    });
                }else{
                    TextView context=dialog1.findViewById(R.id.content_tv);
                    context.setText("银行卡添加失败\n请填写正确信息");
                    ImageView title_img=dialog1.findViewById(R.id.image_title);
                    title_img.setImageResource(R.mipmap.pic_profit_recharge_fail);
                    Button ok_btn=dialog1.findViewById(R.id.ok_btn);
                    ok_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                        }
                    });
                }
                dialog1.show();
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        enterCardId = (TextView) findViewById(R.id.enter_card_id);
        cardIdEt = (EditText) findViewById(R.id.card_id_et);
        nameEt = (EditText) findViewById(R.id.name_et);
        cardIdentityEt = (EditText) findViewById(R.id.card_identity_et);
        phoneNumEt = (EditText) findViewById(R.id.phone_num_et);
        codeVeEt = (EditText) findViewById(R.id.code_ve_et);
        getVeCodeText = (TextView) findViewById(R.id.getVeCode_text);
        submitBtn = (Button) findViewById(R.id.submit_btn);
    }
}
