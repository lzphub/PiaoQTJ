package cn.xunzi.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api.MyServiceFactory;
import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.base.activity.BaseStateActivity;
import cn.xunzi.basiclib.bean.PostBean;
import cn.xunzi.basiclib.pojo.UserInfoBean;
import cn.xunzi.basiclib.pojo.UserResponseBody;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.ToastUtils;
import cn.xunzi.setting.R;

import static cn.xunzi.basiclib.protocol.MyProtocol.SETUSERNAME;

@Route(path = SETUSERNAME)
public class SetUserNameActivity extends BaseActivity {
    private ImageView imBack;
    private EditText etUsername;
    private String username;
    private Button btSubmit;

    @Override
    protected int getLayoutId() {
        username=getIntent().getStringExtra("username");
        return R.layout.activity_set_user_name;
    }

    @Override
    protected void initComponents() {
        initView();
        imBack.setOnClickListener(view -> finish());
        etUsername.setHint(username);

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？_-《》。，、＇：∶；?‘’“”〝〞ˆˇ﹕︰﹔﹖﹑•¨….¸;！´？！～—ˉ｜‖＂〃｀@﹫¡¿﹏﹋﹌︴々﹟#﹩$﹠&﹪%*﹡﹢﹦﹤‐￣¯―﹨ˆ˜﹍﹎+=<＿_-\\ˇ~﹉﹊（）〈〉‹›﹛﹜『』〖〗［］《》〔〕{}「」【】︵︷︿︹︽_﹁﹃︻︶︸﹀︺︾ˉ﹂﹄︼﹢﹣×÷±/=≌∽≦≧≒﹤﹥≈≡≠=≤≥<>≮≯∷∶∫∮∝∞∧∨∑∏∪∩∈∵∴⊥∥∠⌒⊙√∟⊿㏒㏑%‰⅟½⅓⅕⅙⅛⅔⅖⅚⅜¾⅗⅝⅞⅘≂≃≄≅≆≇≈≉≊≋≌≍≎≏≐≑≒≓≔≕≖≗≘≙≚≛≜≝≞≟≠≡≢≣≤≥≦≧≨≩⊰⊱⋛⋚∫∬∭∮∯∰∱∲∳%℅‰‱øØπ°′″＄￥〒￠￡％＠℃℉﹩﹪‰﹫㎡㏕㎜㎝㎞㏎m³㎎㎏㏄º○¤%$º¹²³€£Ұ₴$₰¢₤¥₳₲₪₵元₣₱฿¤₡₮₭₩ރ円₢₥₫₦zł﷼₠₧₯₨Kčर₹ƒ₸￠♠♣♧♡♥❤❥❣♂♀✲☀☼☾☽◐◑☺☻☎☏✿❀№↑↓←→√×÷★℃℉°◆◇⊙■□△▽¿½☯✡㍿卍卐♂♀✚〓㎡♪♫♩♬㊚㊛囍㊒㊖Φ♀♂‖$@*&#※卍卐Ψ♫♬♭♩♪♯♮⌒¶∮‖€￡¥$]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) {
                    return "";
                } else {
                    return null;
                }
            }
        };
//        etUsername.setFilters(new InputFilter[]{filter});
        etUsername.setFilters(new InputFilter[]{
                (source, start, end, dest, dstart, dend) -> {
                    for (int i = start; i < end; i++) {
                        if ( !Character.isLetterOrDigit(source.charAt(i))
                                && !Character.toString(source.charAt(i)) .equals("")
                                && !Character.toString(source.charAt(i)) .equals(""))
                        {
                            return "";
                        }
                    }
                    return null;
                }
        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName=etUsername.getText().toString().trim();
                if(userName.isEmpty()){
                    ToastUtils.showShort("用户名不能为空");
                    return;
                }
                MyServiceFactory.editUserName(XZUserManager.getUserInfo().getId()+"","nickName",userName).safeSubscribe(new AbstractDialogSubscriber<PostBean>(SetUserNameActivity.this) {
                    @Override
                    public void onNext(PostBean postBean) {
                        if(postBean.getCode().equals("200")){
                            MyServiceFactory.userDeta(XZUserManager.getUserInfo().getId()+"").safeSubscribe(new AbstractDialogSubscriber<UserInfoBean>(SetUserNameActivity.this) {
                                @Override
                                public void onNext(UserInfoBean dataEntity) {
                                    UserResponseBody.DataEntity.UserEntity userEntity=XZUserManager.getUserInfo();
                                    userEntity.setNickName(dataEntity.getData().getNickName());
                                    XZUserManager.updateUserInfo(userEntity);
                                    ToastUtils.showShort("修改成功");
                                    finish();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    super.onError(e);
                                }
                            });

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
            }
        });

    }

    private void initView() {
        imBack = findViewById(R.id.im_back);
        etUsername = findViewById(R.id.et_username);
        btSubmit = findViewById(R.id.bt_submit);
    }
}
