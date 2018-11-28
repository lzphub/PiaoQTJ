package cn.dankal.basiclib.bean;

import java.util.List;

public class ChatBean {

    /**
     * total : 1
     * per_page : 20
     * current_page : 1
     * last_page : 1
     * data : [{"type":1,"engineer_read":1,"content":"asd","create_time":"2018-11-23 10:52:41","sent_by":"engineer"}]
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
         * type : 1
         * engineer_read : 1
         * content : asd
         * create_time : 2018-11-23 10:52:41
         * sent_by : engineer
         */

        private int type;
        private int engineer_read;
        private String content;
        private String create_time;
        private String sent_by;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getEngineer_read() {
            return engineer_read;
        }

        public void setEngineer_read(int engineer_read) {
            this.engineer_read = engineer_read;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getSent_by() {
            return sent_by;
        }

        public void setSent_by(String sent_by) {
            this.sent_by = sent_by;
        }
    }
}
