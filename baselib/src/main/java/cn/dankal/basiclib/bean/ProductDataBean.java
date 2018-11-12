package cn.dankal.basiclib.bean;

import java.util.List;

public class ProductDataBean {


    /**
     * uuid : 59da79e07d8ec0d725050acddf42ae9d
     * name : 111商品测试
     * price : 30.00
     * images : ["图片1","img2","img3"]
     * description : 没啥
     * detail : 详情
     */

    private String uuid;
    private String name;
    private String price;
    private String description;
    private String detail;
    private List<String> images;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
