package cn.dankal.my.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.widget.GenDialog;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYEARNING;

@Route(path = MYEARNING)
public class MyEarningsActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.TextView tardingList;
    private android.widget.TextView balanceText;
    private android.widget.TextView rankingText;
    private android.widget.TextView titleRank;
    private android.support.v7.widget.RecyclerView rankOnetothreeList;
    private android.support.v7.widget.RecyclerView rankFourtotenList;
    private android.widget.LinearLayout withdrawalLl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_earnings;
    }

    @Override
    protected void initComponents() {
        initView();
        SpannableString spannableString = new SpannableString("收益排名");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.login_btn_bg));
        spannableString.setSpan(colorSpan, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        titleRank.setText(spannableString);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tardingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.TRANSACTIONRECORD).navigation();
            }
        });
        withdrawalLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true){
                    ARouter.getInstance().build(MyProtocol.WITHDRAWAL).navigation();
                }else{
                    GenDialog.CustomBuilder customBuilder = new GenDialog.CustomBuilder(MyEarningsActivity.this);
                    customBuilder.setContent(R.layout.unsetpwd_layout);
                    Dialog dialog1 = customBuilder.create();
                    ImageView close = dialog1.findViewById(R.id.close_img);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                        }
                    });
                    RelativeLayout setPwd = dialog1.findViewById(R.id.toSetPwd_Rl);
                    setPwd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                        }
                    });
                    dialog1.show();
                }

            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        tardingList = (TextView) findViewById(R.id.tarding_list);
        balanceText = (TextView) findViewById(R.id.balance_text);
        rankingText = (TextView) findViewById(R.id.ranking_text);
        titleRank = (TextView) findViewById(R.id.title_rank);
        rankOnetothreeList = (RecyclerView) findViewById(R.id.rank_onetothree_list);
        rankFourtotenList = (RecyclerView) findViewById(R.id.rank_fourtoten_list);
        withdrawalLl = (LinearLayout) findViewById(R.id.withdrawal_ll);
    }
}
