package cn.xunzi.basiclib.pojo;

import java.util.List;

/**
 * Class description : 地址选择器
 *
 * @author lubolin
 * @since 18/6/22
 */

public class LocalAddressBean {

    public String text;
    public List<AddressCity> children;

    public static class AddressCity {
        public String text;
        public List<AddressArea> children;

        public static class AddressArea {
            public String text;
            public List<AddressStreet> children;

            public static class AddressStreet {
                public String text;
            }
        }
    }

}
