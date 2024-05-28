package com.labgenomics.practice.api.model;

import java.util.Date;

public class BoardComment {
    private int boardCommentId;
    private int boardId;
    private int userIdx;
    private String content;
    private Date createAt;
    private String createBy;
    private Date modifiedAt;
    private String modifiedBy;

    public int getBoardCommentId() {
        return boardCommentId;
    }

    public void setBoardCommentId(int boardCommentId) {
        this.boardCommentId = boardCommentId;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}