package com.qunyu.taoduoduo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/28.
 */

public class GetDrawCommentDetailsBean {
    /**
     * productImage : 测试内容8e22
     * productName : 测试内容3714
     * userInfo : [{"commentImage":[{"image":"测试内容5nqr"}],"commentText":"测试内容75vo","commentTime":"测试内容c1sh","userImage":"测试内容03ul","userName":"测试内容19kb"}]
     */

    private String productImage;
    private String productName;
    /**
     * commentImage : [{"image":"测试内容5nqr"}]
     * commentText : 测试内容75vo
     * commentTime : 测试内容c1sh
     * userImage : 测试内容03ul
     * userName : 测试内容19kb
     */

    private ArrayList<UserInfoBean> userInfo;

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ArrayList<UserInfoBean> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ArrayList<UserInfoBean> userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {
        private String commentText;
        private String commentTime;
        private String userImage;
        private String userName;
        /**
         * image : 测试内容5nqr
         */

        private ArrayList<CommentImageBean> commentImage;

        public String getCommentText() {
            return commentText;
        }

        public void setCommentText(String commentText) {
            this.commentText = commentText;
        }

        public String getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(String commentTime) {
            this.commentTime = commentTime;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public ArrayList<CommentImageBean> getCommentImage() {
            return commentImage;
        }

        public void setCommentImage(ArrayList<CommentImageBean> commentImage) {
            this.commentImage = commentImage;
        }

        public static class CommentImageBean {
            private String image;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}
