package cn.dankal.my.activity;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import api.MyServiceFactory;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.AboutUsBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.image.PicUtils;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.ABOUTUS;

@Route(path = ABOUTUS)
public class AboutUsActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.TextView usContent;
    private String type;
    private TextView tvTitle;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    CharSequence charSequence = (CharSequence) msg.obj;
                    if (charSequence != null) {
                        usContent.setText(charSequence);
                        usContent.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initComponents() {
        initView();
        type= SharedPreferencesUtils.getString(this,"identity","user");
        backImg.setOnClickListener(v -> finish());
        if(type.equals("user")){
            getData();
            tvTitle.setText("ABOUT US");
        }else{
            engGetData();
        }
    }

    //用户端获取数据
    private void getData(){
        MyServiceFactory.getAboutUs().safeSubscribe(new AbstractDialogSubscriber<AboutUsBean>(this) {
            @Override
            public void onNext(AboutUsBean aboutUsBean) {
                CharSequence charSequence= Html.fromHtml(aboutUsBean.getValue());
                usContent.setText(charSequence);
                usContent.setMovementMethod(LinkMovementMethod.getInstance() );
            }
        });
    }

    //工程师端获取数据
    private void engGetData(){
        MyServiceFactory.engGetAboutus().safeSubscribe(new AbstractDialogSubscriber<AboutUsBean>(this) {
            @Override
            public void onNext(AboutUsBean aboutUsBean) {
                setActivityContent(aboutUsBean.getValue());
            }
        });
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        usContent = findViewById(R.id.us_content);
        tvTitle = findViewById(R.id.tv_title);
    }

    //加载富文本
    private void setActivityContent(final String activityContent) {
        new Thread(() -> {
            Html.ImageGetter imageGetter = source -> {
                Drawable drawable;
                drawable = PicUtils.getImageNetwork(source);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                } else if (drawable == null) {
                    return null;
                }
                return drawable;
            };
            CharSequence charSequence = Html.fromHtml(activityContent.trim(), imageGetter, null);
            Message ms = Message.obtain();
            ms.what = 1;
            ms.obj = charSequence;
            mHandler.sendMessage(ms);
        }).start();
    }
}
