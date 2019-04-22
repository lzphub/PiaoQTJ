package cn.xunzi.home.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.xunzi.address.R;
import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.api.BaseApi;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.util.QRCodeUtil;

import static cn.xunzi.basiclib.protocol.HomeProtocol.TUIGUANGAM;

@Route(path = TUIGUANGAM)
public class TuiGuangActivity extends BaseActivity {
    private ImageView ivBack;
    private ImageView ivErweima;
    private TextView tvYaoqingma;
    private String url=BaseApi.BASE_URL+"user/info/registerByWap/";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_tui_guang;
    }

    @Override
    protected void initComponents() {
        initView();
        ivBack.setOnClickListener(view -> finish());

        tvYaoqingma.setText(XZUserManager.getUserInfo().getReferCode());

        tvYaoqingma.setOnLongClickListener(view -> {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(tvYaoqingma.getText());
            Toast.makeText(TuiGuangActivity.this, "已复制到粘贴板", Toast.LENGTH_SHORT).show();
            return false;
        });

        makeErweimaMethod(ivErweima);
    }

    //点击生成带图片的二维码
    public void makeErweimaMethod(View v) {
        String aimContent=url+tvYaoqingma.getText().toString();
        Create2QR2(aimContent,ivErweima);
    }

    //生成二维码的方法
    private void Create2QR2(String urls,ImageView imageView) {
        String uri = urls;
        int mScreenWidth = 0;
        Bitmap bitmap;
        try {
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            mScreenWidth = dm.widthPixels;

            bitmap = QRCodeUtil.createQRImage(uri, mScreenWidth,
                    BitmapFactory.decodeResource(getResources(), R.mipmap.logo));//自己写的方法

            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivErweima = findViewById(R.id.iv_erweima);
        tvYaoqingma = findViewById(R.id.tv_yaoqingma);
    }
}
