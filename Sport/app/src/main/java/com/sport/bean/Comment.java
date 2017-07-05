package com.sport.bean;

import java.io.Serializable;


public class Comment implements Serializable {
    private String headImg;
    private String nick;
    private String comment;
    private String toHeadImg;
    private String toNick;
    private String toComment;
    private String commentDate;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getToHeadImg() {
        return toHeadImg;
    }

    public void setToHeadImg(String toHeadImg) {
        this.toHeadImg = toHeadImg;
    }

    public String getToNick() {
        return toNick;
    }

    public void setToNick(String toNick) {
        this.toNick = toNick;
    }

    public String getToComment() {
        return toComment;
    }

    public void setToComment(String toComment) {
        this.toComment = toComment;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }
}
