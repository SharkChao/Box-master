package com.lenovohit.administrator.box_master;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lenovohit.administrator.box.connection.BoxConnection;
import com.lenovohit.administrator.box.crud.DataSupport;
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

        List<News>list =new ArrayList<>();
        list.add(news);
        list.add(news2);

//        DataSupport.saveAll(list);
//        DataSupport.deleteAll(News.class,"title = ? and conetnt = ?",new String[]{"新闻标题1","新闻内容1"});
//        news2.update();
        DataSupport.updateAll(list);
    }
}
