package cn.dankal.basiclib.protocol;

/**
 * Created by fred
 * Date: 2018/9/28.
 * Time: 17:32
 * classDescription:
 */
public interface LoginProtocol extends BaseRouteProtocol {
    String PART = "/login/";

    String USERSLOGIN=PART+"login";

    String ENTERPRISELOGIN=PART+"enterpriselogin";

    String REGISTERENTEREMSIL=PART+"registr";

    String REGISTERVECODE=PART+"VECODE";

    String SETPWD=PART+"setpwd";

    String FORGETPWD=PART+"forgetPwd";

    String FORGETPWDCODE=PART+"forgetPwdCode";

    String FORGETPWDSET=PART+"forgetPwdSet";
}
