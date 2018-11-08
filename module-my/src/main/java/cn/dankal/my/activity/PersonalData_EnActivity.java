package cn.dankal.my.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.common.camera.CameraHandler;
import cn.dankal.basiclib.common.camera.RequestCodes;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.template.personal.ChangeAvatar;
import cn.dankal.basiclib.template.personal.ChangeAvatarImpl;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.widget.CircleImageView;
import cn.dankal.basiclib.widget.wheelview.WheelView;
import cn.dankal.basiclib.widget.wheelview.adapters.ArrayWheelAdapter;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.PERSONALDATAEN;

@Route(path = PERSONALDATAEN)
public class PersonalData_EnActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.RelativeLayout picRl;
    private CircleImageView headPic;
    private android.widget.RelativeLayout nameRl;
    private android.widget.TextView nameText;
    private android.widget.RelativeLayout ageRl;
    private android.widget.TextView ageText;
    private android.widget.RelativeLayout contactRl;
    private android.widget.TextView contactText;
    private android.widget.RelativeLayout eMailRl;
    private android.widget.TextView eMailText;
    private android.widget.RelativeLayout companyRl;
    private android.widget.TextView companyText;
    private android.widget.RelativeLayout countryRl;
    private android.widget.TextView countryText;
    private android.widget.RelativeLayout positionRl;
    private android.widget.TextView positionText;
    private String[] positions = {"IMPORTER", "WHOLESALERS", "RETAILERS", "DESIGNER", "PERSONAL", "OTHER"};
    private List<String> positionList = new ArrayList<>();
    private int itemcount = 0;
    private Uri imguri;
    private ChangeAvatar changeAvatar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_data__en;
    }

    @Override
    protected void initComponents() {
        initView();
        Glide.with(this)
                .load("http://cdn.duitang.com/uploads/item/201408/28/20140828142218_PS4fi.thumb.700_0.png")
                .into(headPic);
        picRl.setOnClickListener(v -> initBottomDialog());
        positionRl.setOnClickListener(v -> initPositionDialog());
        ageRl.setOnClickListener(v -> initAgeDialog());
        countryRl.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.PICKCOUNTRIES).navigation());
        nameRl.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.EDITDATAEN).withString("data","name").navigation());
        contactRl.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.EDITDATAEN).withString("data","number").navigation());
        eMailRl.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.EDITDATAEN).withString("data","email").navigation());
        companyRl.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.EDITDATAEN).withString("data","company").navigation());

        changeAvatar = new ChangeAvatarImpl(this, this);
        changeAvatar.setIvHead(headPic);
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        picRl = findViewById(R.id.pic_rl);
        headPic = findViewById(R.id.head_pic);
        nameRl = findViewById(R.id.name_rl);
        nameText = findViewById(R.id.name_text);
        ageRl = findViewById(R.id.age_rl);
        ageText = findViewById(R.id.age_text);
        contactRl = findViewById(R.id.contact_rl);
        contactText = findViewById(R.id.contact_text);
        eMailRl = findViewById(R.id.e_mail_rl);
        eMailText = findViewById(R.id.e_mail_text);
        companyRl = findViewById(R.id.company_rl);
        companyText = findViewById(R.id.company_text);
        countryRl = findViewById(R.id.country_rl);
        countryText = findViewById(R.id.country_text);
        positionRl = findViewById(R.id.position_rl);
        positionText = findViewById(R.id.position_text);
    }

    private void initBottomDialog() {
        /*
        * 没有裁剪！！
        * */
        changeAvatar.checkPermission(new CameraHandler(this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            changeAvatar.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initPositionDialog() {
        if (positionList != null) {
            positionList.clear();
        }
        for (int i = 0; i < positions.length; i++) {
            positionList.add(positions[i]);
        }
        BottomSheetDialog sheetDialog = new BottomSheetDialog(this);
        sheetDialog.setContentView(R.layout.dialog_pick_position);
        sheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(android.R.color.transparent));
        sheetDialog.show();
        TextView cancel = sheetDialog.getDelegate().findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(v -> sheetDialog.dismiss());
        WheelPicker position = sheetDialog.getDelegate().findViewById(R.id.postion_wheel);
        position.setData(positionList);
        TextView save = sheetDialog.getDelegate().findViewById(R.id.tv_save);
        position.setSelectedItemPosition(itemcount);
        position.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolled(int offset) {
            }

            @Override
            public void onWheelSelected(int position) {
                itemcount = position;
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
            }
        });
        save.setOnClickListener(v -> {
            positionText.setText(positionList.get(itemcount));
            sheetDialog.dismiss();
        });
    }

    private void initAgeDialog() {
        if (positionList != null) {
            positionList.clear();
        }
        for (int i = 1; i < 100; i++) {
            positionList.add(i + "");
        }
        BottomSheetDialog sheetDialog = new BottomSheetDialog(this);
        sheetDialog.setContentView(R.layout.dialog_pick_position);
        sheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(android.R.color.transparent));
        sheetDialog.show();
        TextView title = sheetDialog.getDelegate().findViewById(R.id.dialog_title);
        title.setText("AGE");
        TextView cancel = sheetDialog.getDelegate().findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(v -> sheetDialog.dismiss());
        WheelPicker position = sheetDialog.getDelegate().findViewById(R.id.postion_wheel);
        position.setData(positionList);
        TextView save = sheetDialog.getDelegate().findViewById(R.id.tv_save);
        position.setSelectedItemPosition(itemcount);
        position.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolled(int offset) {
            }

            @Override
            public void onWheelSelected(int position) {
                itemcount = position;
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
            }
        });
        save.setOnClickListener(v -> {
            ageText.setText(positionList.get(itemcount));
            sheetDialog.dismiss();
        });
    }
}
