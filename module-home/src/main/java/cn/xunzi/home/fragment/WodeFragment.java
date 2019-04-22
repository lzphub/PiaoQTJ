package cn.xunzi.home.fragment;

import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import api.MyServiceFactory;
import cn.xunzi.address.R;
import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.base.fragment.BaseFragment;
import cn.xunzi.basiclib.pojo.UserInfoBean;
import cn.xunzi.basiclib.pojo.UserResponseBody;
import cn.xunzi.basiclib.protocol.HomeProtocol;
import cn.xunzi.basiclib.protocol.LoginProtocol;
import cn.xunzi.basiclib.protocol.MyProtocol;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.ActivityUtils;
import cn.xunzi.basiclib.util.ToastUtils;
import cn.xunzi.home.activity.HomeActivity;
import cn.xunzi.my.activity.SetUserNameActivity;

public class WodeFragment extends BaseFragment {
    private RelativeLayout title;
    private TextView tvName;
    private ImageView ivSet;
    private RelativeLayout rlTuiguan;
    private RelativeLayout rlTuandui;
    private RelativeLayout rlBancrad;
    private RelativeLayout rlTixian;
    private RelativeLayout rlSetpwd;
    private Button btExit;
    private TextView tvLevel;
    private TextView tvVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wode;
    }

    @Override
    protected void initComponents() {

    }

    @Override
    protected void initComponents(View view) {
        initView(view);
        try {
            String verName=getActivity().getApplicationContext().getPackageManager().getPackageInfo(getActivity().getPackageName(),0).versionName;
            tvVersion.setText("当前版本号：v"+verName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        rlTuiguan.setOnClickListener(view15 -> ARouter.getInstance().build(HomeProtocol.TUIGUANGAM).navigation());
        rlTuandui.setOnClickListener(view1 -> ARouter.getInstance().build(MyProtocol.MYTEAM).navigation());
        rlBancrad.setOnClickListener(view12 -> ARouter.getInstance().build(MyProtocol.MYCARD).navigation());
        rlTixian.setOnClickListener(view13 -> ARouter.getInstance().build(MyProtocol.WITH).navigation());
        rlSetpwd.setOnClickListener(view14 -> ARouter.getInstance().build(MyProtocol.CHOICEPWD).navigation());
        ivSet.setOnClickListener(view16 -> ARouter.getInstance().build(MyProtocol.SETUSERNAME).withString("username",tvName.getText().toString().substring(3)).navigation());
        btExit.setOnClickListener(view17 -> {
            XZUserManager.resetUserInfo();
            ARouter.getInstance().build(LoginProtocol.USERSLOGIN).navigation();
            ActivityUtils.finishActivity(HomeActivity.class);
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        MyServiceFactory.userDeta(XZUserManager.getUserInfo().getId()+"").safeSubscribe(new AbstractDialogSubscriber<UserInfoBean>(this) {
            @Override
            public void onNext(UserInfoBean userInfoBean) {
                UserResponseBody.DataEntity.UserEntity userEntity=XZUserManager.getUserInfo();
                userEntity.setPayCode(userInfoBean.getData().getPayCode());
                userEntity.setNickName(userInfoBean.getData().getNickName());
                userEntity.setRoleType(userInfoBean.getData().getRoleType());

                XZUserManager.updateUserInfo(userEntity);

                tvName.setText("用户："+userEntity.getNickName());
                switch (Integer.valueOf(userEntity.getRoleType())){
                    case 0:
                        tvLevel.setText("粉丝");
                        break;
                    case 1:
                        tvLevel.setText("VIP");
                        break;
                    case 2:
                        tvLevel.setText("SVIP");
                        break;
                }
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initView(View view) {
        title = view.findViewById(R.id.title);
        tvName = view.findViewById(R.id.tv_name);
        ivSet = view.findViewById(R.id.iv_set);
        rlTuiguan = view.findViewById(R.id.rl_tuiguan);
        rlTuandui = view.findViewById(R.id.rl_tuandui);
        rlBancrad = view.findViewById(R.id.rl_bancrad);
        rlTixian = view.findViewById(R.id.rl_tixian);
        rlSetpwd = view.findViewById(R.id.rl_setpwd);
        btExit = view.findViewById(R.id.bt_exit);
        tvLevel = view.findViewById(R.id.tv_level);
        tvVersion = view.findViewById(R.id.tv_version);
    }
}
