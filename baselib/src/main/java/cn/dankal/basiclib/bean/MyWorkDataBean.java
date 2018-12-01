package cn.dankal.basiclib.bean;

import java.util.List;

public class MyWorkDataBean {


    /**
     * plan : [{"plan_uuid":"21b346b6fceac3e430fe588dc899c973","plan_detail":"Jxhdbuxndjdvdkdbxidj","plan_images":["zm_154356605961005a7286000d638f26ce7bfb77d9f7589.png"],"status":4,"refuse_reason":null}]
     * cpl : [{"cpl_uuid":"1503f53887c83fe9a5c08c13419e8450","cpl_detail":"Ndudndjdbjdndidndjfbjdndjdnd","cpl_images":["zm_154357233578505a7286000d638f26ce7bfb77d9f7589.png","zm_154357233579005a7286000d638f26ce7bfb77d9f7589.png"],"status":6,"refuse_reason":null},{"cpl_uuid":"f42fff8bf5363d9b59c79223f4ba53e3","cpl_detail":"32413412523152153454352","cpl_images":["zm_154357185445605a7286000d638f26ce7bfb77d9f7589.png"],"status":6,"refuse_reason":null}]
     * project : {"uuid":"ae22e7225e55cfdc5daf45ed4248ec60","name":"zjx测试","start_price":"100.00","end_price":"1000.00","cpl_start_date":"2018-10-22","cpl_end_date":"2019-11-11","desc":"nothing need to say"}
     */

    private ProjectBean project;
    private List<PlanBean> plan;
    private List<CplBean> cpl;

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

    public List<CplBean> getCpl() {
        return cpl;
    }

    public void setCpl(List<CplBean> cpl) {
        this.cpl = cpl;
    }

    public static class ProjectBean {
        /**
         * uuid : ae22e7225e55cfdc5daf45ed4248ec60
         * name : zjx测试
         * start_price : 100.00
         * end_price : 1000.00
         * cpl_start_date : 2018-10-22
         * cpl_end_date : 2019-11-11
         * desc : nothing need to say
         */

        private String uuid;
        private String name;
        private String start_price;
        private String end_price;
        private String cpl_start_date;
        private String cpl_end_date;
        private String desc;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

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
         * plan_uuid : 21b346b6fceac3e430fe588dc899c973
         * plan_detail : Jxhdbuxndjdvdkdbxidj
         * plan_images : ["zm_154356605961005a7286000d638f26ce7bfb77d9f7589.png"]
         * status : 4
         * refuse_reason : null
         */

        private String plan_uuid;
        private String plan_detail;
        private int status;
        private Object refuse_reason;
        private List<String> plan_images;

        public String getPlan_uuid() {
            return plan_uuid;
        }

        public void setPlan_uuid(String plan_uuid) {
            this.plan_uuid = plan_uuid;
        }

        public String getPlan_detail() {
            return plan_detail;
        }

        public void setPlan_detail(String plan_detail) {
            this.plan_detail = plan_detail;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getRefuse_reason() {
            return refuse_reason;
        }

        public void setRefuse_reason(Object refuse_reason) {
            this.refuse_reason = refuse_reason;
        }

        public List<String> getPlan_images() {
            return plan_images;
        }

        public void setPlan_images(List<String> plan_images) {
            this.plan_images = plan_images;
        }
    }

    public static class CplBean {
        /**
         * cpl_uuid : 1503f53887c83fe9a5c08c13419e8450
         * cpl_detail : Ndudndjdbjdndidndjfbjdndjdnd
         * cpl_images : ["zm_154357233578505a7286000d638f26ce7bfb77d9f7589.png","zm_154357233579005a7286000d638f26ce7bfb77d9f7589.png"]
         * status : 6
         * refuse_reason : null
         */

        private String cpl_uuid;
        private String cpl_detail;
        private int status;
        private String refuse_reason;
        private List<String> cpl_images;

        public String getCpl_uuid() {
            return cpl_uuid;
        }

        public void setCpl_uuid(String cpl_uuid) {
            this.cpl_uuid = cpl_uuid;
        }

        public String getCpl_detail() {
            return cpl_detail;
        }

        public void setCpl_detail(String cpl_detail) {
            this.cpl_detail = cpl_detail;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRefuse_reason() {
            return refuse_reason;
        }

        public void setRefuse_reason(String refuse_reason) {
            this.refuse_reason = refuse_reason;
        }

        public List<String> getCpl_images() {
            return cpl_images;
        }

        public void setCpl_images(List<String> cpl_images) {
            this.cpl_images = cpl_images;
        }
    }
}
