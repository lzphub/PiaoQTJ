package cn.dankal.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import api.MyServiceFactory;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.PersonalData_EngineerPostBean;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
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
    private PersonalData_EngineerPostBean personalData_engineerPostBean=new PersonalData_EngineerPostBean();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_data;
    }

    @Override
    protected void initComponents() {
        initView();
        type=getIntent().getIntExtra("datatype",1);
        personalData_engineerPostBean= (PersonalData_EngineerPostBean) getIntent().getSerializableExtra("data");
        if(type==2){
            etName.setHint(R.string.hiht);
        }else if(type==3){
            etName.setHint("请输入您的手机号");
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
                switch (type){
                    case 1:
                        personalData_engineerPostBean.setName(etName.getText().toString().trim());
                        break;
                    case 2:
                        personalData_engineerPostBean.setCompetence(etName.getText().toString().trim());
                        break;
                }
                postEngineerData();
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        etName = (EditText) findViewById(R.id.et_name);
        submitBtn = (Button) findViewById(R.id.submit_btn);
    }

    //更新个人信息
    private void postEngineerData(){
        MyServiceFactory.engineerUpdateInfo(personalData_engineerPostBean).safeSubscribe(new AbstractDialogSubscriber<String>(this) {
            @Override
            public void onNext(String s) {
                ToastUtils.showShort("保存成功");
                finish();
            }
        });
    }
}
