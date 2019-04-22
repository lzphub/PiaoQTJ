package cn.xunzi.basiclib.bean;

import java.util.List;

public class UserHomeBannerBean {

    private List<CarouselsBean> carousels;

    public List<CarouselsBean> getCarousels() {
        return carousels;
    }

    public void setCarousels(List<CarouselsBean> carousels) {
        this.carousels = carousels;
    }

    public static class CarouselsBean {
        /**
         * name : 轮播图
         * image : image
         * jump_url : www.baidu.com
         */

        private String name;
        private String image;
        private String jump_url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getJump_url() {
            return jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = jump_url;
        }
    }
}
