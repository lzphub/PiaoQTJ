package cn.xunzi.my.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api.MyServiceFactory;
import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.bean.BankListBean;
import cn.xunzi.basiclib.bean.PostBean;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.BankUtil;
import cn.xunzi.basiclib.util.Logger;
import cn.xunzi.basiclib.util.StringUtil;
import cn.xunzi.basiclib.util.ToastUtils;
import cn.xunzi.setting.R;

import static cn.xunzi.basiclib.protocol.MyProtocol.ADDBANKCARD;

@Route(path = ADDBANKCARD)
public class AddBankCardActivity extends BaseActivity {

    private ImageView imBack;
    private EditText etName;
    private TextView tvPriv;
    private TextView tvCity;
    private EditText etSubBank;
    private EditText etBank;
    private EditText etCard;
    private Button btBind;
    private BankListBean bankListBean = new BankListBean();

    @Override
    protected int getLayoutId() {
        bankListBean = (BankListBean) getIntent().getSerializableExtra("data");
        return R.layout.activity_add_bank_card;
    }
    /**
     * 只能输入中文的判断
     *
     * @param c
     * @return
     */
    private boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) {
            return true;
        }
        return false;
    }

    @Override
    protected void initComponents() {
        initView();
        imBack.setOnClickListener(view -> finish());

        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？_-《》。，、＇：∶；?‘’“”〝〞ˆˇ﹕︰﹔﹖﹑•¨….¸;！´？！～—ˉ｜‖＂〃｀@﹫¡¿﹏﹋﹌︴々﹟#﹩$﹠&﹪%*﹡﹢﹦﹤‐￣¯―﹨ˆ˜﹍﹎+=<＿_-\\ˇ~﹉﹊（）〈〉‹›﹛﹜『』〖〗［］《》〔〕{}「」【】︵︷︿︹︽_﹁﹃︻︶︸﹀︺︾ˉ﹂﹄︼﹢﹣×÷±/=≌∽≦≧≒﹤﹥≈≡≠=≤≥<>≮≯∷∶∫∮∝∞∧∨∑∏∪∩∈∵∴⊥∥∠⌒⊙√∟⊿㏒㏑%‰⅟½⅓⅕⅙⅛⅔⅖⅚⅜¾⅗⅝⅞⅘≂≃≄≅≆≇≈≉≊≋≌≍≎≏≐≑≒≓≔≕≖≗≘≙≚≛≜≝≞≟≠≡≢≣≤≥≦≧≨≩⊰⊱⋛⋚∫∬∭∮∯∰∱∲∳%℅‰‱øØπ°′″＄￥〒￠￡％＠℃℉﹩﹪‰﹫㎡㏕㎜㎝㎞㏎m³㎎㎏㏄º○¤%$º¹²³€£Ұ₴$₰¢₤¥₳₲₪₵元₣₱฿¤₡₮₭₩ރ円₢₥₫₦zł﷼₠₧₯₨Kčर₹ƒ₸￠♠♣♧♡♥❤❥❣♂♀✲☀☼☾☽◐◑☺☻☎☏✿❀№↑↓←→√×÷★℃℉°◆◇⊙■□△▽¿½☯✡㍿卍卐♂♀✚〓㎡♪♫♩♬㊚㊛囍㊒㊖Φ♀♂‖$@*&#※卍卐Ψ♫♬♭♩♪♯♮⌒¶∮‖€￡¥$]";
            Pattern pattern = Pattern.compile(speChat);
            Matcher matcher = pattern.matcher(source.toString());
            if (matcher.find()) {
                return "";
            } else {
                return null;
            }
        };

        /**
         * EditText只能输入中文
         */
        InputFilter filter2 = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                if (!isChinese(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        };


        etName.setFilters(new InputFilter[]{filter2});
        etBank.setFilters(new InputFilter[]{filter2});
        etSubBank.setFilters(new InputFilter[]{filter2});

        etCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i == 18) {
                    etBank.setText(BankUtil.getname(charSequence.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tvPriv.setOnClickListener(view -> {
            Intent intent = new Intent(AddBankCardActivity.this, PickCityActivity.class);
            intent.putExtra("type", "priv");
            tvCity.setText("选择");
            startActivityForResult(intent, 22);
        });
        tvCity.setOnClickListener(view -> {
            if (tvPriv.getText().toString().trim().equals("选择")) {
                ToastUtils.showShort("请选择开户省");
                return;
            }
            Intent intent = new Intent(AddBankCardActivity.this, PickCityActivity.class);
            intent.putExtra("type", tvPriv.getText().toString().trim());
            startActivityForResult(intent, 23);
        });

        btBind.setOnClickListener(view -> {
            String name = etName.getText().toString().trim();
            String province = tvPriv.getText().toString().trim();
            String city = tvCity.getText().toString().trim();
            String card = etCard.getText().toString().trim();
            String bank = etBank.getText().toString().trim();
            String subbank = etSubBank.getText().toString().trim();

            if (name.isEmpty()) {
                ToastUtils.showShort("请填写持卡人姓名");
                return;
            }
            if (province.equals("选择")) {
                ToastUtils.showShort("请选择开户省");
                return;
            }
            if (city.equals("选择")) {
                ToastUtils.showShort("请选择开户市");
                return;
            }
            if (card.isEmpty()) {
                ToastUtils.showShort("请填写银行卡号");
                return;
            }
//            if (StringUtil.isBankCard(card)) {
//                ToastUtils.showShort("请填写正确的银行卡号");
//                return;
//            }
            if(card.length()<15){
                ToastUtils.showShort("请填写正确的银行卡号");
                return;
            }
            if (bank.isEmpty()) {
                ToastUtils.showShort("请填写开户银行");
                return;
            }
            if (subbank.isEmpty()) {
                ToastUtils.showShort("请填写开户支行");
                return;
            }

            if(null != bankListBean){
                for (int i = 0; i < bankListBean.getData().getList().size(); i++) {
                    if (card.equals(bankListBean.getData().getList().get(i).getBankNo())) {
                        ToastUtils.showShort("此银行卡号已添加");
                        return;
                    }
                }
            }


            MyServiceFactory.bindCard(XZUserManager.getUserInfo().getId() + "", name, province, city, subbank, bank, card).safeSubscribe(new AbstractDialogSubscriber<PostBean>(AddBankCardActivity.this) {
                @Override
                public void onNext(PostBean postBean) {
                    ToastUtils.showShort(postBean.getMsg());
                    if (postBean.getCode().equals("200")) {
                        ToastUtils.showShort("绑定成功");
                        finish();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                }
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            if (requestCode == 22) {
                String countries = data.getStringExtra("countries");
                tvPriv.setText(countries);
            }
            if (requestCode == 23) {
                String countries = data.getStringExtra("countries");
                tvCity.setText(countries);
            }
        }
    }

    private void initView() {
        imBack = findViewById(R.id.im_back);
        etName = findViewById(R.id.et_name);
        tvPriv = findViewById(R.id.tv_priv);
        tvCity = findViewById(R.id.tv_city);
        etSubBank = findViewById(R.id.et_subBank);
        etBank = findViewById(R.id.et_bank);
        etCard = findViewById(R.id.et_card);
        btBind = findViewById(R.id.bt_bind);
    }
}
