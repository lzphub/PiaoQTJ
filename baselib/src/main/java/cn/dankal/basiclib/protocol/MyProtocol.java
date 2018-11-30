package cn.dankal.basiclib.protocol;

public interface MyProtocol extends BaseRouteProtocol {
    String PART = "/my/";

    String MYWORKLIST=PART+"myworklist";

    String MYIDEA=PART+"myidea";

    String MYEARNING=PART+"myearning";

    String TRANSACTIONRECORD=PART+"transaction";

    String SETTING=PART+"setting";

    String SYSTEMNEWS=PART+"news";

    String ENGSYSTEMNEWS=PART+"engnews";

    String PERSONALDATA=PART+"personaldata";

    String PERSONALDATAEN=PART+"personaldataen";

    String EDITDATA=PART+"editdata";

    String EDITDATAEN=PART+"editdataen";

    String WITHDRAWAL=PART+"withdrawal";

    String ABOUTUS=PART+"aboutus";

    String OPINION=PART+"opinion";

    String SETWITHPEDCODE=PART+"setwithpwdcode";

    String SETWITHPWD=PART+"setwithpwd";

    String COMPROB=PART+"comprob";

    String COMPORDATA=PART+"comprodata";

    String BINDCARD=PART+"bindcard";

    String CHECKCARD=PART+"checkcrad";

    String MYFAVORITE=PART+"myfavorite";

    String MYINTENTION=PART+"myintention";

    String MYREQUEST=PART+"myrequest";

    String PICKCOUNTRIES=PART+"pickcountries";

    String MYINTENTIONDETA=PART+"myintentiondeta";

    String MYREQUESTDETA=PART+"myrequestdeta";

    String IDEADATA=PART+"myideadata";

    String WORKDATA=PART+"workdata";
}
