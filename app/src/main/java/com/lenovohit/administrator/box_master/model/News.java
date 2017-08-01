package com.lenovohit.administrator.box_master.model;

import com.lenovohit.administrator.box.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SharkChao on 2017-07-27.
 */

public class News extends DataSupport{

    private String title;
    private String conetnt;
    private String publsihData;
    private List<Comment>mCommentList = new ArrayList<>();
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConetnt() {
        return conetnt;
    }

    public void setConetnt(String conetnt) {
        this.conetnt = conetnt;
    }

    public String getPublsihData() {
        return publsihData;
    }

    public void setPublsihData(String publsihData) {
        this.publsihData = publsihData;
    }

    public List<Comment> getCommentList() {
        return mCommentList;
    }

    public void setCommentList(List<Comment> commentList) {
        mCommentList = commentList;
    }
}
