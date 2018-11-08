package cn.dankal.basiclib.bean;

import java.util.List;

public class MyRequestBean {
    private String title="I am title";
    private String price="1~10000";
    private String data="2018-10-25~2018-12-25";
    private List<urli> urlString;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<urli> getUrlString() {
        return urlString;
    }

    public void setUrlString(List<urli> urlString) {
        this.urlString = urlString;
    }

   public static class urli{
        private String imgurl="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541421297535&di=7ada3a21db141cb769f8bce9d407686b&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Farchive%2Fdbfb6a5c4276ef323c6b140f40b688363f7c52dd.jpg";

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }
    }

}
