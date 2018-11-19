package cn.dankal.basiclib.bean;

import java.util.List;

public class RequestDataBean {

    /**
     * title : Hhdnndn
     * images : null
     * start_price : 9.00
     * end_price : 69669.00
     * start_date : 2018-11-24
     * end_date : 2018-11-26
     */

    private String title;
    private List<String> images;
    private String start_price;
    private String end_price;
    private String start_date;
    private String end_date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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
}
