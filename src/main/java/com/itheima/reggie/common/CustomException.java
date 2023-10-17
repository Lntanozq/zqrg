package com.itheima.reggie.common;

import com.sun.xml.internal.ws.protocol.soap.MessageCreationException;

/**
 * 自定义业务异常类
 */
public class CustomException extends RuntimeException{

    public CustomException(String msg){
        super(msg);
    }
}
