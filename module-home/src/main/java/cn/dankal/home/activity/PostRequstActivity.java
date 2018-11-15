package cn.dankal.home.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import api.HomeServiceFactory;
import cn.dankal.address.R;
import cn.dankal.basiclib.ResultCode;
import cn.dankal.basiclib.adapter.ImageRvAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.callback.DKCallBackObject;
import cn.dankal.basiclib.bean.PostRequestBean;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.util.image.CheckImage;
import cn.dankal.basiclib.widget.TimeDialog;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static cn.dankal.basiclib.protocol.HomeProtocol.POSTREQUEST;

@Route(path = POSTREQUEST)
public class PostRequstActivity extends BaseActivity {
    private android.widget.Button submitBtn;
    private android.widget.ImageView colseImg;
    private android.widget.TextView titleThe;
    private android.widget.TextView titleSize;
    private android.widget.EditText titleEt;
    private android.widget.ImageView addImg;
    private android.support.v7.widget.RecyclerView addImgRv;
    private android.widget.TextView periodText;
    private android.widget.EditText periodStart;
    private android.widget.EditText periodEnd;
    private android.widget.TextView priceText;
    private android.widget.LinearLayout priceMinLl;
    private android.widget.EditText priceMin;
    private android.widget.LinearLayout priceMaxLl;
    private android.widget.EditText priceMax;
    private android.widget.EditText contentEt;
    private int size = 3;
    private List<Uri> result = new ArrayList<>();
    private ImageRvAdapter imageRvAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_request;
    }

    @Override
    protected void initComponents() {
        initView();
        SpannableString spannableString = new SpannableString("POST REQUEST");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.home_green));
        spannableString.setSpan(colorSpan, 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        titleThe.setText(spannableString);

        colseImg.setOnClickListener(v -> finish());
        periodEnd.setInputType(InputType.TYPE_NULL);
        periodStart.setInputType(InputType.TYPE_NULL);

        periodStart.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                TimeDialog timeDialog = new TimeDialog();
                timeDialog.show(getSupportFragmentManager(), "timeDialog");
                timeDialog.setListener(time -> periodStart.setText(time));
            }
        });
        periodEnd.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                TimeDialog timeDialog = new TimeDialog();
                timeDialog.show(getSupportFragmentManager(), "timeDialog");
                timeDialog.setListener(time -> periodStart.setText(time));
            }
        });
        periodStart.setOnClickListener(v -> {
            TimeDialog timeDialog = new TimeDialog();
            timeDialog.show(getSupportFragmentManager(), "timeDialog");
            timeDialog.setListener(time -> periodStart.setText(time));
        });
        periodEnd.setOnClickListener(v -> {
            TimeDialog timeDialog = new TimeDialog();
            timeDialog.show(getSupportFragmentManager(), "timeDialog");
            timeDialog.setListener(time -> periodEnd.setText(time));
        });

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckImage.takePhotoPicker(PostRequstActivity.this, size - result.size());
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        addImgRv.setLayoutManager(linearLayoutManager);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEt.getText().toString().trim();
                String content = contentEt.getText().toString().trim();
                String start_price = priceMin.getText().toString().trim();
                String end_price = priceMax.getText().toString().trim();
                String start_date = periodStart.getText().toString().trim();
                String end_date = periodEnd.getText().toString().trim();
                if (title == null) {
                    ToastUtils.showShort("Title is mandatory");
                } else if (content.length() < 15) {
                    ToastUtils.showShort("The length of description cannot be less than 15");
                } else {
                    PostRequestBean postRequestBean = new PostRequestBean();
                    postRequestBean.setTitle(title);
                    postRequestBean.setDescription(content);
                    postRequestBean.setStart_date(start_date);
                    postRequestBean.setEnd_date(end_date);
                    postRequestBean.setStart_price(start_price);
                    postRequestBean.setEnd_price(end_price);
                    HomeServiceFactory.postRequest(postRequestBean).safeSubscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String s) {
                            ToastUtils.showShort("Release success");
                            finish();
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showShort(e + "");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                }
            }
        });
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
                imageRvAdapter = new ImageRvAdapter(this, result);
                addImgRv.setAdapter(imageRvAdapter);
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
        submitBtn = findViewById(R.id.submit_btn);
        colseImg = findViewById(R.id.colse_img);
        titleThe = findViewById(R.id.title_the);
        titleSize = findViewById(R.id.title_size);
        titleEt = findViewById(R.id.title_et);
        addImg = findViewById(R.id.add_img);
        addImgRv = findViewById(R.id.add_img_Rv);
        periodText = findViewById(R.id.period_text);
        periodStart = findViewById(R.id.period_start);
        periodEnd = findViewById(R.id.period_end);
        priceText = findViewById(R.id.price_text);
        priceMinLl = findViewById(R.id.price_min_ll);
        priceMin = findViewById(R.id.price_min);
        priceMaxLl = findViewById(R.id.price_max_ll);
        priceMax = findViewById(R.id.price_max);
        contentEt = findViewById(R.id.content_et);
    }
}
