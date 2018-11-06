package cn.dankal.basiclib.bean;

public class DemandListbean {
    private String priceRange="$0-260000";
    private String date="2018-04-21";
    private String title="大型工程照明需求解决";
    private String content="新艺工厂，建筑面积600m2，现需要定制一套照明系统，大概600套";

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
