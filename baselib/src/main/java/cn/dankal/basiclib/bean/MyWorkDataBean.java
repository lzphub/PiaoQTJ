package cn.dankal.basiclib.bean;

import java.util.List;

public class MyWorkDataBean {

    private ProjectBean project;
    private List<PlanBean> plan;

    public ProjectBean getProject() {
        return project;
    }

    public void setProject(ProjectBean project) {
        this.project = project;
    }

    public List<PlanBean> getPlan() {
        return plan;
    }

    public void setPlan(List<PlanBean> plan) {
        this.plan = plan;
    }

    public static class ProjectBean {
        /**
         * name : 简单的工单
         * start_price : 20.00
         * end_price : 50.00
         * cpl_start_date : 2018-10-29
         * cpl_end_date : 2019-04-04
         * desc : 没啥可描述的
         */

        private String name;
        private String start_price;
        private String end_price;
        private String cpl_start_date;
        private String cpl_end_date;
        private String desc;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStart_price() {
            return start_price;
        }

        public void setStart_price(String start_price) {
            this.start_price = start_price;
        }

        public String getEnd_price() {
            return end_price;
        }

        public void setEnd_price(String end_price) {
            this.end_price = end_price;
        }

        public String getCpl_start_date() {
            return cpl_start_date;
        }

        public void setCpl_start_date(String cpl_start_date) {
            this.cpl_start_date = cpl_start_date;
        }

        public String getCpl_end_date() {
            return cpl_end_date;
        }

        public void setCpl_end_date(String cpl_end_date) {
            this.cpl_end_date = cpl_end_date;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public static class PlanBean {
        /**
         * plan_detail : 内容
         * plan_images : ["zm_1542717694390.png","zm_1542717694446.png","zm_1542717694399.png"]
         */

        private String plan_detail;
        private List<String> plan_images;

        public String getPlan_detail() {
            return plan_detail;
        }

        public void setPlan_detail(String plan_detail) {
            this.plan_detail = plan_detail;
        }

        public List<String> getPlan_images() {
            return plan_images;
        }

        public void setPlan_images(List<String> plan_images) {
            this.plan_images = plan_images;
        }
    }
}
