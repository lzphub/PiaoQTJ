package cn.dankal.home.activity;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import api.MyServiceFactory;
import cn.dankal.address.R;
import cn.dankal.basiclib.adapter.ServiceRvAdapter;
import cn.dankal.basiclib.api.MyService;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.callback.DKCallBack;
import cn.dankal.basiclib.base.recyclerview.SmoothScrollLayoutManager;
import cn.dankal.basiclib.bean.ChatBean;
import cn.dankal.basiclib.bean.PersonalData_EnBean;
import cn.dankal.basiclib.bean.PersonalData_EngineerBean;
import cn.dankal.basiclib.bean.ServiceTextBean;
import cn.dankal.basiclib.common.camera.CamerImageBean;
import cn.dankal.basiclib.common.camera.CameraHandler;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.template.personal.ChangeAvatar;
import cn.dankal.basiclib.template.personal.ChangeAvatarImpl;
import cn.dankal.basiclib.util.ImagePathUtil;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.widget.swipetoloadlayout.OnRefreshListener;
import cn.dankal.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout;
import cn.dankal.home.persenter.ServiceContact;
import cn.dankal.home.persenter.ServicePersenter;
import retrofit2.http.PATCH;

import static cn.dankal.basiclib.protocol.HomeProtocol.SERVICE;

@Route(path = SERVICE)
public class ServiceActivity extends BaseActivity implements ServiceContact.pcview {

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

