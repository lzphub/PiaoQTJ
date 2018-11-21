package cn.dankal.home.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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

import java.util.ArrayList;
import java.util.List;

import cn.dankal.address.R;
import cn.dankal.basiclib.adapter.ServiceRvAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.callback.DKCallBack;
import cn.dankal.basiclib.bean.ServiceTextBean;
import cn.dankal.basiclib.common.camera.CamerImageBean;
import cn.dankal.basiclib.common.camera.CameraHandler;
import cn.dankal.basiclib.template.personal.ChangeAvatar;
import cn.dankal.basiclib.template.personal.ChangeAvatarImpl;
import cn.dankal.basiclib.util.ImagePathUtil;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
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
    private ChangeAvatar changeAvatar;
    private int phototype=0;

    private ServiceRvAdapter serviceRvAdapter;
    private List<ServiceTextBean> serviceTextBeanList=new ArrayList<>();
    private String type;
    private TextView title;
    private TextView tvPhotograph;
    private TextView tvAlbum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service;
    }

    @Override
    protected void initComponents() {
        initView();
        type= SharedPreferencesUtils.getString(this,"identity","user");
        if(!type.equals("user")){
            title.setText("客服中心");
            tipsText.setText("如客服没有及时回复，请联系1071377555@qq.com");
            contentEt.setHint("请输入您的问题");
            tvAlbum.setText("相册");
            tvPhotograph.setText("拍摄");
        }
        changeAvatar = new ChangeAvatarImpl(this, this);
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
        contentEt.setOnEditorActionListener((v, actionId, event) -> {
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_ENTER:
                    String msg=contentEt.getText().toString().trim();
                    if(StringUtil.isValid(msg)){
                        ServiceTextBean serviceTextBean=new ServiceTextBean();
                        serviceTextBean.setType(1);
                        serviceTextBean.setSend_text(msg);
                        serviceTextBeanList.add(serviceTextBean);
                        serviceRvAdapter.update(serviceTextBeanList);
                        contentEt.setText("");
                        chatRv.scrollToPosition(serviceTextBeanList.size()-1);
                    }else{
                    }
                    break;
            }
            return true;
        });
        serviceRvAdapter=new ServiceRvAdapter(serviceTextBeanList,ServiceActivity.this);
        chatRv.setAdapter(serviceRvAdapter);
        addToCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraHandler cameraHandler=new CameraHandler(ServiceActivity.this);
                changeAvatar.checkPermission(cameraHandler, () -> {
                    cameraHandler.takePhoto();
                    phototype=0;
                });
            }
        });
        addToAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraHandler cameraHandler=new CameraHandler(ServiceActivity.this);
                changeAvatar.checkPermission(cameraHandler, () -> {
                    cameraHandler.pickPhoto();
                    phototype=1;
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            changeAvatar.onChatPickPhoto(chatRv,serviceRvAdapter,serviceTextBeanList,requestCode,resultCode,data);
        }
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

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatRv.setLayoutManager(linearLayoutManager);
        title = (TextView) findViewById(R.id.title);
        tvPhotograph = (TextView) findViewById(R.id.tv_photograph);
        tvAlbum = (TextView) findViewById(R.id.tv_album);
    }
}
