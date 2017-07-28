package com.lenovohit.administrator.box.util;

import android.content.res.AssetManager;

import com.lenovohit.administrator.box.application.BoxApplication;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by SharkChao on 2017-07-26.
 */

public class BaseUtil {
    public static boolean isBoxXmlExists(){
        AssetManager assetManager = BoxApplication.getContext().getAssets();
        try {
            String[] fileNames = assetManager.list("");
            if (fileNames !=null && fileNames.length>0){
                for (String name:fileNames){
                    if (Constact.Config.CONFIG_FILE_NAME.equals(name)){
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断类和方法是否存在
     * @return
     */
    public static boolean isExistClassAndMethod(String className,String methodName){
        try {
            Class<?> aClass = Class.forName(className);
            Method[] methods = aClass.getMethods();
            for (Method method:methods){
                if (method.getName().equals(methodName)){
                    return true;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
