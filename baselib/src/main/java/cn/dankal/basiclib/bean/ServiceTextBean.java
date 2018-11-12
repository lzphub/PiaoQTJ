package cn.dankal.basiclib.bean;

import android.net.Uri;

public class ServiceTextBean {
    private String imgUrl;
    private String send_text;
    private Uri send_img;
    private int type;

    public Uri getSend_img() {
        return send_img;
    }

    public void setSend_img(Uri send_img) {
        this.send_img = send_img;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSend_text() {
        return send_text;
    }

    public void setSend_text(String send_text) {
        this.send_text = send_text;
    }
}
