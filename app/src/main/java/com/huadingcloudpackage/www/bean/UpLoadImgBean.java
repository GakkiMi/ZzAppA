package com.huadingcloudpackage.www.bean;

public class UpLoadImgBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"imageUrl":"http://152.136.97.49/group1/M00/00/06/rBUADl7h-9CABEcmAATJM1FrS4I48.jpeghttp://152.136.97.49/group1/M00/00/06/rBUADl7h-9GAHU1HAAF19RQOpX440.jpeg","thumbImageUrl":"http://152.136.97.49/group1/M00/00/06/rBUADl7h-9CABEcmAATJM1FrS4I48_200x150.jpeghttp://152.136.97.49/group1/M00/00/06/rBUADl7h-9GAHU1HAAF19RQOpX440_200x150.jpeg"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * imageUrl : http://152.136.97.49/group1/M00/00/06/rBUADl7h-9CABEcmAATJM1FrS4I48.jpeghttp://152.136.97.49/group1/M00/00/06/rBUADl7h-9GAHU1HAAF19RQOpX440.jpeg
         * thumbImageUrl : http://152.136.97.49/group1/M00/00/06/rBUADl7h-9CABEcmAATJM1FrS4I48_200x150.jpeghttp://152.136.97.49/group1/M00/00/06/rBUADl7h-9GAHU1HAAF19RQOpX440_200x150.jpeg
         */

        private String imageUrl;
        private String thumbImageUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getThumbImageUrl() {
            return thumbImageUrl;
        }

        public void setThumbImageUrl(String thumbImageUrl) {
            this.thumbImageUrl = thumbImageUrl;
        }
    }
}
