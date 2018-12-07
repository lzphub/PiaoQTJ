package cn.dankal.my.activity;

import com.alibaba.android.arouter.facade.annotation.Route;

import api.MyServiceFactory;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.PersonalData_EngineerPostBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.EDITDATA;

/**
 * 工程师编辑个人信息
 */
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
        backImg.setOnClickListener(v -> finish());
        submitBtn.setOnClickListener(v -> {
            switch (type){
                case 1:
                    personalData_engineerPostBean.setName(etName.getText().toString().trim());
                    break;
                case 2:
                    personalData_engineerPostBean.setCompetence(etName.getText().toString().trim());
                    break;
                case 3:
                    personalData_engineerPostBean.setMobile(etName.getText().toString().trim());
                    break;
            }
            postEngineerData();
        });
    }

    private void initView() {
        etName = findViewById(R.id.et_name);
        backImg = findViewById(R.id.back_img);
        submitBtn = findViewById(R.id.submit_btn);
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
