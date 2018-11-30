package cn.dankal.home.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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

import api.HomeServiceFactory;
import cn.dankal.address.R;
import cn.dankal.basiclib.ResultCode;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.DemandListbean;
import cn.dankal.basiclib.common.qiniu.QiniuUpload;
import cn.dankal.basiclib.common.qiniu.UploadHelper;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.util.UriUtils;
import cn.dankal.basiclib.util.image.CheckImage;
import cn.dankal.basiclib.adapter.ImageRvAdapter;
import cn.dankal.basiclib.widget.TipDialog;

import static cn.dankal.basiclib.protocol.HomeProtocol.CLAIMDEMAND;
import static cn.dankal.basiclib.widget.TipDialog.Builder.ICON_TYPE_FAIL;

@Route(path = CLAIMDEMAND)
public class DemandClaimActivity extends BaseActivity {

    private android.widget.Button submitBtn;
    private android.widget.ImageView backImg;
    private android.widget.TextView demandPrice;
    private android.widget.TextView releaseTime;
    private android.widget.TextView demandTitle;
    private android.widget.TextView demandData;
    private TextView demand_content;
    private android.widget.ImageView addImg;
    private android.support.v7.widget.RecyclerView imgList;
    private android.widget.TextView sizeDetails;
    private android.widget.EditText detailsEt;
    private List<Uri> result = new ArrayList<>();
    private int size = 5;
    private ImageRvAdapter imageRvAdapter;
    private static List<String> images=new ArrayList<>();
    private DemandListbean.DataBean dataBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_demand_claim;
    }

    @Override
    public void initComponents() {
        initView();
        dataBean= (DemandListbean.DataBean) getIntent().getSerializableExtra("demandData");
        demandTitle.setText(dataBean.getName());
        demand_content.setText(dataBean.getDesc());
        demandPrice.setText("¥"+dataBean.getStart_price()+"~"+dataBean.getEnd_price());
        releaseTime.setText(dataBean.getCpl_start_date()+"~"+dataBean.getCpl_end_date());
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeServiceFactory.postplan(dataBean.getUuid(),detailsEt.getText().toString().trim(),images).safeSubscribe(new AbstractDialogSubscriber<String>(DemandClaimActivity.this) {
                    @Override
                    public void onNext(String s) {
                        ARouter.getInstance().build(HomeProtocol.SUBMITIDEA).withInt("type", 2).navigation();
                        finish();
                    }
                });

            }
        });

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckImage.takePhotoPicker(DemandClaimActivity.this, 1);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imgList.setLayoutManager(linearLayoutManager);
    }

    private void initView() {
        submitBtn = (Button) findViewById(R.id.submit_btn);
        backImg = (ImageView) findViewById(R.id.back_img);
        demandPrice = (TextView) findViewById(R.id.demand_price);
        releaseTime = (TextView) findViewById(R.id.re_date);
        demandTitle = (TextView) findViewById(R.id.demand_title);
        addImg = (ImageView) findViewById(R.id.add_img);
        imgList = (RecyclerView) findViewById(R.id.img_list);
        sizeDetails = (TextView) findViewById(R.id.size_details);
        detailsEt = (EditText) findViewById(R.id.details_et);
        demand_content=findViewById(R.id.demand_content);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ResultCode.CheckImageCode) {
            if (data != null) {
                for(int i=0;i<Matisse.obtainPathResult(data).size();i++){
                    result .add( Matisse.obtainResult(data).get(i));
                }
                if(result.size()==size){
                    addImg.setVisibility(View.INVISIBLE);
                }
                images=new ArrayList<>();
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


    //图片上传至七牛
    public static void uploadQiniu(Uri uri,Context context){
        final String[] path = {null};
        TipDialog loadingDialog;
//        final File tempFile = new File(uri.getPath());

        TipDialog.Builder builder = new TipDialog.Builder(context);
        loadingDialog = builder
                .setIconType(TipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("图片上传中").create();
        loadingDialog.show();

        boolean b = UriUtils.getPath(context, uri) == null;

        new UploadHelper().uploadQiniuPic(new QiniuUpload.UploadListener() {
            @Override
            public void onSucess(String localPath, String key) {
                loadingDialog.dismiss();
                TipDialog dialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_SUCCESS)
                        .setTipWord("上传成功")
                        .create(1000);
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
                TipDialog dialog = builder.setIconType(ICON_TYPE_FAIL)
                        .setTipWord("上传失败")
                        .create(2000);
                dialog.show();
                dialog.dismiss();
            }
        }, b ? uri.getPath() : UriUtils.getPath(context, uri));
    }
}
