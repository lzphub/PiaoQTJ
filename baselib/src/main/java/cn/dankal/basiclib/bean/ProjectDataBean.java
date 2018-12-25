package cn.dankal.basiclib.bean;

import java.io.Serializable;

public class ProjectDataBean implements Serializable {

    /**
     * name : 6号
     * start_price : 122.00
     * end_price : 22222.00
     * cpl_start_date : 2018-12-06
     * cpl_end_date : 2018-12-28
     * desc : null
     * status : 2
     * detail : null
     */
    private String uuid;
    private String name;
    private String start_price;
    private String end_price;
    private String cpl_start_date;
    private String cpl_end_date;
    private String desc;
    private int status;
    private String detail;

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

    public String getCpl_start_date() {
        return cpl_start_date;
    }

    public void setCpl_start_date(String cpl_start_date) {
        this.cpl_start_date = cpl_start_date;
    }

    public String getCpl_end_date() {
        return cpl_end_date;
    }

    public void setCpl_end_date(String cpl_end_date) {
        this.cpl_end_date = cpl_end_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
