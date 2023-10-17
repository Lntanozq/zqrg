package com.itheima.reggie.common;

import com.itheima.reggie.entity.Employee;
import org.yaml.snakeyaml.events.Event;

/**
 * 基于ThreadLocal的一个工具类，用于保存登录的用户id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();
    public static void serCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
