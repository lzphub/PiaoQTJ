package cn.dankal.basiclib.protocol;

public interface MyProtocol extends BaseRouteProtocol {
    String PART = "/my/";

    String MYWORKLIST=PART+"myworklist";

    String MYIDEA=PART+"myidea";

    String MYEARNING=PART+"myearning";

    String TRANSACTIONRECORD=PART+"transaction";

    String SETTING=PART+"setting";

    String SYSTEMNEWS=PART+"news";

    String PERSONALDATA=PART+"personaldata";

    String EDITDATA=PART+"editdata";

    String WITHDRAWAL=PART+"withdrawal";

    String ABOUTUS=PART+"aboutus";

    String OPINION=PART+"opinion";

    String SETWITHPEDCODE=PART+"setwithpwdcode";

    String SETWITHPWD=PART+"setwithpwd";
}
