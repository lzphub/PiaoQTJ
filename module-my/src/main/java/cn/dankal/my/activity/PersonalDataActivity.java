package cn.dankal.my.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.util.image.CheckImage;
import cn.dankal.basiclib.util.image.MygildeEngine;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_data;
    }

    @Override
    protected void initComponents() {
        initView();
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
                ARouter.getInstance().build(MyProtocol.EDITDATA).withInt("type", 1).navigation();
            }
        });
        skillsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.EDITDATA).withInt("type", 2).navigation();
            }
        });
        headPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckImage.takePhotoPicker(PersonalDataActivity.this, 1);
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        picRl = (RelativeLayout) findViewById(R.id.pic_rl);
        headPic = (ImageView) findViewById(R.id.head_pic);
        nameRl = (RelativeLayout) findViewById(R.id.name_rl);
        nameText = (TextView) findViewById(R.id.name_text);
        skillsRl = (RelativeLayout) findViewById(R.id.skills_rl);
        skillsText = (TextView) findViewById(R.id.skills_text);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2305) {
            if (data != null) {
                List<Uri> result = Matisse.obtainResult(data);
            }
        }
    }
}
