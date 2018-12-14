package cn.dankal.home.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zhihu.matisse.Matisse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import api.HomeServiceFactory;
import cn.dankal.address.R;
import cn.dankal.basiclib.ResultCode;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.common.qiniu.QiniuUpload;
import cn.dankal.basiclib.common.qiniu.UploadHelper;
import cn.dankal.basiclib.exception.LocalException;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.util.UriUtils;
import cn.dankal.basiclib.util.image.CheckImage;
import cn.dankal.basiclib.adapter.ImageRvAdapter;
import cn.dankal.basiclib.widget.TipDialog;

import static cn.dankal.basiclib.protocol.HomeProtocol.HOMERELEASE;
import static cn.dankal.basiclib.widget.TipDialog.Builder.ICON_TYPE_FAIL;

@Route(path = HOMERELEASE)
public class ReleaseIdeaActivity extends BaseActivity {

    private android.widget.Button submitBtn;
    private android.widget.ImageView backImg;
    private android.widget.TextView titleText;
    private android.widget.TextView sizeTitle;
    private android.widget.EditText titleEt;
    private android.widget.ImageView addImg;
    private android.support.v7.widget.RecyclerView imgList;
    private android.widget.TextView sizeDetails;
    private android.widget.EditText detailsEt;
    private List<Uri> result = new ArrayList<>();
    private int size = 5;
    private ImageRvAdapter imageRvAdapter;
    private static List<String> images = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_idea;
    }

    @Override
    protected void initComponents() {
        initView();
        backImg.setOnClickListener(v -> finish());
        titleEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sizeTitle.setText(titleEt.getText().toString().trim().length() + "/50");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        detailsEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sizeDetails.setText(detailsEt.getText().toString().trim().length() + "/2000");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        submitBtn.setOnClickListener(v -> HomeServiceFactory.postidea(titleEt.getText().toString().trim(), detailsEt.getText().toString().trim(), images).safeSubscribe(new AbstractDialogSubscriber<String>(ReleaseIdeaActivity.this) {
            @Override
            public void onNext(String s) {
                ARouter.getInstance().build(HomeProtocol.SUBMITIDEA).navigation();
                finish();
            }

            @Override
            public void onError(Throwable e) {
                dismissLoadingDialog();
                if (e instanceof LocalException) {
                    LocalException exception = (LocalException) e;
                    if (exception.getMsg().equals("title不能为空")) {
                        ToastUtils.showShort("方案标题不能为空");
                        return;
                    }
                    if (exception.getMsg().equals("title长度不符合要求 6,50")) {
                        ToastUtils.showShort("方案标题至少为6个字符");
                        return;
                    }
                    if (exception.getMsg().equals("detail长度不符合要求 15,20000")) {
                        ToastUtils.showShort("方案详情至少为15个字符");
                        return;
                    }
                    if (exception.getMsg().equals("detail不能为空")) {
                        ToastUtils.showShort("方案详情不能为空");
                        return;
                    }
                    else{
                        ToastUtils.showShort(exception.getMsg());
                    }
                }
            }
        }));

        addImg.setOnClickListener(v -> CheckImage.takePhotoPicker(ReleaseIdeaActivity.this, size - result.size()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imgList.setLayoutManager(linearLayoutManager);
    }

    private void initView() {
        submitBtn = findViewById(R.id.submit_btn);
        backImg = findViewById(R.id.back_img);
        titleText = findViewById(R.id.title_text);
        sizeTitle = findViewById(R.id.size_title);
        titleEt = findViewById(R.id.title_et);
        addImg = findViewById(R.id.add_img);
        imgList = findViewById(R.id.img_list);
        sizeDetails = findViewById(R.id.size_details);
        detailsEt = findViewById(R.id.details_et);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ResultCode.CheckImageCode) {
            if (data != null) {
                for (int i = 0; i < Matisse.obtainPathResult(data).size(); i++) {
                    result.add(Matisse.obtainResult(data).get(i));
                }
                if (result.size() == size) {
                    addImg.setVisibility(View.INVISIBLE);
                }
                images = new ArrayList<>();
                for (int i = 0; i < result.size(); i++) {
                    uploadQiniu(result.get(i), this);
                }
                imageRvAdapter = new ImageRvAdapter(this, result);
                imgList.setAdapter(imageRvAdapter);
                imageRvAdapter.setOnClickListener(pos -> {
                    result.remove(pos);
                    images.remove(pos);
                    imageRvAdapter.UpData(result);
                    addImg.setVisibility(View.VISIBLE);
                });
            }
        }
    }

    //图片上传至七牛
    public static void uploadQiniu(Uri uri, Context context) {
        final String[] path = {null};
        TipDialog loadingDialog;

        TipDialog.Builder builder = new TipDialog.Builder(context);
        loadingDialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_LOADING).setTipWord("上传图片中").create();
        loadingDialog.show();

        boolean b = UriUtils.getPath(context, uri) == null;

        new UploadHelper().uploadQiniuPic(new QiniuUpload.UploadListener() {
            @Override
            public void onSucess(String localPath, String key) {
                loadingDialog.dismiss();
                TipDialog dialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_SUCCESS).setTipWord("图片添加成功").create(500);
                dialog.show();
                dialog.dismiss();
                images.add(key);
            }

            @Override
            public void onUpload(double percent) {
                DecimalFormat df = new DecimalFormat("#0.00");
                builder.setTipWord(df.format(percent * 100) + "%").showProgress();
            }

            @Override
            public void onError(String string) {
                ToastUtils.showLong(string);
                loadingDialog.dismiss();
                TipDialog dialog = builder.setIconType(ICON_TYPE_FAIL).setTipWord("上传失败").create(2000);
                dialog.show();
                dialog.dismiss();
            }
        }, b ? uri.getPath() : UriUtils.getPath(context, uri));
    }
}
