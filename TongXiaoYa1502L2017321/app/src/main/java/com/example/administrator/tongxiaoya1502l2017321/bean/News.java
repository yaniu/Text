package com.example.administrator.tongxiaoya1502l2017321.bean;

import java.util.List;

/**
 * 作者：仝晓雅 on 2017/3/21 08:53
 * 类的注释：
 */

public class News {
    private String cname;
    private List<NodesBean> nodes;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public List<NodesBean> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodesBean> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "News{" +
                "cname='" + cname + '\'' +
                ", nodes=" + nodes +
                '}';
    }

    public static class NodesBean {
        private String category_fid;
        private String category_fiid;
        private String category_ishot;
        private String category_lever;
        private String category_name;
        private String category_order;
        private String category_status;
        private String id;

        public String getCategory_fid() {
            return category_fid;
        }

        public void setCategory_fid(String category_fid) {
            this.category_fid = category_fid;
        }

        public String getCategory_fiid() {
            return category_fiid;
        }

        public void setCategory_fiid(String category_fiid) {
            this.category_fiid = category_fiid;
        }

        public String getCategory_ishot() {
            return category_ishot;
        }

        public void setCategory_ishot(String category_ishot) {
            this.category_ishot = category_ishot;
        }

        public String getCategory_lever() {
            return category_lever;
        }

        public void setCategory_lever(String category_lever) {
            this.category_lever = category_lever;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getCategory_order() {
            return category_order;
        }

        public void setCategory_order(String category_order) {
            this.category_order = category_order;
        }

        public String getCategory_status() {
            return category_status;
        }

        public void setCategory_status(String category_status) {
            this.category_status = category_status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "NodesBean{" +
                    "category_fid='" + category_fid + '\'' +
                    ", category_fiid='" + category_fiid + '\'' +
                    ", category_ishot='" + category_ishot + '\'' +
                    ", category_lever='" + category_lever + '\'' +
                    ", category_name='" + category_name + '\'' +
                    ", category_order='" + category_order + '\'' +
                    ", category_status='" + category_status + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }
    }
}
