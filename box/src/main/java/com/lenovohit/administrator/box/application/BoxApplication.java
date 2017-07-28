package com.lenovohit.administrator.box.application;

import android.app.Application;
import android.content.Context;

import com.lenovohit.administrator.box.exceptions.GlobalException;

/**
 * Created by SharkChao on 2017-07-26.
 * 全局变量，方便之后调用
 */

public class BoxApplication extends Application{
    public static Context sContext;
    //获取到上下文对象
    public BoxApplication(){
        sContext = this;
    }
    public static Context getContext(){
        if (sContext == null){
            throw new GlobalException(GlobalException.errorMessage);
        }
        return sContext;
    }
}
