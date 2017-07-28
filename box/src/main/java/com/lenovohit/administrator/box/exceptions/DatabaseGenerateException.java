package com.lenovohit.administrator.box.exceptions;

/**
 * Created by SharkChao on 2017-07-27.
 */

public class DatabaseGenerateException extends RuntimeException{
    public static String NOT_DATA_PERMISSION = "please your uses permission";
    public DatabaseGenerateException(String errorMessage){
        super(errorMessage);
    }
}
