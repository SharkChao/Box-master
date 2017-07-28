package com.lenovohit.administrator.box.exceptions;

/**
 * Created by SharkChao on 2017-07-26.
 * 全局context 没有初始化时会抛出的错误
 */

public class GlobalException extends RuntimeException{
    //支持跨进程
    private static final long serialVersionUID = 1L;
    //自定义异常提示信息
    public static final String errorMessage = "Application context is null,maybe context is not initialize";

    public GlobalException(String errorMessage){
        super(errorMessage);
    }
}
