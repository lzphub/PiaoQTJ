package cn.dankal.home.activity;

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

import java.util.ArrayList;
import java.util.List;

import cn.dankal.address.R;
import cn.dankal.basiclib.ResultCode;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.util.image.CheckImage;
import cn.dankal.basiclib.adapter.ImageRvAdapter;

import static cn.dankal.basiclib.protocol.HomeProtocol.CLAIMDEMAND;

@Route(path = CLAIMDEMAND)
public class DemandClaimActivity extends BaseActivity {

    private android.widget.Button submitBtn;
    private android.widget.ImageView backImg;
    private android.widget.TextView demandPrice;
    private android.widget.TextView releaseTime;
    private android.widget.TextView demandTitle;
    private android.widget.TextView demandData;
    private android.widget.ImageView addImg;
    private android.support.v7.widget.RecyclerView imgList;
    private android.widget.TextView sizeDetails;
    private android.widget.EditText detailsEt;
    private List<Uri> result = new ArrayList<>();
    private int size = 5;
    private ImageRvAdapter imageRvAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_demand_claim;
    }

    @Override
    public void initComponents() {
        initView();
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(HomeProtocol.SUBMITIDEA).withInt("type", 2).navigation();
                finish();
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
        releaseTime = (TextView) findViewById(R.id.release_time);
        demandTitle = (TextView) findViewById(R.id.demand_title);
        addImg = (ImageView) findViewById(R.id.add_img);
        imgList = (RecyclerView) findViewById(R.id.img_list);
        sizeDetails = (TextView) findViewById(R.id.size_details);
        detailsEt = (EditText) findViewById(R.id.details_et);
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

}
