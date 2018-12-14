package cn.dankal.basiclib.bean;

import java.util.List;

public class SystemMsgBean {


    /**
     * total : 10
     * per_page : 20
     * current_page : 1
     * last_page : 1
     * data : [{"uuid":"39b3ebdcccbc395d651805bc797bd178","title":"123","description":"123","is_read":0,"push_time":"2018-12-13 17:24:19","kind":1,"content":"123"},{"uuid":"977481f00642893d7f51504a8e94c063","title":"Text","description":"123","is_read":0,"push_time":"2018-12-13 17:18:32","kind":1,"content":"123"},{"uuid":"28fcbe54025f9907cbc79f59ce268713","title":"12月13日测试","description":"1","is_read":0,"push_time":"2018-12-13 11:07:17","kind":1,"content":"1"},{"uuid":"e581c2b9a2ed8a226994bfbd97401b14","title":"哈哈哈","description":"描述","is_read":0,"push_time":"2018-12-13 10:35:48","kind":1,"content":"详情"},{"uuid":"eb74e912797e6914835d46953d126a33","title":"测试","description":"ksdjafhkjasd","is_read":0,"push_time":"2018-12-13 10:30:22","kind":1,"content":"sahgjkfhgjkdfahjkghkjdhgkfghkjlakdlfgj"},{"uuid":"8eb6f014962cb82b63cbe57bb10e8cef","title":"123123","description":"123123123","is_read":0,"push_time":"2018-12-12 15:32:50","kind":1,"content":"12312312312312"},{"uuid":"d47b49a580eb7b8fe83d3ff2b45cc246","title":"1212","description":"1212121","is_read":0,"push_time":"2018-12-12 15:31:12","kind":1,"content":"1111111111"},{"uuid":"b4da1efcff286d42122709fb9c5205c6","title":"test","description":"description","is_read":0,"push_time":"2018-12-12 15:28:27","kind":1,"content":"tetsts"},{"uuid":"fdb352457831ce44e5c3eafed048ffe3","title":"rteetre","description":"ertere","is_read":0,"push_time":"2018-12-12 15:21:42","kind":1,"content":"rerer"},{"uuid":"5aa8ec55f9c14356834c96482108ad7c","title":"testtesttest","description":"description description","is_read":0,"push_time":"2018-12-06 15:48:16","kind":1,"content":"tatagsdfsdfs"}]
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
         * uuid : 39b3ebdcccbc395d651805bc797bd178
         * title : 123
         * description : 123
         * is_read : 0
         * push_time : 2018-12-13 17:24:19
         * kind : 1
         * content : 123
         */

        private String uuid;
        private String title;
        private String description;
        private int is_read;
        private String push_time;
        private int kind;
        private String content;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }

        public String getPush_time() {
            return push_time;
        }

        public void setPush_time(String push_time) {
            this.push_time = push_time;
        }

        public int getKind() {
            return kind;
        }

        public void setKind(int kind) {
            this.kind = kind;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
