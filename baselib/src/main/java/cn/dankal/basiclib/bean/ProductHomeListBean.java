package cn.dankal.basiclib.bean;

import java.util.List;

public class ProductHomeListBean {


    /**
     * total : 2
     * per_page : 20
     * current_page : 1
     * last_page : 1
     * data : [{"uuid":"7da4bfded9611eba30fee52db91d848e","name":"范鼎的蛋蛋","price":"9.90","images":["FngLrTlf3dIXi_uIwbVrV9yWEP-p","FuhRfAk-WNjdLudqw--zGOk-jti2","FhOPEH6Ia41tWD4zQARHVKUnXPhG","FvCuNd9kMK_VP0xHv-B9vONThLwl"],"description":"范鼎的蛋蛋"},{"uuid":"7064fe3bef9702806847c1a2b9f0ed10","name":"范鼎的饭","price":"9.90","images":["FuhRfAk-WNjdLudqw--zGOk-jti2","FngLrTlf3dIXi_uIwbVrV9yWEP-p","FvCuNd9kMK_VP0xHv-B9vONThLwl","FhOPEH6Ia41tWD4zQARHVKUnXPhG"],"description":"范鼎的饭"}]
     * tag : ["标签1","标签2"]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<DataBean> data;
    private List<String> tag;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
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

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public static class DataBean {
        /**
         * uuid : 7da4bfded9611eba30fee52db91d848e
         * name : 范鼎的蛋蛋
         * price : 9.90
         * images : ["FngLrTlf3dIXi_uIwbVrV9yWEP-p","FuhRfAk-WNjdLudqw--zGOk-jti2","FhOPEH6Ia41tWD4zQARHVKUnXPhG","FvCuNd9kMK_VP0xHv-B9vONThLwl"]
         * description : 范鼎的蛋蛋
         */

        private String uuid;
        private String name;
        private String price;
        private String description;
        private List<String> images;

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
