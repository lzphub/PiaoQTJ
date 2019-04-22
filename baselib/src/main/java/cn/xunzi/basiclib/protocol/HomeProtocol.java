package cn.xunzi.basiclib.protocol;

public interface HomeProtocol extends BaseRouteProtocol {
    String PART = "/home/";

    String HOME=PART+"home";

    String ZHUANGYONG=PART+"zhuangyong";

    String TUIGUANGAM=PART+"tuiguangma";

    String SERVICE=PART+"service";

    String VIP=PART+"vip";

    String MESSAGE=PART+"message";

    String ONEMONEY=PART+"onemoney";

    String PAYMODE=PART+"paymode";

    String MESSAGELIST=PART+"messagelist";

    String SYSTEMMSG=PART+"systemmsg";

    String PAYDATAS=PART+"paydata";

    String ADDRESSLIST=PART+"addresslist";

    String ADDRESSADD=PART+"addressadd";
}