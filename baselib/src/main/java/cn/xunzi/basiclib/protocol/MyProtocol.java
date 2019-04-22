package cn.xunzi.basiclib.protocol;

import retrofit2.http.POST;

public interface MyProtocol extends BaseRouteProtocol {
    String PART = "/my/";

    String MYTEAM=PART+"myteam";

    String MYCARD=PART+"mucrad";

    String WITH=PART+"with";

    String SETPWD=PART+"setpwd";

    String CHANGEWITHPWD=PART+"changewithpwd";

    String SETWITHPWD=PART+"setwithpwd";

    String ADDBANKCARD=PART+"addcard";

    String WITHDETA=PART+"withdeta";

    String MYTEAMLIST=PART+"myteamlist";

    String SETUSERNAME=PART+"setusername";

    String CHOICEPWD=PART+"choicepwd";

    String PICKCITY=PART+"pickcity";
}
