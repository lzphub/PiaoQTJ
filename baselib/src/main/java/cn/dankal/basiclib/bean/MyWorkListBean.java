package cn.dankal.basiclib.bean;

import java.util.List;

public class MyWorkListBean {

    /**
     * total : 1
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"uuid":"e6e14c8e980c8b9a1f7831695ce351d7","name":"简单的工单","start_price":"20.00","end_price":"50.00","cpl_start_date":"2018-10-29","cpl_end_date":"2019-04-04","status":3,"desc":"没啥可描述的"}]
     */

    private int total;
    private String per_page;
    private int current_page;
    private int last_page;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPer_page() {
        return per_page;
    }

    public void setPer_page(String per_page) {
        this.per_page = per_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uuid : e6e14c8e980c8b9a1f7831695ce351d7
         * name : 简单的工单
         * start_price : 20.00
         * end_price : 50.00
         * cpl_start_date : 2018-10-29
         * cpl_end_date : 2019-04-04
         * status : 3
         * desc : 没啥可描述的
         */

        private String uuid;
        private String name;
        private String start_price;
        private String end_price;
        private String cpl_start_date;
        private String cpl_end_date;
        private int status;
        private String desc;

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
    }
}
