package cn.dankal.my.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.ResultCode;
import cn.dankal.basiclib.adapter.ImageRvAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.util.image.CheckImage;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.OPINION;

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initComponents() {
        initView();
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
                spannableString.setSpan(colorSpan,0,opinionSize.getText().length()-3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                opinionSize.setText(spannableString);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckImage.takePhotoPicker(FeedBackActivity.this,size - result.size());
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imgList.setLayoutManager(linearLayoutManager);
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
    }

}
