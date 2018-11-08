package cn.dankal.home.activity;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.dankal.address.R;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.util.Logger;
import retrofit2.http.PATCH;

import static cn.dankal.basiclib.protocol.HomeProtocol.SERVICE;

@Route(path = SERVICE)
public class ServiceActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.TextView tipsText;
    private android.widget.EditText contentEt;
    private android.widget.ImageView addImg;
    private android.widget.LinearLayout addToLl;
    private android.widget.LinearLayout addToCamera;
    private android.widget.LinearLayout addToAlbum;
    private android.support.v7.widget.RecyclerView chatRv;
    private android.widget.RelativeLayout etRl;
    final private static int KeyboardHeightLimit = 200;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service;
    }

    @Override
    protected void initComponents() {
        initView();
        backImg.setOnClickListener(v -> finish());
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addToLl.getVisibility()==View.GONE){
                    addImg.setImageResource(R.mipmap.ic_customerservice_close);
                    addToLl.setVisibility(View.VISIBLE);
                }else{
                    addImg.setImageResource(R.mipmap.ic_customerservice_addto);
                    addToLl.setVisibility(View.GONE);
                }
            }
        });
        etRl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                etRl.getWindowVisibleDisplayFrame(r);
                final int screenHeight = etRl.getRootView().getHeight();
                final int keyboardHeight = screenHeight - (r.bottom);

                if (keyboardHeight > KeyboardHeightLimit) {
                    RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) etRl.getLayoutParams();
                    layoutParams.setMargins(0,0,0,keyboardHeight);
                    etRl.setLayoutParams(layoutParams);
                }else{
                    RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) etRl.getLayoutParams();
                    layoutParams.setMargins(0,0,0,7);
                    etRl.setLayoutParams(layoutParams);
                }
            }
        });
        contentEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        tipsText = (TextView) findViewById(R.id.tips_text);
        contentEt = (EditText) findViewById(R.id.content_et);
        addImg = (ImageView) findViewById(R.id.add_img);
        addToLl = (LinearLayout) findViewById(R.id.add_to_ll);
        addToCamera = (LinearLayout) findViewById(R.id.add_to_camera);
        addToAlbum = (LinearLayout) findViewById(R.id.add_to_album);
        chatRv = (RecyclerView) findViewById(R.id.chat_rv);
        etRl = (RelativeLayout) findViewById(R.id.et_rl);
    }
}
