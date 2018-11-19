package cn.dankal.basiclib.bean;

import java.util.List;

public class ProductClassifyBean {


    private List<RootBean> root;

    public List<RootBean> getRoot() {
        return root;
    }

    public void setRoot(List<RootBean> root) {
        this.root = root;
    }

    public static class RootBean {
        /**
         * uuid : 4a9d6c38ef08baea4aba47753a0095d7
         * name : 一级分类
         * image : 图片
         * parent_uuid : 0
         * children : [{"uuid":"97e811e5664bae6c192ee41f47c1a840","name":"二级分类3","image":"图片","parent_uuid":"4a9d6c38ef08baea4aba47753a0095d7"},{"uuid":"0f67b8871da0f10c89b02dcbf31ab811","name":"二级分类2","image":"图片","parent_uuid":"4a9d6c38ef08baea4aba47753a0095d7"},{"uuid":"2cb787c328d389895df1eca1f40e0f93","name":"二级分类1","image":"图片","parent_uuid":"4a9d6c38ef08baea4aba47753a0095d7"}]
         */

        private String uuid;
        private String name;
        private String image;
        private String parent_uuid;
        private List<ChildrenBean> children;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getParent_uuid() {
            return parent_uuid;
        }

        public void setParent_uuid(String parent_uuid) {
            this.parent_uuid = parent_uuid;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * uuid : 97e811e5664bae6c192ee41f47c1a840
             * name : 二级分类3
             * image : 图片
             * parent_uuid : 4a9d6c38ef08baea4aba47753a0095d7
             */

            private String uuid;
            private String name;
            private String image;
            private String parent_uuid;

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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getParent_uuid() {
                return parent_uuid;
            }

            public void setParent_uuid(String parent_uuid) {
                this.parent_uuid = parent_uuid;
            }
        }
    }
}
