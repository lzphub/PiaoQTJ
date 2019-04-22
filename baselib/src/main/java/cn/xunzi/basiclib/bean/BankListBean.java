package cn.xunzi.basiclib.bean;

import java.io.Serializable;
import java.util.List;

public class BankListBean implements Serializable {

    /**
     * msg : 获取成功
     * code : 200
     * data : {"list":[{"idCard":"","name":"","bankNo":"","bankName":"","tel":"","id":"","userId":"","bankAddress":"","status":""}]}
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

    public class DataEntity implements Serializable  {
        /**
         * list : [{"idCard":"","name":"","bankNo":"","bankName":"","tel":"","id":"","userId":"","bankAddress":"","status":""}]
         */
        private List<ListEntity> list;

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public class ListEntity implements Serializable {
            /**
             * idCard :
             * name :
             * bankNo :
             * bankName :
             * tel :
             * id :
             * userId :
             * bankAddress :
             * status :
             */
            private String idCard;
            private String name;
            private String bankNo;
            private String bankName;
            private String tel;
            private String id;
            private String userId;
            private String bankAddress;
            private String status;

            public void setIdCard(String idCard) {
                this.idCard = idCard;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setBankNo(String bankNo) {
                this.bankNo = bankNo;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public void setBankAddress(String bankAddress) {
                this.bankAddress = bankAddress;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getIdCard() {
                return idCard;
            }

            public String getName() {
                return name;
            }

            public String getBankNo() {
                return bankNo;
            }

            public String getBankName() {
                return bankName;
            }

            public String getTel() {
                return tel;
            }

            public String getId() {
                return id;
            }

            public String getUserId() {
                return userId;
            }

            public String getBankAddress() {
                return bankAddress;
            }

            public String getStatus() {
                return status;
            }
        }
    }
}
