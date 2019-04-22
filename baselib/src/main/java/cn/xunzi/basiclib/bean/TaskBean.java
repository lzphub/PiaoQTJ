package cn.xunzi.basiclib.bean;

import java.util.List;

public class TaskBean {

    /**
     * msg : 获取成功
     * code : 200
     * data : {"list":[{"reward":0,"img":"","remain":30,"remark":"11111111dfsasdfadggag","updateTime":"2019-01-21 16:03:08","title":"提神醒脑茶","type":2,"number":30,"userStatue":0,"createTime":"2019-01-10 16:05:03","detail":"fdgsgf","id":2,"status":1},{"reward":12,"img":"/Weixun/static/upload/20190114/20190114170331_455.png","remain":10,"remark":"双方各渤海金控","updateTime":"2019-01-21 16:02:05","title":"测试1","type":2,"number":12,"userStatue":0,"createTime":"2019-01-14 17:05:36","detail":"啊所发生的","id":5,"status":1},{"reward":0,"img":"","remain":123,"remark":"safdsfadsf","updateTime":"2019-01-17 17:05:10","title":"ces ","type":2,"number":12,"userStatue":0,"createTime":"2019-01-17 14:46:47","detail":"xiangqing,123","id":12,"status":1},{"reward":1,"img":"","remain":"","remark":"dgsadsgag","updateTime":"2019-01-21 19:24:25","title":"123","type":2,"number":20,"userStatue":0,"createTime":"2019-01-21 19:24:25","detail":"dakhgkjdgkjgd","id":18,"status":1}]}
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
         * list : [{"reward":0,"img":"","remain":30,"remark":"11111111dfsasdfadggag","updateTime":"2019-01-21 16:03:08","title":"提神醒脑茶","type":2,"number":30,"userStatue":0,"createTime":"2019-01-10 16:05:03","detail":"fdgsgf","id":2,"status":1},{"reward":12,"img":"/Weixun/static/upload/20190114/20190114170331_455.png","remain":10,"remark":"双方各渤海金控","updateTime":"2019-01-21 16:02:05","title":"测试1","type":2,"number":12,"userStatue":0,"createTime":"2019-01-14 17:05:36","detail":"啊所发生的","id":5,"status":1},{"reward":0,"img":"","remain":123,"remark":"safdsfadsf","updateTime":"2019-01-17 17:05:10","title":"ces ","type":2,"number":12,"userStatue":0,"createTime":"2019-01-17 14:46:47","detail":"xiangqing,123","id":12,"status":1},{"reward":1,"img":"","remain":"","remark":"dgsadsgag","updateTime":"2019-01-21 19:24:25","title":"123","type":2,"number":20,"userStatue":0,"createTime":"2019-01-21 19:24:25","detail":"dakhgkjdgkjgd","id":18,"status":1}]
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
             * reward : 0
             * img :
             * remain : 30
             * remark : 11111111dfsasdfadggag
             * updateTime : 2019-01-21 16:03:08
             * title : 提神醒脑茶
             * type : 2
             * number : 30
             * userStatue : 0
             * createTime : 2019-01-10 16:05:03
             * detail : fdgsgf
             * id : 2
             * status : 1
             */
            private int reward;
            private String img;
            private int remain;
            private String remark;
            private String updateTime;
            private String title;
            private int type;
            private int number;
            private int userStatue;
            private String createTime;
            private String detail;
            private int id;
            private int status;

            public void setReward(int reward) {
                this.reward = reward;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public void setRemain(int remain) {
                this.remain = remain;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setType(int type) {
                this.type = type;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public void setUserStatue(int userStatue) {
                this.userStatue = userStatue;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getReward() {
                return reward;
            }

            public String getImg() {
                return img;
            }

            public int getRemain() {
                return remain;
            }

            public String getRemark() {
                return remark;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public String getTitle() {
                return title;
            }

            public int getType() {
                return type;
            }

            public int getNumber() {
                return number;
            }

            public int getUserStatue() {
                return userStatue;
            }

            public String getCreateTime() {
                return createTime;
            }

            public String getDetail() {
                return detail;
            }

            public int getId() {
                return id;
            }

            public int getStatus() {
                return status;
            }
        }
    }
}
