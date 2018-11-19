package cn.dankal.basiclib.protocol;

public interface LoginProtocol extends BaseRouteProtocol {
    String PART = "/login/";

    String USERSLOGIN=PART+"login";

    String ENTERPRISELOGIN=PART+"enterpriselogin";

    String REGISTERENTEREMSIL=PART+"registr";

    String REGISTERVECODE=PART+"vecode";

    String SETPWD=PART+"setpwd";

    String FORGETPWD=PART+"forgetPwd";

    String FORGETPWDCODE=PART+"forgetPwdCode";

    String FORGETPWDSET=PART+"forgetPwdSet";

    String REGISTERUSER=PART+"registeruser";

    String USERREGISTERVECODE=PART+"usercode";

    String USERSETPWD=PART+"usersetpwd";
}
