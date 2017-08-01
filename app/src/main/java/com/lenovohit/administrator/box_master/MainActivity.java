package com.lenovohit.administrator.box_master;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lenovohit.administrator.box.connection.BoxConnection;
import com.lenovohit.administrator.box_master.model.Comment;
import com.lenovohit.administrator.box_master.model.News;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BoxConnection.getDatabase();
        News news = new News();
        news.setConetnt("新闻内容000");
        news.setPublsihData(new Date()+"");
        news.setTitle("新闻标题000");
        news.setId(7);

        News news2 = new News();
        news2.setConetnt("新闻内容111");
        news2.setPublsihData(new Date()+"");
        news2.setTitle("新闻标题111");
        news2.setId(9);


        Comment comment = new Comment();
        comment.setContent("这是一条评论");
        comment.setPublish(new Date()+"");
        Comment comment2 = new Comment();
        comment2.setContent("这是第二条评论");
        comment2.setPublish(new Date()+"");
        List<Comment>commentList = new ArrayList<>();
        commentList.add(comment);
        commentList.add(comment2);
        news2.setCommentList(commentList);

//        List<News>list =new ArrayList<>();
//        list.add(news);
//        list.add(news2);

//        DataSupport.saveAll(list);

//        DataSupport.deleteAll(News.class,"title = ? and conetnt = ?",new String[]{"新闻标题1","新闻内容1"});
//        DataSupport.deleteAll(News.class);

//        news2.update();
//        DataSupport.updateAll(list);

//        DataSupport.selectAll(News.class, "id = ?", new String[]{"7"});

//        News first = DataSupport.find(News.class,1);
//        Log.e("tag",first.getTitle()+"");
        try {
            long save = news2.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
