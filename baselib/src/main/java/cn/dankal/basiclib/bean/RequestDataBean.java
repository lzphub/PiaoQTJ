package cn.dankal.basiclib.bean;

import java.util.List;

public class RequestDataBean {


    /**
     * title : Text8
     * images : ["zm_1542420461151.png"]
     * start_price : 25.00
     * end_price : 36.00
     * start_date : 2018-11-17
     * end_date : 2018-11-23
     * status : 2
     * description : Jdhhdhhdhdhddhhdhdhshshuhdbudhdhdhvywujnxb
     */

    private String title;
    private String start_price;
    private String end_price;
    private String start_date;
    private String end_date;
    private int status;
    private String description;
    private List<String> images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_price() {
        return start_price;
    }

    public void setStart_price(String start_price) {
        this.start_price = start_price;
    }

    public String getEnd_price() {
        return end_price;
    }

    public void setEnd_price(String end_price) {
        this.end_price = end_price;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