    private ServiceRvAdapter serviceRvAdapter;
    private List<ChatBean.DataBean> serviceTextBeanList = new ArrayList<>();
    private String type;
    private TextView title;
    private TextView tvPhotograph;
    private TextView tvAlbum;
    private ServicePersenter servicePersenter;
    private String picurl;
    private cn.dankal.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout swipeToloadLayout;
    private int page_index = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service;
    }

    @Override
    protected void initComponents() {
        initView();
        type = SharedPreferencesUtils.getString(this, "identity", "user");
        getPersonalData();
        servicePersenter = new ServicePersenter();
        servicePersenter.attachView(this);
        if ("user".equals(type)) {
            servicePersenter.getUserMsgRecord("1", "30");
        } else {
            servicePersenter.getMsgRecord("1", "30");
        }

        if (!type.equals("user")) {
            title.setText("客服中心");
            tipsText.setText("如客服没有及时回复，请联系1071377555@qq.com");
            contentEt.setHint("请输入您的问题");
            tvAlbum.setText("相册");
            tvPhotograph.setText("拍摄");
        }
        changeAvatar = new ChangeAvatarImpl(this, this);
        backImg.setOnClickListener(v -> finish());
        addImg.setOnClickListener(v -> {
            if (addToLl.getVisibility() == View.GONE) {
                addImg.setImageResource(R.mipmap.ic_customerservice_close);
                addToLl.setVisibility(View.VISIBLE);
            } else {
                addImg.setImageResource(R.mipmap.ic_customerservice_addto);
                addToLl.setVisibility(View.GONE);
            }
        });
        etRl.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            etRl.getWindowVisibleDisplayFrame(r);
            final int screenHeight = etRl.getRootView().getHeight();
            final int keyboardHeight = screenHeight - (r.bottom);

            if (keyboardHeight > KeyboardHeightLimit) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) etRl.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, keyboardHeight);
                etRl.setLayoutParams(layoutParams);
                if (!chatRv.canScrollVertically(1)) {
                    chatRv.scrollToPosition(serviceRvAdapter.getItemCount() - 1);
                }
            } else {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) etRl.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 7);
                etRl.setLayoutParams(layoutParams);
            }
        });
        contentEt.setOnEditorActionListener((v, actionId, event) -> {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_ENTER:
                    String msg = contentEt.getText().toString().trim();
                    if (StringUtil.isValid(msg)) {
                        ChatBean.DataBean serviceTextBean = new ChatBean.DataBean();
                        serviceTextBean.setType(1);
                        serviceTextBean.setContent(msg);
                        serviceRvAdapter.addSendData(serviceTextBean, picurl);
                        contentEt.setText("");
                        if (type.equals("user")) {
                            servicePersenter.userSendMsg(msg, 1);
                        } else {
                            servicePersenter.sendMsg(msg, 1);
                        }
                        chatRv.smoothScrollToPosition(serviceRvAdapter.getItemCount() - 1);
                    }
                    break;
            }
            return true;
        });
        addToCamera.setOnClickListener(v -> {
            CameraHandler cameraHandler = new CameraHandler(ServiceActivity.this);
            changeAvatar.checkPermission(cameraHandler, () -> {
                cameraHandler.takePhoto();
            });
        });
        addToAlbum.setOnClickListener(v -> {
            CameraHandler cameraHandler = new CameraHandler(ServiceActivity.this);
            changeAvatar.checkPermission(cameraHandler, () -> {
                cameraHandler.pickPhoto();
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            changeAvatar.onChatPickPhoto(this, picurl, chatRv, serviceRvAdapter, serviceTextBeanList, requestCode, resultCode, data);
        }
    }

    private void getPersonalData() {
        if (type.equals("user")) {
            MyServiceFactory.getUserData().safeSubscribe(new AbstractDialogSubscriber<PersonalData_EnBean>(this) {
                @Override
                public void onNext(PersonalData_EnBean personalData_enBean) {
                    picurl = personalData_enBean.getAvatar();
                }
            });
        } else {
            MyServiceFactory.getEngineerData().safeSubscribe(new AbstractDialogSubscriber<PersonalData_EngineerBean>(this) {
                @Override
                public void onNext(PersonalData_EngineerBean personalData_engineerBean) {
                    picurl = personalData_engineerBean.getAvatar();
                }
            });
        }
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        tipsText = findViewById(R.id.tips_text);
        contentEt = findViewById(R.id.content_et);
        addImg = findViewById(R.id.add_img);
        addToLl = findViewById(R.id.add_to_ll);
        addToCamera = findViewById(R.id.add_to_camera);
        addToAlbum = findViewById(R.id.add_to_album);
        chatRv = findViewById(R.id.swipe_target);
        etRl = findViewById(R.id.et_rl);

        SmoothScrollLayoutManager linearLayoutManager = new SmoothScrollLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatRv.setLayoutManager(linearLayoutManager);
        title = findViewById(R.id.title);
        tvPhotograph = findViewById(R.id.tv_photograph);
        tvAlbum = findViewById(R.id.tv_album);
        swipeToloadLayout = findViewById(R.id.swipe_toload_layout);
        swipeToloadLayout.setOnRefreshListener(() -> {
            page_index++;
            servicePersenter.addmore(page_index + "", "30");
        });
        serviceRvAdapter = new ServiceRvAdapter(serviceTextBeanList, ServiceActivity.this);
        chatRv.setAdapter(serviceRvAdapter);
    }

    @Override
    public void sendMsgSuccess(int type) {
    }

    @Override
    public void sendMsgFail(int type) {
    }

    @Override
    public void getMsgRecord(List<ChatBean.DataBean> dataBean, boolean isLastPage) {
        serviceRvAdapter.update(dataBean, picurl);
        chatRv.scrollToPosition(dataBean.size() - 1);
        serviceTextBeanList = dataBean;
        if (isLastPage) {
            swipeToloadLayout.setRefreshEnabled(false);
        }
    }

    @Override
    public void addmoreSuccess(List<ChatBean.DataBean> dataBean, boolean isLastPage) {
        serviceRvAdapter.addmore(dataBean, picurl);
        swipeToloadLayout.setRefreshing(false);
        if (isLastPage) {
            swipeToloadLayout.setRefreshEnabled(false);
        }
        chatRv.scrollToPosition(dataBean.size());
    }

    @Override
    public void getUserMsgRecord(List<ChatBean.DataBean> dataBean, boolean isLastPage) {
        serviceRvAdapter.update(dataBean, picurl);
        chatRv.scrollToPosition(dataBean.size() - 1);
        serviceTextBeanList = dataBean;
        if (isLastPage) {
            swipeToloadLayout.setRefreshEnabled(false);
        }
    }

    @Override
    public void userAddMoreSuccess(List<ChatBean.DataBean> dataBean, boolean isLastPage) {
        serviceRvAdapter.addmore(dataBean, picurl);
        swipeToloadLayout.setRefreshing(false);
        if (isLastPage) {
            swipeToloadLayout.setRefreshEnabled(false);
        }
        chatRv.scrollToPosition(dataBean.size());
    }
}
