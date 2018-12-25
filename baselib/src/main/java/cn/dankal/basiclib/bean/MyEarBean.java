package cn.dankal.basiclib.bean;

import java.util.List;

public class MyEarBean {

    /**
     * self : {"uuid":"05a7286000d638f26ce7bfb77d9f7589","name":null,"avatar":null,"balance":"0.00","total_commission":"0.00","rank":2}
     * chart : [{"name":"ccc111","avatar":"头像","total_commission":"1000.23","rank":1},{"name":null,"avatar":null,"total_commission":"0.00","rank":2}]
     */

    private SelfBean self;
    private List<ChartBean> chart;

    public SelfBean getSelf() {
        return self;
    }

    public void setSelf(SelfBean self) {
        this.self = self;
    }

    public List<ChartBean> getChart() {
        return chart;
    }

    public void setChart(List<ChartBean> chart) {
        this.chart = chart;
    }

    public static class SelfBean {
        /**
         * uuid : 05a7286000d638f26ce7bfb77d9f7589
         * name : null
         * avatar : null
         * balance : 0.00
         * total_commission : 0.00
         * rank : 2
         */

        private String uuid;
        private Object name;
        private Object avatar;
        private String balance;
        private String total_commission;
        private int rank;
        private int has_withdrawal_pwd;

        public int getHas_withdrawal_pwd() {
            return has_withdrawal_pwd;
        }

        public void setHas_withdrawal_pwd(int has_withdrawal_pwd) {
            this.has_withdrawal_pwd = has_withdrawal_pwd;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getTotal_commission() {
            return total_commission;
        }

        public void setTotal_commission(String total_commission) {
            this.total_commission = total_commission;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }

    public static class ChartBean {
        /**
         * name : ccc111
         * avatar : 头像
         * total_commission : 1000.23
         * rank : 1
         */

        private String name;
        private String avatar;
        private String total_commission;
        private int rank;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTotal_commission() {
            return total_commission;
        }

        public void setTotal_commission(String total_commission) {
            this.total_commission = total_commission;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }
}
