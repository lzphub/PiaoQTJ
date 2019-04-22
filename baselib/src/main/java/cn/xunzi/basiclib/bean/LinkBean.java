package cn.xunzi.basiclib.bean;

import java.util.List;

public class LinkBean {

    /**
     * msg : 获取成功
     * code : 200
     * data : {"list":[{"servicePhone":"0755-85279131","createTime":"2018-12-29 10:52:35","serviceCode":"vx123456","hotline":"131254125412","remark":"快来加入我们","updateTime":"2018-12-29 10:52:35","id":1}]}
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
         * list : [{"servicePhone":"0755-85279131","createTime":"2018-12-29 10:52:35","serviceCode":"vx123456","hotline":"131254125412","remark":"快来加入我们","updateTime":"2018-12-29 10:52:35","id":1}]
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
             * servicePhone : 0755-85279131
             * createTime : 2018-12-29 10:52:35
             * serviceCode : vx123456
             * hotline : 131254125412
             * remark : 快来加入我们
             * updateTime : 2018-12-29 10:52:35
             * id : 1
             */
            private String servicePhone;
            private String createTime;
            private String serviceCode;
            private String hotline;
            private String remark;
            private String updateTime;
            private int id;

            public void setServicePhone(String servicePhone) {
                this.servicePhone = servicePhone;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public void setServiceCode(String serviceCode) {
                this.serviceCode = serviceCode;
            }

            public void setHotline(String hotline) {
                this.hotline = hotline;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getServicePhone() {
                return servicePhone;
            }

            public String getCreateTime() {
                return createTime;
            }

            public String getServiceCode() {
                return serviceCode;
            }

            public String getHotline() {
                return hotline;
            }

            public String getRemark() {
                return remark;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public int getId() {
                return id;
            }
        }
    }
}
