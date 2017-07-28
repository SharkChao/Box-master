package com.lenovohit.administrator.box.parser;

import android.text.TextUtils;

import com.lenovohit.administrator.box.exceptions.InitTableException;
import com.lenovohit.administrator.box.util.BaseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SharkChao on 2017-07-26.
 * 数据库中表格的属性，需要从xml中获取
 */

public class BoxAttrs {
    private static BoxAttrs sTableAttrs;
    private int version;
    private String dbName;
    private String cases;
    private String storage;
    private List<String> classNames;

    private BoxAttrs(){
    }
    //获取单例，并且解析xml
    public static BoxAttrs getInstance(){
        if (sTableAttrs == null){
            synchronized (BoxAttrs.class){
                if (sTableAttrs == null){
                   sTableAttrs = new BoxAttrs();
                    loadBoxXmlConfiguration();
                }
            }
        }
        return sTableAttrs;
    }
    //加载box.xml 并且把数据放置到当前对象中去
    private static void loadBoxXmlConfiguration(){
        if (BaseUtil.isBoxXmlExists()){
            BoxConfig boxConfig = BoxParser.parseXml();
            sTableAttrs.setDbName(boxConfig.getDbName());
            sTableAttrs.setClassNames(boxConfig.getClassNames());
            sTableAttrs.setCases(boxConfig.getCases());
            sTableAttrs.setVersion(boxConfig.getVersion());
            sTableAttrs.setStorage(boxConfig.getStorage());
        }
    }
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public void addClassName(String className) {
        getClassNames().add(className);
    }
    public void setClassNames(List<String> classNames) {
        this.classNames = classNames;
    }
    public List<String> getClassNames() {
        if (classNames == null) {
            classNames = new ArrayList<String>();
            classNames.add("org.litepal.model.Table_Schema");
        } else if (classNames.isEmpty()) {
            classNames.add("org.litepal.model.Table_Schema");
        }
        return classNames;
    }


    /**
     * 检查数据库是否存在
     */
    public void checkSelfValid(){
        if (TextUtils.isEmpty(dbName)){
            loadBoxXmlConfiguration();
            if (TextUtils.isEmpty(dbName)){
                throw  new InitTableException(InitTableException.TABLE_NOT_INIT);
            }
        }
    }
    @Override
    public String toString() {
        return "BoxAttrs{" +
                "version=" + version +
                ", dbName='" + dbName + '\'' +
                ", cases='" + cases + '\'' +
                ", storage='" + storage + '\'' +
                ", classNames=" + classNames +
                '}';
    }

}
