package cn.dankal.basiclib.bean;

import java.util.List;

public class TransactionBean {


    /**
     * total : 5
     * per_page : 20
     * current_page : 1
     * last_page : 1
     * data : [{"create_time":"2018-11-20 15:13:10","money":"5.00","type":"提现"},{"create_time":"2018-11-20 15:11:38","money":"5.00","type":"提现"},{"create_time":"2018-11-20 15:09:42","money":"200.00","type":"提现"},{"create_time":"2018-11-16 19:23:42","money":"100.00","type":"提现"},{"create_time":"2018-11-16 18:03:32","money":"1000.23","type":"收入"}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
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
         * create_time : 2018-11-20 15:13:10
         * money : 5.00
         * type : 提现
         */

        private String create_time;
        private String money;
        private String type;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
