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

}