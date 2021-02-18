package com.huadingcloudpackage.www.bean;

import java.util.List;

public class UpLoadIVBean {

    /**
     * 单图返回json
     {
     "msg":"操作成功",
     "code":200,
     "imageUrl":"http://121.89.202.8:8001/group1/M00/00/20/rBp6pV_LRBKACExRAAgggwrKjao63.jpeg",
     "thumbImageUrl":"http://121.89.202.8:8001/group1/M00/00/20/rBp6pV_LRBKACExRAAgggwrKjao63_200x150.jpeg"
     }
     */
    /**
     * 多图返回json
      {
          "msg":"操作成功",
          "imgs":[
              {
                  "imageUrl":"http://121.89.202.8:8001/group1/M00/00/2B/rBp6pV_im22AHevqAAHqZoPoSX844.jpeg",
                  "thumbImageUrl":"http://121.89.202.8:8001/group1/M00/00/2B/rBp6pV_im22AHevqAAHqZoPoSX844_200x150.jpeg"
              },
              {
                  "imageUrl":"http://121.89.202.8:8001/group1/M00/00/2B/rBp6pV_im22ASH6JAAJSRADP9Qg89.jpeg",
                  "thumbImageUrl":"http://121.89.202.8:8001/group1/M00/00/2B/rBp6pV_im22ASH6JAAJSRADP9Qg89_200x150.jpeg"
              }
          ],
          "code":200
      }
     */

    private String msg;
    private int code;
    private String imageUrl;
    private String thumbImageUrl;
    private List<ImgsBean> imgs;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


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

  public List<ImgsBean> getImgs() {
        return imgs;
    }

    public void setImgs(List<ImgsBean> imgs) {
        this.imgs = imgs;
    }

    public static class ImgsBean {
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
