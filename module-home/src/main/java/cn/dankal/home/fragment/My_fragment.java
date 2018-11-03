package cn.dankal.home.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.support.constraint.ConstraintLayout;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.address.R;
import cn.dankal.basiclib.base.fragment.BaseFragment;
import cn.dankal.basiclib.protocol.MyProtocol;

public class My_fragment extends BaseFragment {
    private android.widget.ImageView myNews;
    private android.widget.ImageView headPic;
    private android.widget.TextView myPostion;
    private android.widget.TextView myName;
    private android.support.constraint.ConstraintLayout myWorklist;
    private android.support.constraint.ConstraintLayout myIdea;
    private android.support.constraint.ConstraintLayout myProfit;
    private android.support.constraint.ConstraintLayout customer;
    private android.support.constraint.ConstraintLayout setting;

    @Override
    protected int getLayoutId() {
        return R.layout.my_layout;
    }

    @Override
    protected void initComponents() {

    }

    @Override
    protected void initComponents(View view) {
        initView(view);
        myWorklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.MYWORKLIST).navigation();
            }
        });
        myIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.MYIDEA).navigation();
            }
        });
        myProfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.MYEARNING).navigation();
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.SETTING).navigation();
            }
        });
        myNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.SYSTEMNEWS).navigation();
            }
        });
        headPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.PERSONALDATA).navigation();
            }
        });
    }

    private void initView(View view) {
        myNews = (ImageView) view.findViewById(R.id.my_news);
        headPic = (ImageView) view.findViewById(R.id.head_pic);
        myPostion = (TextView) view.findViewById(R.id.my_postion);
        myName = (TextView) view.findViewById(R.id.my_name);
        myWorklist = (ConstraintLayout) view.findViewById(R.id.my_worklist);
        myIdea = (ConstraintLayout) view.findViewById(R.id.my_idea);
        myProfit = (ConstraintLayout) view.findViewById(R.id.my_profit);
        customer = (ConstraintLayout) view.findViewById(R.id.customer);
        setting = (ConstraintLayout) view.findViewById(R.id.setting);
    }
}
