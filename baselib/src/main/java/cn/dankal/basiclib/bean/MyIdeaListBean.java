package cn.dankal.basiclib.bean;

import java.io.Serializable;
import java.util.List;

public class MyIdeaListBean {

    /**
     * total : 2
     * per_page : 20
     * current_page : 1
     * last_page : 1
     * data : [{"uuid":"b5b9bf96ddd138248165d645c979eb9b","title":"这是第二个创意","detail":"这是第二个创意的方案详情。。。","images":null,"status":1},{"uuid":"ead63765c78c1b6fd455aeb4082933c4","title":"这是第一个测试方案的标题","detail":"这是第一个测试方案的内容，他说要十五个字。","images":["zm_1542870449447.png"],"status":1}]
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

    public static class DataBean implements Serializable {
        /**
         * uuid : b5b9bf96ddd138248165d645c979eb9b
         * title : 这是第二个创意
         * detail : 这是第二个创意的方案详情。。。
         * images : null
         * status : 1
         */

        private String uuid;
        private String title;
        private String detail;
        private List<String> images;
        private int status;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
