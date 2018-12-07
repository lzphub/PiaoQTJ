package cn.dankal.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;

import api.MyServiceFactory;
import cn.dankal.address.R;
import cn.dankal.basiclib.DKUserManager;
import cn.dankal.basiclib.base.fragment.BaseFragment;
import cn.dankal.basiclib.bean.PersonalData_EnBean;
import cn.dankal.basiclib.bean.PersonalData_EngineerBean;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.image.PicUtils;
import cn.dankal.basiclib.widget.CircleImageView;

public class My_fragment extends BaseFragment {
    private android.widget.ImageView myNews;
    private CircleImageView headPic;
    private android.widget.TextView myPostion;
    private android.widget.TextView myName;
    private android.support.constraint.ConstraintLayout myWorklist;
    private android.support.constraint.ConstraintLayout myIdea;
    private android.support.constraint.ConstraintLayout myProfit;
    private android.support.constraint.ConstraintLayout customer;
    private android.support.constraint.ConstraintLayout setting;
    private String type;
    private TextView menuText;
    private ImageView iconImg2;
    private TextView menuText2;
    private TextView menuText3;
    private TextView menuText4;
    private TextView menuText5;
    private String head_pic;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initComponents() {

    }

    @Override
    protected void initComponents(View view) {
        initView(view);
        type= SharedPreferencesUtils.getString(getContext(),"identity","user");
        if(type.equals("user")){
            getData();
            menuText.setText("MY \nREQUEST");
            menuText2.setText("MY \nINTENTION");
            menuText3.setText("MY \nFAVORITE");
            menuText4.setText("SERVICE");
            menuText5.setText("SET");
            iconImg2.setImageResource(R.mipmap.ic_my_purchase);
            myPostion.setVisibility(View.INVISIBLE);
        }else{
            getEngineerData();
        }

        myWorklist.setOnClickListener(v -> {
            if(type.equals("user")){
                ARouter.getInstance().build(MyProtocol.MYREQUEST).navigation();
            }else{
                ARouter.getInstance().build(MyProtocol.MYWORKLIST).navigation();
            }
        });
        myIdea.setOnClickListener(v -> {
            if(type.equals("user")){
                ARouter.getInstance().build(MyProtocol.MYINTENTION).navigation();
            }else{
                ARouter.getInstance().build(MyProtocol.MYIDEA).navigation();
            }
        });
        myProfit.setOnClickListener(v -> {
            if(type.equals("user")){
                ARouter.getInstance().build(MyProtocol.MYFAVORITE).navigation();
            }else{
                ARouter.getInstance().build(MyProtocol.MYEARNING).navigation();
            }
        });
        setting.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.SETTING).navigation());
        myNews.setOnClickListener(v -> {
            if (type.equals("user")){
                ARouter.getInstance().build(MyProtocol.SYSTEMNEWS).navigation();
            }else{
                ARouter.getInstance().build(MyProtocol.ENGSYSTEMNEWS).navigation();
            }
        });

        headPic.setOnClickListener(v -> {
            if(type.equals("user")){
                ARouter.getInstance().build(MyProtocol.PERSONALDATAEN).navigation();
            }else{
                ARouter.getInstance().build(MyProtocol.PERSONALDATA).navigation();
            }
        });

        customer.setOnClickListener(v -> ARouter.getInstance().build(HomeProtocol.SERVICE).withString("head_pic",head_pic).navigation());

    }

    private void initView(View view) {
        myNews =  view.findViewById(R.id.my_news);
        headPic = view.findViewById(R.id.head_pic);
        myPostion =  view.findViewById(R.id.my_postion);
        myName =  view.findViewById(R.id.my_name);
        myWorklist =view.findViewById(R.id.my_worklist);
        myIdea =  view.findViewById(R.id.my_idea);
        myProfit = view.findViewById(R.id.my_profit);
        customer =  view.findViewById(R.id.customer);
        setting = view.findViewById(R.id.setting);
        menuText = view.findViewById(R.id.menu_text);
        iconImg2 =  view.findViewById(R.id.icon_img2);
        menuText2 = view.findViewById(R.id.menu_text2);
        menuText3 = view.findViewById(R.id.menu_text3);
        menuText4 = view.findViewById(R.id.menu_text4);
        menuText5 = view.findViewById(R.id.menu_text5);
    }

    //获取用户信息
    private void getData(){
        MyServiceFactory.getUserData().safeSubscribe(new AbstractDialogSubscriber<PersonalData_EnBean>(this) {
            @Override
            public void onNext(PersonalData_EnBean personalData_enBean) {
                myName.setText(personalData_enBean.getName());
                PicUtils.loadAvatar(PicUtils.getUrl(personalData_enBean.getAvatar()),headPic);
                head_pic=personalData_enBean.getAvatar();
            }
        });
    }
    private void getEngineerData(){
        MyServiceFactory.getEngineerData().safeSubscribe(new AbstractDialogSubscriber<PersonalData_EngineerBean>(this) {
            @Override
            public void onNext(PersonalData_EngineerBean personalData_engineerBean) {
                myName.setText(personalData_engineerBean.getName());
                PicUtils.loadAvatar(personalData_engineerBean.getAvatar(),headPic);
                head_pic=personalData_engineerBean.getAvatar();
                switch (personalData_engineerBean.getLevel()){
                    case 1:
                        myPostion.setText("初级工程师");
                        break;
                    case 2:
                        myPostion.setText("中级工程师");
                        break;
                    case 3:
                        myPostion.setText("高级工程师");
                        break;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        dismissLoadingDialog();
        if(type.equals("user")){
            getData();
        }else{
            getEngineerData();
        }
    }
}
