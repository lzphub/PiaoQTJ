package cn.dankal.basiclib.bean;

import java.io.Serializable;
import java.util.List;

public class MyIdeaListBean {


    /**
     * total : 7
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"uuid":"1d9be5642930e451d230903d3879b0a2","title":"Hdndjdbdjdbjdb","detail":"Djdbdjdbjdbdjdndn","images":["zm_154518758036605a7286000d638f26ce7bfb77d9f7589.png","zm_154518759310105a7286000d638f26ce7bfb77d9f7589.png"],"status":1,"refuse_reason":null,"refuse_images":null},{"uuid":"d8c006f3c05a90ae35d2e63d59c969f4","title":"Hehjdbdjdbudhd","detail":"Dhdbudbdusbjdbjdbdjd","images":["zm_154518754634005a7286000d638f26ce7bfb77d9f7589.png","zm_154518755304505a7286000d638f26ce7bfb77d9f7589.png","zm_154518755730705a7286000d638f26ce7bfb77d9f7589.png"],"status":1,"refuse_reason":null,"refuse_images":null},{"uuid":"e431a09d0dc03a1d61c543de44d68cb7","title":"Udhshsusbdjd","detail":"Dhbdudbdudbdjdbhd","images":["zm_154518670225105a7286000d638f26ce7bfb77d9f7589.png","zm_154518670698405a7286000d638f26ce7bfb77d9f7589.png","zm_154518671170805a7286000d638f26ce7bfb77d9f7589.png"],"status":1,"refuse_reason":null,"refuse_images":null},{"uuid":"a2ecbba3ac7fb5c3e77ba22466a3d717","title":"Hshsbjsbshs","detail":"Djjsbdudbdudbdjdbh","images":["zm_154518463327005a7286000d638f26ce7bfb77d9f7589.png","zm_154518463822505a7286000d638f26ce7bfb77d9f7589.png","zm_154518465102605a7286000d638f26ce7bfb77d9f7589.png","zm_154518653487905a7286000d638f26ce7bfb77d9f7589.png","zm_154518653981305a7286000d638f26ce7bfb77d9f7589.png","zm_154518654524705a7286000d638f26ce7bfb77d9f7589.png"],"status":1,"refuse_reason":null,"refuse_images":null},{"uuid":"0a394142156464c57ee83b3277097a30","title":"Djidbdubxhhd","detail":"Udhdudbjdbhdudbsj","images":["zm_154518463327005a7286000d638f26ce7bfb77d9f7589.png","zm_154518463822505a7286000d638f26ce7bfb77d9f7589.png","zm_154518465102605a7286000d638f26ce7bfb77d9f7589.png"],"status":1,"refuse_reason":null,"refuse_images":null},{"uuid":"b34f3a7cc5bbab021becdf87c291dd60","title":"Hdjsbdjdbdhd","detail":"Jejdbdjdbdjxbxjxbxjxbxjxb","images":["zm_154512817519405a7286000d638f26ce7bfb77d9f7589.png","zm_154512818297505a7286000d638f26ce7bfb77d9f7589.png"],"status":1,"refuse_reason":null,"refuse_images":null},{"uuid":"3d3a0eb9bf18eb17923522d226452407","title":"Hsusbsjsb","detail":"Hxbdjdbdjdbdjdbxbzzj","images":["zm_154512803711805a7286000d638f26ce7bfb77d9f7589.png","zm_154512804189305a7286000d638f26ce7bfb77d9f7589.png","zm_154512804186605a7286000d638f26ce7bfb77d9f7589.png"],"status":1,"refuse_reason":null,"refuse_images":null}]
     */

    private int total;
    private String per_page;
    private int current_page;
    private int last_page;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPer_page() {
        return per_page;
    }

    public void setPer_page(String per_page) {
        this.per_page = per_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * uuid : 1d9be5642930e451d230903d3879b0a2
         * title : Hdndjdbdjdbjdb
         * detail : Djdbdjdbjdbdjdndn
         * images : ["zm_154518758036605a7286000d638f26ce7bfb77d9f7589.png","zm_154518759310105a7286000d638f26ce7bfb77d9f7589.png"]
         * status : 1
         * refuse_reason : null
         * refuse_images : null
         */

        private String uuid;
        private String title;
        private String detail;
        private int status;
        private String refuse_reason;
        private List<String> refuse_images;
        private List<String> images;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
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

        public List<String> getRefuse_images() {
            return refuse_images;
        }

        public void setRefuse_images(List<String> refuse_images) {
            this.refuse_images = refuse_images;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
