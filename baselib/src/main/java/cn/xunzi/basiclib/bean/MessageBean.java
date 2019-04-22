package cn.xunzi.basiclib.bean;

import java.util.List;

public class MessageBean {


    /**
     * msg : 获取成功
     * code : 200
     * data : {"list":[{"createtime":"2018-03-02 11:51:00","creater":"","id":2,"title":"全球狂欢节双十一","type":2,"content":"双十一狂欢节,凡交易免手续费，超值矿机享八折优惠"}]}
     */
    private String msg;
    private String code;
    private DataEntity data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public class DataEntity {
        /**
         * list : [{"createtime":"2018-03-02 11:51:00","creater":"","id":2,"title":"全球狂欢节双十一","type":2,"content":"双十一狂欢节,凡交易免手续费，超值矿机享八折优惠"}]
         */
        private List<ListEntity> list;

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public class ListEntity {
            /**
             * createtime : 2018-03-02 11:51:00
             * creater :
             * id : 2
             * title : 全球狂欢节双十一
             * type : 2
             * content : 双十一狂欢节,凡交易免手续费，超值矿机享八折优惠
             */
            private String createtime;
            private String creater;
            private int id;
            private String title;
            private int type;
            private String content;

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public void setCreater(String creater) {
                this.creater = creater;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setType(int type) {
                this.type = type;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreatetime() {
                return createtime;
            }

            public String getCreater() {
                return creater;
            }

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public int getType() {
                return type;
            }

            public String getContent() {
                return content;
            }
        }
    }
}
