package cn.xunzi.basiclib.bean;

import java.io.Serializable;
import java.util.List;

public class TaskRecordBean implements Serializable {


    /**
     * msg : 获取成功
     * code : 200
     * data : [{"types":"","createTime":"2019-01-21 16:06:24","taskImage":"上传成功","updateTime":"2019-01-21 16:59:48","id":2,"taskTitle":"测试1","userId":"3","taskId":5,"status":1,"taskReward":12},{"types":"","createTime":"2019-01-21 16:15:27","taskImage":"","updateTime":"2019-01-21 16:59:57","id":3,"taskTitle":"提神醒脑茶","userId":"3","taskId":2,"status":0,"taskReward":0},{"types":"","createTime":"2019-01-23 15:00:38","taskImage":"","updateTime":"2019-01-23 15:00:38","id":30,"taskTitle":"提神醒脑茶","userId":"3","taskId":2,"status":0,"taskReward":0},{"types":"","createTime":"2019-01-23 15:02:51","taskImage":"/Weixun/static/upload/20190114/20190114170331_455.png","updateTime":"2019-01-23 15:02:51","id":31,"taskTitle":"测试1","userId":"3","taskId":5,"status":0,"taskReward":12}]
     */
    private String msg;
    private String code;
    private List<DataEntity> data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public class DataEntity implements Serializable {
        /**
         * types :
         * createTime : 2019-01-21 16:06:24
         * taskImage : 上传成功
         * updateTime : 2019-01-21 16:59:48
         * id : 2
         * taskTitle : 测试1
         * userId : 3
         * taskId : 5
         * status : 1
         * taskReward : 12
         */
        private String types;
        private String createTime;
        private String taskImage;
        private String updateTime;
        private int id;
        private String taskTitle;
        private String userId;
        private int taskId;
        private int status;
        private int taskReward;

        public void setTypes(String types) {
            this.types = types;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setTaskImage(String taskImage) {
            this.taskImage = taskImage;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setTaskTitle(String taskTitle) {
            this.taskTitle = taskTitle;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setTaskReward(int taskReward) {
            this.taskReward = taskReward;
        }

        public String getTypes() {
            return types;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getTaskImage() {
            return taskImage;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public int getId() {
            return id;
        }

        public String getTaskTitle() {
            return taskTitle;
        }

        public String getUserId() {
            return userId;
        }

        public int getTaskId() {
            return taskId;
        }

        public int getStatus() {
            return status;
        }

        public int getTaskReward() {
            return taskReward;
        }
    }
}
