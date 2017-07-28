package com.lenovohit.administrator.box_master.application;

import com.facebook.stetho.Stetho;
import com.lenovohit.administrator.box.application.BoxApplication;
import com.lenovohit.administrator.box.parser.BoxAttrs;

/**
 * Created by SharkChao on 2017-07-26.
 */

public class MyApplication extends BoxApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        BoxAttrs.getInstance();
        Stetho.initializeWithDefaults(this);
    }
}
