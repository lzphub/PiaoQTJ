package cn.xunzi.basiclib.protocol;

public interface LoginProtocol extends BaseRouteProtocol {
    String PART = "/login/";

    String GUIDELOGIN=PART+"guideLogin";

    String USERSLOGIN=PART+"login";

    String FORGETPWD=PART+"forpwd";
}
