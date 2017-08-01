package com.lenovohit.administrator.box_master.model;

/**
 * Created by SharkChao on 2017-07-29.
 */

public class Comment {
   private  String content;
   private  String publish;
    private News mNews;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public News getNews() {
        return mNews;
    }

    public void setNews(News news) {
        mNews = news;
    }
}
