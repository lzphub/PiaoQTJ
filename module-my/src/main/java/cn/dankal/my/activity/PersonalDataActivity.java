package cn.dankal.my.activity;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import api.MyServiceFactory;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.AddressBean;
import cn.dankal.basiclib.bean.PersonalData_EngineerBean;
import cn.dankal.basiclib.bean.PersonalData_EngineerPostBean;
import cn.dankal.basiclib.common.camera.CameraHandler;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.template.personal.ChangeAvatar;
import cn.dankal.basiclib.template.personal.ChangeAvatarImpl;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.util.image.PicUtils;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.PERSONALDATA;

@Route(path = PERSONALDATA)
public class PersonalDataActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.RelativeLayout picRl;
    private android.widget.ImageView headPic;
    private android.widget.RelativeLayout nameRl;
    private android.widget.TextView nameText;
    private android.widget.RelativeLayout skillsRl;
    private android.widget.TextView skillsText;
    private RelativeLayout phoneRl;
    private TextView phoneText;
    private RelativeLayout addressRl;
    private TextView addressText;
    private AddressBean addressBeans;
    private List<String> province_list=new ArrayList<>(),city_list=new ArrayList<>();
    private int provinceCount=0,cityCount=0;
    private PersonalData_EngineerPostBean personalData_engineerPostBean=new PersonalData_EngineerPostBean();
    private ChangeAvatar changeAvatar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_data;
    }

    @Override
    protected void initComponents() {
        initView();
        getEngineerData();
        if(StringUtil.isValid(getIntent().getStringExtra("name"))){
            nameText.setText(getIntent().getStringExtra("name"));
        }
        if(StringUtil.isValid(getIntent().getStringExtra("skills"))){
            skillsText.setText(getIntent().getStringExtra("skills"));
        }
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nameRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.EDITDATA).withInt("datatype", 1).withSerializable("data",personalData_engineerPostBean).navigation();
            }
        });
        skillsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.EDITDATA).withInt("datatype", 2).withSerializable("data",personalData_engineerPostBean).navigation();
            }
        });
        phoneRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.EDITDATA).withInt("datatype", 3).withSerializable("data",personalData_engineerPostBean).navigation();
            }
        });
        headPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 没有裁剪！！
                 */
                changeAvatar.checkPermission(new CameraHandler(PersonalDataActivity.this), null);

            }
        });
        addressRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAddressDialog();
            }
        });
        changeAvatar = new ChangeAvatarImpl(this, this);
        changeAvatar.setIvHead(headPic);
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        picRl = (RelativeLayout) findViewById(R.id.pic_rl);
        headPic = (ImageView) findViewById(R.id.head_pic);
        nameRl = (RelativeLayout) findViewById(R.id.name_rl);
        nameText = (TextView) findViewById(R.id.name_text);
        skillsRl = (RelativeLayout) findViewById(R.id.skills_rl);
        skillsText = (TextView) findViewById(R.id.skills_text);
        phoneRl = (RelativeLayout) findViewById(R.id.phone_rl);
        phoneText = (TextView) findViewById(R.id.phone_text);
        addressRl = (RelativeLayout) findViewById(R.id.address_rl);
        addressText = (TextView) findViewById(R.id.address_text);
    }

    private void initAddressDialog() {
        MyServiceFactory.getGeoInfo().safeSubscribe(new AbstractDialogSubscriber<AddressBean>(this) {
            @Override
            public void onNext(AddressBean addressBean) {
                addressBeans=new AddressBean();
                addressBeans=addressBean;
                if(province_list!=null){
                    province_list.clear();
                }
                if(city_list!=null){
                    city_list.clear();
                }
                provinceCount=0;
                cityCount=0;
                for(int i=0;i<addressBean.getRoot().size();i++){
                    province_list.add(addressBean.getRoot().get(i).getName());
                    for(int n=0;n<addressBean.getRoot().get(0).getChildren().size();n++){
                        city_list.add(addressBean.getRoot().get(0).getChildren().get(n).getName());
                    }
                }
                showDialog();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastUtils.showShort(e+"");
                return;
            }
        });


    }

    private void showDialog(){
        BottomSheetDialog sheetDialog = new BottomSheetDialog(this);
        sheetDialog.setContentView(R.layout.dialog_pick_address);
        sheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(android.R.color.transparent));
        sheetDialog.show();
        TextView cancel = sheetDialog.getDelegate().findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(v -> sheetDialog.dismiss());
        WheelPicker position = sheetDialog.getDelegate().findViewById(R.id.province_wheel);
        position.setData(province_list);
        WheelPicker city=sheetDialog.getDelegate().findViewById(R.id.city_wheel);
        city.setData(city_list);
        TextView save = sheetDialog.getDelegate().findViewById(R.id.tv_save);
        position.setSelectedItemPosition(0);
        city.setSelectedItemPosition(0);
        position.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolled(int offset) {
            }

            @Override
            public void onWheelSelected(int position) {
                provinceCount = position;
                if(city_list!=null){
                    city_list.clear();
                }
                for(int i=0;i<addressBeans.getRoot().get(position%34).getChildren().size();i++){
                    city_list.add(addressBeans.getRoot().get(position%34).getChildren().get(i).getName());
                }
                city.setData(city_list);
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
            }
        });
        city.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolled(int offset) {

            }

            @Override
            public void onWheelSelected(int position) {
                cityCount=position;
            }

            @Override
            public void onWheelScrollStateChanged(int state) {

            }
        });
        save.setOnClickListener(v -> {
            addressText.setText(province_list.get(provinceCount%34)+city_list.get(cityCount%city_list.size()));
            personalData_engineerPostBean.setProvince(province_list.get(provinceCount%34));
            personalData_engineerPostBean.setCity(city_list.get(cityCount%city_list.size()));
            sheetDialog.dismiss();
            postEngineerData();
        });
    }

    //获取数据
    private void getEngineerData(){
        MyServiceFactory.getEngineerData().safeSubscribe(new AbstractDialogSubscriber<PersonalData_EngineerBean>(this) {
            @Override
            public void onNext(PersonalData_EngineerBean personalData_engineerBean) {
                nameText.setText(personalData_engineerBean.getName());
                skillsText.setText(personalData_engineerBean.getCompetence());
                addressText.setText(personalData_engineerBean.getProvince()+personalData_engineerBean.getCity());
                phoneText.setText(personalData_engineerBean.getMobile());
                PicUtils.loadAvatar(personalData_engineerBean.getAvatar(),headPic);
                personalData_engineerPostBean.setCompetence(personalData_engineerBean.getCompetence());
                personalData_engineerPostBean.setName(personalData_engineerBean.getName());
                personalData_engineerPostBean.setAvatar(personalData_engineerBean.getAvatar());
                personalData_engineerPostBean.setProvince(personalData_engineerBean.getProvince());
                personalData_engineerPostBean.setCity(personalData_engineerBean.getCity());
                personalData_engineerBean.setMobile(personalData_engineerBean.getMobile());
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getEngineerData();
    }

    //更新个人信息
    private void postEngineerData(){
        MyServiceFactory.engineerUpdateInfo(personalData_engineerPostBean).safeSubscribe(new AbstractDialogSubscriber<String>(this) {
            @Override
            public void onNext(String s) {
                ToastUtils.showShort("保存成功");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            changeAvatar.onActivityResult2(requestCode, resultCode, data, personalData_engineerPostBean);
        }
    }
}
