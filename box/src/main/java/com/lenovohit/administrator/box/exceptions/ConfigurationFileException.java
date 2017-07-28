package com.lenovohit.administrator.box.exceptions;

/**
 * Created by SharkChao on 2017-07-26.
 */

public class ConfigurationFileException extends RuntimeException{
    public static final String CONFIG_FILE_NOT_FIND = "box.xml is not find";
    public ConfigurationFileException(String errorMessage){
        super(errorMessage);
    }
}
