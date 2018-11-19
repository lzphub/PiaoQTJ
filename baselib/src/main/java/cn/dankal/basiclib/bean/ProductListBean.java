package cn.dankal.basiclib.bean;

import java.util.List;

public class ProductListBean {


    /**
     * total : 1
     * per_page : 20
     * current_page : 1
     * last_page : 1
     * data : [{"uuid":"59da79e07d8ec0d725050acddf42ae9d","name":"111商品测试","price":"30.00","images":["图片1","img2","img3"],"description":"没啥"}]
     * tag : [null]
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
         * uuid : 59da79e07d8ec0d725050acddf42ae9d
         * name : 111商品测试
         * price : 30.00
         * images : ["图片1","img2","img3"]
         * description : 没啥
         */

        private String product_uuid;
        private String name;
        private String price;
        private String description;
        private List<String> images;

        public String getProduct_uuid() {
            return product_uuid;
        }

        public void setProduct_uuid(String product_uuid) {
            this.product_uuid = product_uuid;
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
