package cn.dankal.my.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zhihu.matisse.Matisse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import api.MyServiceFactory;
import cn.dankal.basiclib.ResultCode;
import cn.dankal.basiclib.adapter.ImageRvAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.common.qiniu.QiniuUpload;
import cn.dankal.basiclib.common.qiniu.UploadHelper;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.util.UriUtils;
import cn.dankal.basiclib.util.image.CheckImage;
import cn.dankal.basiclib.widget.TipDialog;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.OPINION;
import static cn.dankal.basiclib.widget.TipDialog.Builder.ICON_TYPE_FAIL;

@Route(path = OPINION)
public class FeedBackActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.TextView titleText;
    private android.widget.EditText etOpinion;
    private android.widget.TextView opinionSize;
    private android.widget.ImageView addImg;
    private android.support.v7.widget.RecyclerView imgList;
    private android.widget.Button submitBtn;
    private int size=3;
    private List<Uri> result=new ArrayList<>();
    private ImageRvAdapter imageRvAdapter;
    private static String identity=null;
    private TextView title;
    private static List<String> images=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initComponents() {
        initView();
        identity = SharedPreferencesUtils.getString(this, "identity", "enterprise");
        if(identity.equals("user")){
            titleText.setText("HELP AND FEEDBACK");
            title.setText("ADD IMAGE");
            etOpinion.setHint("* WE KNOW HOW TO LISTEN. PLEASE WRITE DOWN YOUR SUGGESTIONS HERE");
            submitBtn.setText("SAVE");
        }
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        etOpinion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SpannableString spannableString=new SpannableString(etOpinion.getText().toString().trim().length()+"/200");
                ForegroundColorSpan colorSpan=new ForegroundColorSpan(getResources().getColor(R.color.login_btn_bg));
                spannableString.setSpan(colorSpan,0,opinionSize.getText().length()-4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                opinionSize.setText(spannableString);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        addImg.setOnClickListener(v -> CheckImage.takePhotoPicker(FeedBackActivity.this,size - result.size()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imgList.setLayoutManager(linearLayoutManager);

        submitBtn.setOnClickListener(v -> {
            if(identity.equals("user")){
                MyServiceFactory.postFeedBack(etOpinion.getText().toString().trim(),images).safeSubscribe(new AbstractDialogSubscriber<String>(FeedBackActivity.this) {
                    @Override
                    public void onNext(String s) {
                        ARouter.getInstance().build(HomeProtocol.SUBMITINTENTION).withString("feedback","feedback").navigation();
                        finish();
                    }
                });
            }else{
                MyServiceFactory.engPostFeedBack(etOpinion.getText().toString().trim(),images).safeSubscribe(new AbstractDialogSubscriber<String>(this) {
                    @Override
                    public void onNext(String s) {
                        ARouter.getInstance().build(HomeProtocol.SUBMITINTENTION).withString("feedback","feedback").navigation();
                        finish();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== ResultCode.CheckImageCode){
            if (data != null) {
                for(int i = 0; i< Matisse.obtainPathResult(data).size(); i++){
                    result .add( Matisse.obtainResult(data).get(i));
                }
                if(result.size()==size){
                    addImg.setVisibility(View.INVISIBLE);
                }
                for(int i=0;i<result.size();i++){
                    uploadQiniu(result.get(i),this);
                }
                imageRvAdapter=new ImageRvAdapter(this,result);
                imgList.setAdapter(imageRvAdapter);
                imageRvAdapter.setOnClickListener(new ImageRvAdapter.OnClickListener() {
                    @Override
                    public void OnClick(int pos) {
                        result.remove(pos);
                        imageRvAdapter.UpData(result);
                        addImg.setVisibility(View.VISIBLE);
                    }
                });
            }
        }
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        titleText = (TextView) findViewById(R.id.title_text);
        etOpinion = (EditText) findViewById(R.id.et_opinion);
        opinionSize = (TextView) findViewById(R.id.opinion_size);
        addImg = (ImageView) findViewById(R.id.add_img);
        imgList = (RecyclerView) findViewById(R.id.img_list);
        submitBtn = (Button) findViewById(R.id.submit_btn);
        title = (TextView) findViewById(R.id.title);
    }

    //图片上传至七牛
    public static void uploadQiniu(Uri uri,Context context){
        final String[] path = {null};
        TipDialog loadingDialog;
//        final File tempFile = new File(uri.getPath());

        TipDialog.Builder builder = new TipDialog.Builder(context);
        if(identity.equals("user")){
            loadingDialog = builder
                    .setIconType(TipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("uploading").create();
        }else{
            loadingDialog = builder
                    .setIconType(TipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("正在上传").create();
        }
        loadingDialog.show();

        boolean b = UriUtils.getPath(context, uri) == null;

        new UploadHelper().uploadQiniuPic(new QiniuUpload.UploadListener() {
            @Override
            public void onSucess(String localPath, String key) {
                loadingDialog.dismiss();
                if(identity.equals("user")){
                    TipDialog dialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_SUCCESS)
                            .setTipWord("Uploaded successfully")
                            .create(2000);
                    dialog.show();
                    dialog.dismiss();
                }else{
                    TipDialog dialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_SUCCESS)
                            .setTipWord("上传成功,请等待审核")
                            .create(2000);
                    dialog.show();
                    dialog.dismiss();
                }

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
                TipDialog dialog = builder.setIconType(ICON_TYPE_FAIL)
                        .setTipWord("上传失败")
                        .create(2000);
                dialog.show();
                dialog.dismiss();
            }
        }, b ? uri.getPath() : UriUtils.getPath(context, uri));
    }
}
