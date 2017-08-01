package com.lenovohit.administrator.box.crud;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.lenovohit.administrator.box.connection.BoxConnection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by SharkChao on 2017-07-27.
 */

public class DataSupport {
    public long id;
    /**
     * 保存数据
     * @return
     */
    public  long  save() throws Exception {
        SQLiteDatabase db = BoxConnection.getDatabase();
        Class<? extends DataSupport> aClass = this.getClass();
        Field[] fields = aClass.getDeclaredFields();
        ContentValues contentValues = new ContentValues();
        for (int i = 0;i<fields.length;i++){
            Field field =fields[i];
            field.setAccessible(true);
            if (field.getType() == java.util.List.class){
                //属性值如果是list的话
                Type genericType = field.getGenericType();
                if (genericType == null)continue;
                if (genericType instanceof ParameterizedType){
                    ParameterizedType pt = (ParameterizedType) genericType;
                    //得到泛型里的class类型对象
                    Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
                    List list = (List) field.get(this);
                    List<String> childIdList = saveChild(genericClazz, list);
                    if (childIdList !=null && childIdList.size()>0){
                        String ids = childIdList.get(0);
                        if (childIdList.size()>1)
                            for (int k = 1;k<childIdList.size();k++){
                                ids =ids + ","+ childIdList.get(k);
                            }
                        contentValues.put(field.getName()+"Id",ids);
                    }
                }
            }else {
                try {
                    contentValues.put(field.getName(),field.get(this)+"");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        long insert = db.insert(aClass.getSimpleName(), null, contentValues);
        id = insert;
        return insert;
    }


    /**
     * 保存对象中list集合
     */
    private List<String> saveChild(Class<?>aClass,List list) {
        SQLiteDatabase db = BoxConnection.getDatabase();
        Field[] fields = aClass.getDeclaredFields();
        ContentValues contentValues = new ContentValues();
        List<String>childIdList = new ArrayList<>();
        for (int j = 0; j < list.size(); j++){
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                if (field.getType() == java.util.List.class) {
                    //属性值如果是list的话
                    Type genericType = field.getGenericType();
                    if (genericType == null) continue;
                    if (genericType instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType) genericType;
                        //得到泛型里的class类型对象
                        Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
                        try {
                            List list1 = (List) field.get(this);
                            List<String> childIdList1 = saveChild(genericClazz, list1);
                            if (childIdList1 !=null && childIdList1.size()>0){
                                String ids = childIdList1.get(0);
                                if (childIdList1.size()>1)
                                    for (int k = 1;k<childIdList1.size();k++){
                                        ids =ids + ","+ childIdList1.get(k);
                                    }
                                contentValues.put(field.getName()+"Id",ids);
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        saveChild(genericClazz, list);
                    }
                } else {
                    try {
                        if (field.getType().isAssignableFrom(DataSupport.class) ){
                            //此处应该存放父亲id，冲突
                            contentValues.put(field.getName()+"id", field.get(list.get(j)) + "");

                        }else {
                            contentValues.put(field.getName(), field.get(list.get(j)) + "");
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            long insert = db.insert(aClass.getSimpleName(), null, contentValues);
            childIdList.add(insert+"");
         }
         return childIdList;
    }

    /**
     * 保存数据集合
     */
   public static  <T extends DataSupport> void saveAll(Collection<T>collection){
       SQLiteDatabase db = BoxConnection.getDatabase();
       if (collection!=null && collection.size()>0){
           DataSupport[] array = collection.toArray(new DataSupport[0]);
           DataSupport object = array[0];
           Class<?> aClass = object.getClass();
           String tableName = aClass.getSimpleName();
           Field[] fields = aClass.getDeclaredFields();
           for (int j=0;j<array.length;j++){
               ContentValues contentValues = new ContentValues();
               DataSupport obj = array[j];
               for (int i = 0;i<fields.length;i++){
                   Field field = fields[i];
                   field.setAccessible(true);
                   try {
                       String columnName = field.getName();
                       String value = (String) field.get(obj);
                       contentValues.put(columnName,value);
                   } catch (IllegalAccessException e) {
                       e.printStackTrace();
                   }
               }
               long insert = db.insert(tableName, null, contentValues);
               obj.id = insert;
           }
       }
    }
    /**
     * 删除一条数据
     */
    public void delete(){
        SQLiteDatabase db = BoxConnection.getDatabase();
        Class<? extends DataSupport> aClass = this.getClass();
        String tableName = aClass.getSimpleName();
        int delete = db.delete(tableName, " id = ?", new String[]{this.id + ""});
        Log.e("tag",delete+"..........");
        id=0;
    }
    /**
     * 删除所有数据
     */
    public static void deleteAll(Class<?>aClass){
        SQLiteDatabase db = BoxConnection.getDatabase();
        String tableName = aClass.getSimpleName();
        db.delete(tableName,null,null);
    }
    /**
     * 根据条件删除相应的数据
     */
    public static void deleteAll(Class<?>aClass,String where,String[]string){
        SQLiteDatabase db = BoxConnection.getDatabase();
        String tableName = aClass.getSimpleName();
        db.delete(tableName,where,string);
    }
    /**
     * 修改一条数据
     */
    public void update(){
        SQLiteDatabase db = BoxConnection.getDatabase();
        Class<? extends DataSupport> aClass = this.getClass();
        Class<?> superclass = aClass.getSuperclass();
        String tableName = aClass.getSimpleName();
        Field[] fields = aClass.getDeclaredFields();
        ContentValues contentValues = new ContentValues();
        for (Field field:fields){
            field.setAccessible(true);
            try {
                if (field.getName().equals("id")){
                    contentValues.put(field.getName(), (Long) superclass.getDeclaredField("id").get(this));
                }else {
                    contentValues.put(field.getName(), (String) field.get(this));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        db.update(tableName,contentValues," id = ?",new String[]{this.id+""});
    }
    /**
     * 修改数据集合,集合中存放的必须是更新好的数据
     */
    public static <T extends DataSupport> void updateAll(Collection<T>collection){
        SQLiteDatabase db = BoxConnection.getDatabase();
        DataSupport[] array = collection.toArray(new DataSupport[0]);
        DataSupport dataSupport = array[0];
        Class<? extends DataSupport> aClass = dataSupport.getClass();
        String tableName = aClass.getSimpleName();
        for (int i = 0;i<array.length;i++){
            DataSupport support = array[i];
            Class<?> superclass = support.getClass().getSuperclass();
            Class<? extends DataSupport> cls = support.getClass();
            Field[] fields = cls.getDeclaredFields();
            ContentValues contentValues = new ContentValues();
            long mId = 0;
            for (Field field:fields){
                field.setAccessible(true);
                try{
                    if (field.getName().equals("id")){
                        mId = (Long) superclass.getDeclaredField("id").get(support);
                        contentValues.put(field.getName(), mId);
                    }else {
                        contentValues.put(field.getName(),(String) field.get(support));
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            try {
                db.update(tableName,contentValues,"id = ?",new String[]{mId+""});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 根据条件更新数据集合  更新方法是在此方法中完成
     */
    public static void updateAll(Class<?>aClass, ContentValues contentValues,String where,String[] values){
        SQLiteDatabase db = BoxConnection.getDatabase();
        String tableName = aClass.getSimpleName();
        db.update(tableName,contentValues,where,values);
    }

    /**
     * 查询满足条件的数据
     * @param aClass
     * @param where
     * @param values
     */
    public static <T>List<T> findAll(Class<T>aClass,String where,String[]values){
        SQLiteDatabase db = BoxConnection.getDatabase();
        String tableName = aClass.getSimpleName();
        Field[] fields = aClass.getDeclaredFields();
        Cursor cursor = null;
        if (!TextUtils.isEmpty(where)){
             cursor = db.rawQuery("select * from " + tableName + " where " + where, values);
        }else {
            cursor = db.rawQuery("select * from " + tableName,null);
        }

        List<T>list=new ArrayList<>();
        while(cursor.moveToNext()){
            try {
                T object = (T) aClass.newInstance();
                for (Field field:fields){
                    field.setAccessible(true);
                    String column = field.getName();
                    String value = cursor.getString(cursor.getColumnIndex(column));
                    if (field.getName().equals("id")){
                        aClass.getSuperclass().getDeclaredField("id").set(object,Long.parseLong(value));
                    }else {
                        field.set(object,value);
                    }
                }
                list.add(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    /**
     * 通过id查找一条数据
     */
    public static <T> T find(Class<T>aClass,int id){
        SQLiteDatabase db = BoxConnection.getDatabase();
        String tableName = aClass.getSimpleName();
        Cursor cursor = db.rawQuery("select * from " + tableName + " where id = ? ", new String[]{id + ""});
        try {
            T t = aClass.newInstance();
            while (cursor.moveToNext()){
                Field[] fields = aClass.getDeclaredFields();
                for (Field field:fields){
                    field.setAccessible(true);
                    String column = field.getName();
                    String value = cursor.getString(cursor.getColumnIndex(column));
                    if (field.getName().equals("id")){
                        aClass.getSuperclass().getDeclaredField("id").set(t,Long.parseLong(value));
                    }else {
                        field.set(t,value);
                    }
                }
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 查询第一条数据
     */
    public static <T> T findFirst(Class<T>aClass){
        SQLiteDatabase db = BoxConnection.getDatabase();
        String tableName = aClass.getSimpleName();

        List<T> all = findAll(aClass, null, null);
        if (all ==null || all.size()<=0){
            return null;
        }
        T t1 = all.get(0);
        Class<?> aClass1 = t1.getClass();
        long s = 0;
        try {
            Field ids = aClass1.getSuperclass().getDeclaredField("id");
            ids.setAccessible(true);
             s = (Long) ids.get(t1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.rawQuery("select * from " + tableName + " where id = ?", new String[]{s+""});
        T t = null;
        while(cursor.moveToNext()){
            try {
                t = aClass.newInstance();
                Field[] fields = aClass.getDeclaredFields();
                for (Field field:fields){
                    field.setAccessible(true);
                    String value = cursor.getString(cursor.getColumnIndex(field.getName()));
                    if (field.getName().equals("id")){
                        aClass.getSuperclass().getDeclaredField("id").set(t,Long.parseLong(value));
                    }else {
                        field.set(t,value);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return t;
    }
    /**
     * 查询最后一条数据
     */
    public static <T> T findLast(Class<T>aClass){
        SQLiteDatabase db = BoxConnection.getDatabase();
        String tableName = aClass.getSimpleName();
        List<T> all = findAll(aClass, null, null);
        if (all ==null || all.size()<=0){
            return null;
        }
        T t1 = all.get(all.size() - 1);
        Class<?> aClass1 = t1.getClass();
        long  ids = 0;
        try {
             ids = (Long) aClass1.getSuperclass().getDeclaredField("id").get(t1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.rawQuery("select * from " + tableName + " where id = ? ", new String[]{ids+""});
        T t = null;
        while(cursor.moveToNext()){
            try {
                t = aClass.newInstance();
                Field[] fields = aClass.getDeclaredFields();
                for (Field field:fields){
                    field.setAccessible(true);
                    String value = cursor.getString(cursor.getColumnIndex(field.getName()));
                    if (field.getName().equals("id")){
                        aClass.getSuperclass().getDeclaredField("id").set(t,Long.parseLong(value));
                    }else {
                        field.set(t,value);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    /**
     * 判断当前对象是否保存到数中去了
     */
    public boolean isSaved(){
        return id>0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
