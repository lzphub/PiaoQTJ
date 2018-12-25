package cn.dankal.basiclib.bean;

import java.util.List;

public class MyIntentionBean {


    /**
     * total : 3
     * per_page : 20
     * current_page : 1
     * last_page : 1
     * data : [{"intention_id":"2018112813555423","product_name":"范鼎的蛋蛋","product_price":"9.90","images":["FngLrTlf3dIXi_uIwbVrV9yWEP-p","FuhRfAk-WNjdLudqw--zGOk-jti2","FhOPEH6Ia41tWD4zQARHVKUnXPhG","FvCuNd9kMK_VP0xHv-B9vONThLwl"],"status":1,"product_uuid":"7da4bfded9611eba30fee52db91d848e"},{"intention_id":"2018112831305087","product_name":"范鼎的蛋蛋","product_price":"9.90","images":["FngLrTlf3dIXi_uIwbVrV9yWEP-p","FuhRfAk-WNjdLudqw--zGOk-jti2","FhOPEH6Ia41tWD4zQARHVKUnXPhG","FvCuNd9kMK_VP0xHv-B9vONThLwl"],"status":1,"product_uuid":"7da4bfded9611eba30fee52db91d848e"},{"intention_id":"2018112863158386","product_name":"范鼎的蛋蛋","product_price":"9.90","images":["FngLrTlf3dIXi_uIwbVrV9yWEP-p","FuhRfAk-WNjdLudqw--zGOk-jti2","FhOPEH6Ia41tWD4zQARHVKUnXPhG","FvCuNd9kMK_VP0xHv-B9vONThLwl"],"status":1,"product_uuid":"7da4bfded9611eba30fee52db91d848e"}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<DataBean> data;

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

    public static class DataBean {
        /**
         * intention_id : 2018112813555423
         * product_name : 范鼎的蛋蛋
         * product_price : 9.90
         * images : ["FngLrTlf3dIXi_uIwbVrV9yWEP-p","FuhRfAk-WNjdLudqw--zGOk-jti2","FhOPEH6Ia41tWD4zQARHVKUnXPhG","FvCuNd9kMK_VP0xHv-B9vONThLwl"]
         * status : 1
         * product_uuid : 7da4bfded9611eba30fee52db91d848e
         */

        private String intention_id;
        private String product_name;
        private String product_price;
        private int status;
        private String product_uuid;
        private List<String> images;

        public String getIntention_id() {
            return intention_id;
        }

        public void setIntention_id(String intention_id) {
            this.intention_id = intention_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_price() {
            return product_price;
        }

        public void setProduct_price(String product_price) {
            this.product_price = product_price;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getProduct_uuid() {
            return product_uuid;
        }

        public void setProduct_uuid(String product_uuid) {
            this.product_uuid = product_uuid;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
