package cn.xunzi.basiclib.pojo;

/**
 * Date: 2018/7/17.
 * Time: 11:30
 * classDescription:
 *
 * @author fred
 */
public class UserInfoBean {


    /**
     * msg : 获取成功
     * code : 200
     * data : {"birthday":"","salt":"wm8kw","headImg":"","level":"0","idCode":"","nickName":"哈哈哈","referCode":"P3653","sex":1,"recommendUser":"2","updateTime":"2019-01-09 10:16:44","roleType":0,"realName":"","isAuth":0,"password":"84c1f0fd8c27d88e40f74bcb7791afd1","recommendCount":0,"createTime":"2019-01-07 14:52:00","teamTwo":0,"tel":"18122067260","id":3,"payCode":"","teamOne":0,"email":"","status":1}
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
         * birthday :
         * salt : wm8kw
         * headImg :
         * level : 0
         * idCode :
         * nickName : 哈哈哈
         * referCode : P3653
         * sex : 1
         * recommendUser : 2
         * updateTime : 2019-01-09 10:16:44
         * roleType : 0
         * realName :
         * isAuth : 0
         * password : 84c1f0fd8c27d88e40f74bcb7791afd1
         * recommendCount : 0
         * createTime : 2019-01-07 14:52:00
         * teamTwo : 0
         * tel : 18122067260
         * id : 3
         * payCode :
         * teamOne : 0
         * email :
         * status : 1
         */
        private String birthday;
        private String salt;
        private String headImg;
        private String level;
        private String idCode;
        private String nickName;
        private String referCode;
        private int sex;
        private String recommendUser;
        private String updateTime;
        private int roleType;
        private String realName;
        private int isAuth;
        private String password;
        private int recommendCount;
        private String createTime;
        private int teamTwo;
        private String tel;
        private int id;
        private String payCode;
        private int teamOne;
        private String email;
        private int status;

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public void setIdCode(String idCode) {
            this.idCode = idCode;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public void setReferCode(String referCode) {
            this.referCode = referCode;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public void setRecommendUser(String recommendUser) {
            this.recommendUser = recommendUser;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public void setRoleType(int roleType) {
            this.roleType = roleType;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public void setIsAuth(int isAuth) {
            this.isAuth = isAuth;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setRecommendCount(int recommendCount) {
            this.recommendCount = recommendCount;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setTeamTwo(int teamTwo) {
            this.teamTwo = teamTwo;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setPayCode(String payCode) {
            this.payCode = payCode;
        }

        public void setTeamOne(int teamOne) {
            this.teamOne = teamOne;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getBirthday() {
            return birthday;
        }

        public String getSalt() {
            return salt;
        }

        public String getHeadImg() {
            return headImg;
        }

        public String getLevel() {
            return level;
        }

        public String getIdCode() {
            return idCode;
        }

        public String getNickName() {
            return nickName;
        }

        public String getReferCode() {
            return referCode;
        }

        public int getSex() {
            return sex;
        }

        public String getRecommendUser() {
            return recommendUser;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public int getRoleType() {
            return roleType;
        }

        public String getRealName() {
            return realName;
        }

        public int getIsAuth() {
            return isAuth;
        }

        public String getPassword() {
            return password;
        }

        public int getRecommendCount() {
            return recommendCount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public int getTeamTwo() {
            return teamTwo;
        }

        public String getTel() {
            return tel;
        }

        public int getId() {
            return id;
        }

        public String getPayCode() {
            return payCode;
        }

        public int getTeamOne() {
            return teamOne;
        }

        public String getEmail() {
            return email;
        }

        public int getStatus() {
            return status;
        }
    }
}
