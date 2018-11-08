package cn.dankal.my.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.EDITDATAEN;

@Route(path = EDITDATAEN)
public class EditDataEnActivity extends BaseActivity {
    private android.widget.ImageView backImg;
    private android.widget.TextView titleText;
    private android.widget.EditText etName;
    private android.widget.Button submitBtn;
    private String data;

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
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        titleText = (TextView) findViewById(R.id.title_text);
        etName = (EditText) findViewById(R.id.et_name);
        submitBtn = (Button) findViewById(R.id.submit_btn);
    }
}
