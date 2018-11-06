package cn.dankal.basiclib.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.widget.TextView;

import cn.dankal.basiclib.R;
import cn.dankal.basiclib.base.callback.DKCallBackBooleanObject;
import cn.dankal.basiclib.util.SoftKeyboardUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.widget.passwordview.GridPasswordView;

import static android.view.KeyEvent.KEYCODE_ENTER;


/**
 * description: 密码Dialog
 *
 * @author vane
 * @since 2018/4/9
 */

public class PasswordDialog extends DialogFragment {

    private Dialog mDialog;

    final int passwordLength = 6;
    private DKCallBackBooleanObject<String> dkCallBackBoolean;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog = new Dialog(getActivity(), R.style.NormalDialogStyle);
        mDialog.setContentView(R.layout.dialog_password);
        mDialog.setCanceledOnTouchOutside(false);
         GridPasswordView passwordView = mDialog.findViewById(R.id.passwordview);
         TextView tvTitle = mDialog.findViewById(R.id.tv_title);
         TextView tvBank = mDialog.findViewById(R.id.tv_bank);
         TextView tvCount = mDialog.findViewById(R.id.tv_count);

        String defaultErrorToast = "请输入6位数字";

        passwordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                String password = passwordView.getPassWord();
                if (TextUtils.isEmpty(password) || password.length() != passwordLength) {
                    ToastUtils.showShort("xx");
                    return ;
                }
                SoftKeyboardUtil.hideSoftInput(passwordView);
                dkCallBackBoolean.action(1,password);
                dismiss();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString("title");
            String bank = bundle.getString("bank");
            defaultErrorToast = bundle.getString("error_toast");
            String cashMony = bundle.getString("cash_money");
            tvTitle.setText(title);
            tvBank.setText(bank.split("·")[0]);
            tvCount.setText(cashMony);

        }

        final String finalErrorToast = defaultErrorToast;

        mDialog.findViewById(R.id.iv_cancel).setOnClickListener(v -> mDialog.dismiss());
//        mDialog.findViewById(R.id.inputView).setOnKeyListener((v, keyCode, event) -> {
//            if (keyCode== KEYCODE_ENTER){
//                String password = passwordView.getPassWord();
//                if (TextUtils.isEmpty(password) || password.length() != passwordLength) {
//                    ToastUtils.showShort(finalErrorToast);
//                    return true;
//                }
//                SoftKeyboardUtil.hideSoftInput(v);
//                dkCallBackBoolean.action(1,password);
//                dismiss();
//                return true;
//            }
//            return false;
//        });

        mDialog.findViewById(R.id.ll_card).setOnClickListener(v -> {
            dkCallBackBoolean.action(0,null);
            dismiss();
        });
        return mDialog;
    }

    public void setConfirmOrderCallback(DKCallBackBooleanObject dkCallBackBooleanObject) {
        this.dkCallBackBoolean = dkCallBackBooleanObject;
    }


}

