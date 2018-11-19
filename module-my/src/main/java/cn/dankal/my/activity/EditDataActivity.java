package cn.dankal.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.ActivityUtils;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.EDITDATA;

@Route(path = EDITDATA)
public class EditDataActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.EditText etName;
    private android.widget.Button submitBtn;
    private int type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_data;
    }

    @Override
    protected void initComponents() {
        initView();
        type=getIntent().getIntExtra("datatype",1);
        if(type==2){
            etName.setHint(R.string.hiht);
        }
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringUtil.isValid(etName.getText().toString().trim())){
                    if(type==1){
                        ARouter.getInstance().build(MyProtocol.PERSONALDATA).withString("name",etName.getText().toString().trim()).navigation();
                    }else{
                        ARouter.getInstance().build(MyProtocol.PERSONALDATA).withString("skills",etName.getText().toString().trim()).navigation();
                    }
                    finish();
                    ActivityUtils.finishActivity(PersonalDataActivity.class);
                }else{
                    if(type==1){
                        ToastUtils.showShort("姓名不能为空");
                    }else{
                        ToastUtils.showShort("技能不能为空");
                    }
                }
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        etName = (EditText) findViewById(R.id.et_name);
        submitBtn = (Button) findViewById(R.id.submit_btn);
    }
}
