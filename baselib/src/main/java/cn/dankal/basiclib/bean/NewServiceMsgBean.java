package cn.dankal.basiclib.bean;

import java.util.List;

public class NewServiceMsgBean {


    private List<NewMsgBean> new_msg;

    public List<NewMsgBean> getNew_msg() {
        return new_msg;
    }

    public void setNew_msg(List<NewMsgBean> new_msg) {
        this.new_msg = new_msg;
    }

    public static class NewMsgBean {
        /**
         * type : 1
         * is_read : 0
         * content : 123
         * create_time : 2018-11-29 17:41:55
         */

        private int type;
        private int is_read;
        private String content;
        private String create_time;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
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
    }
}
