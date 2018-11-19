package cn.dankal.my.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import api.MyServiceFactory;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.PersonalData_EnBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.EDITDATAEN;

@Route(path = EDITDATAEN)
public class EditDataEnActivity extends BaseActivity {
    private android.widget.ImageView backImg;
    private android.widget.TextView titleText;
    private android.widget.EditText etName;
    private android.widget.Button submitBtn;
    private String data;
    private PersonalData_EnBean personalData_enBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_data;
    }

    @Override
    protected void initComponents() {
        initView();
        backImg.setOnClickListener(v -> finish());
        titleText.setText("PERSONAL DATA");
        submitBtn.setText("SAVE");

        personalData_enBean= (PersonalData_EnBean) getIntent().getSerializableExtra("bean");
        data = getIntent().getStringExtra("data");
        switch (data) {
            case "name":
                etName.setHint("PLEASE FILL IN YOUR NAME HERE!");
                break;
            case "number":
                etName.setHint("PLEASE FILL IN YOUR NUMBER HERE!");
                break;
            case "email":
                etName.setHint("PLEASE FILL IN YOUR E-MAIL HERE!");
                break;
            case "company":
                etName.setHint("PLEASE FILL IN YOUR COMPANY HERE!");
                break;
        }
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (data) {
                    case "name":
                        personalData_enBean.setName(etName.getText().toString().trim());
                        break;
                    case "number":
                        personalData_enBean.setContact(etName.getText().toString().trim());
                        break;
                    case "email":
                        personalData_enBean.setEmail(etName.getText().toString().trim());
                        break;
                    case "company":
                        personalData_enBean.setCompany(etName.getText().toString().trim());
                        break;
                }
                updata();
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        titleText = (TextView) findViewById(R.id.title_text);
        etName = (EditText) findViewById(R.id.et_name);
        submitBtn = (Button) findViewById(R.id.submit_btn);
    }

    private void updata() {
        MyServiceFactory.updateInfo(personalData_enBean).safeSubscribe(new AbstractDialogSubscriber<String>(this) {
            @Override
            public void onNext(String s) {
                finish();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastUtils.showShort(e+"");
            }
        });
    }
}
