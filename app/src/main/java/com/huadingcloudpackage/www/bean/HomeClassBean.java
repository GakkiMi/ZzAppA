package com.huadingcloudpackage.www.bean;

import java.util.List;

public class HomeClassBean {

    /**
     {
     "msg":"操作成功",
     "code":200,
     "data":[
     {
     "id":"3",
     "name":"海鲜",
     "mobileName":"",
     "parentId":"1",
     "parentIdPath":"",
     "level":3,
     "sortOrder":1,
     "visible":"0",
     "image":"",
     "isHot":0,
     "catGroup":0,
     "commission":0,
     "commissionRate":0,
     "typeId":0,
     "classCode":"",
     "storeId":"1",
     "yhsId":null,
     "yhsCode":null,
     "clsId":null,
     "createdBy":null,
     "createdTime":null,
     "updatedBy":null,
     "updatedTime":null
     },
     {
     "id":"GFL2020111400001",
     "name":"烧烤",
     "mobileName":"",
     "parentId":"1",
     "parentIdPath":"",
     "level":3,
     "sortOrder":50,
     "visible":"0",
     "image":"",
     "isHot":0,
     "catGroup":0,
     "commission":0,
     "commissionRate":0,
     "typeId":0,
     "classCode":"",
     "storeId":"1",
     "yhsId":null,
     "yhsCode":null,
     "clsId":null,
     "createdBy":"liman",
     "createdTime":"2020-11-14 15:28:57",
     "updatedBy":null,
     "updatedTime":null
     }
     ]
     }


     */

    private String msg;
    private int code;
    private List<ListBean> data;

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


    public List<ListBean> getData() {
        return data;
    }

    public void setData(List<ListBean> data) {
        this.data = data;
    }

    public static class ListBean {
        /**
         * id : 377
         * yhsId : null
         * yhsCode : null
         * name : 火火
         * mobileName : null
         * parentId : 375
         * parentName : 行行
         * parentIdPath : null
         * level : 3
         * sortOrder : 50
         * visible : 0
         * image : 
         * isHot : 0
         * catGroup : null
         * commission : null
         * commissionRate : null
         * typeId : 0
         * classCode : 
         * storeId : 223
         */

        private String id;
        private String name;
        private String mobileName;
        private String parentId;
        private String parentIdPath;
        private int level;
        private int sortOrder;
        private String visible;
        private String image;
        private int isHot;
        private int catGroup;
        private int commission;
        private int commissionRate;
        private int typeId;
        private String classCode;
        private String storeId;
        private String yhsId;
        private String yhsCode;
        private String clsId;
        private String createdBy;
        private String createdTime;
        private String updatedBy;
        private String updatedTime;

        private String parentName;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getVisible() {
            return visible;
        }

        public void setVisible(String visible) {
            this.visible = visible;
        }

        public int getCatGroup() {
            return catGroup;
        }

        public void setCatGroup(int catGroup) {
            this.catGroup = catGroup;
        }

        public int getCommission() {
            return commission;
        }

        public void setCommission(int commission) {
            this.commission = commission;
        }

        public int getCommissionRate() {
            return commissionRate;
        }

        public void setCommissionRate(int commissionRate) {
            this.commissionRate = commissionRate;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getClsId() {
            return clsId;
        }

        public void setClsId(String clsId) {
            this.clsId = clsId;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedTime() {
            return updatedTime;
        }

        public void setUpdatedTime(String updatedTime) {
            this.updatedTime = updatedTime;
        }

        public String getYhsId() {
            return yhsId;
        }

        public void setYhsId(String yhsId) {
            this.yhsId = yhsId;
        }

        public String getYhsCode() {
            return yhsCode;
        }

        public void setYhsCode(String yhsCode) {
            this.yhsCode = yhsCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobileName() {
            return mobileName;
        }

        public void setMobileName(String mobileName) {
            this.mobileName = mobileName;
        }


        public String getParentName() {
            return parentName;
        }

        public void setParentName(String parentName) {
            this.parentName = parentName;
        }

        public String getParentIdPath() {
            return parentIdPath;
        }

        public void setParentIdPath(String parentIdPath) {
            this.parentIdPath = parentIdPath;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getSortOrder() {
            return sortOrder;
        }

        public void setSortOrder(int sortOrder) {
            this.sortOrder = sortOrder;
        }



        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getIsHot() {
            return isHot;
        }

        public void setIsHot(int isHot) {
            this.isHot = isHot;
        }






        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getClassCode() {
            return classCode;
        }

        public void setClassCode(String classCode) {
            this.classCode = classCode;
        }


    }
}
