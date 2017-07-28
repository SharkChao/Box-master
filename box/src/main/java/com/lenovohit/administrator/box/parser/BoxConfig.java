package com.lenovohit.administrator.box.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SharkChao on 2017-07-26.
 * 数据库对象
 */

public class BoxConfig {
    private int version;
    private String dbName;
    private String cases;
    private String storage;
    private List<String> classNames;

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

    public List<String> getClassNames() {
        if (classNames == null) {
            classNames = new ArrayList<String>();
            classNames.add("com.lenovohit.administrator.box.model.Table_Schema");
        } else if (classNames.isEmpty()) {
            classNames.add("com.lenovohit.administrator.box.model.Table_Schema");
        }
        return classNames;
    }

    public void setClassNames(List<String> classNames) {
        this.classNames = classNames;
    }
    public void addClassName(String className){
        getClassNames().add(className);
    }

    @Override
    public String toString() {
        return "BoxConfig{" +
                "version=" + version +
                ", dbName='" + dbName + '\'' +
                ", cases='" + cases + '\'' +
                ", storage='" + storage + '\'' +
                ", classNames=" + classNames +
                '}';
    }
}
