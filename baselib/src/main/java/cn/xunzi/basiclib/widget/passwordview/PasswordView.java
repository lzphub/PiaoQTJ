package cn.xunzi.basiclib.widget.passwordview;

/**
 *
 * @author vane
 * @since 2018/4/9
 */

public interface PasswordView {

//void setError(String error);

    String getPassWord();

    void clearPassword();

    void setPassword(String password);

    void setPasswordVisibility(boolean visible);

    void togglePasswordVisibility();

    void setOnPasswordChangedListener(GridPasswordView.OnPasswordChangedListener listener);

    void setPasswordType(PasswordType passwordType);

}
