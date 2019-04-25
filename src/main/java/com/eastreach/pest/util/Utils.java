package com.eastreach.pest.util;

import org.joda.time.DateTime;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 常用静态工具类
 */
public class Utils {
    /**
     * 对象成员属性赋值
     */
    public static void copy(Object source, Object destination, List<String> excludes) throws Exception {
        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(), Object.class);
        PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();

        BeanInfo destinationBean = Introspector.getBeanInfo(destination.getClass(), Object.class);
        PropertyDescriptor[] destinationProperties = destinationBean.getPropertyDescriptors();

        for (int i = 0; i < sourceProperty.length; i++) {
            if (excludes.contains(sourceProperty[i].getName()) || sourceProperty[i].getReadMethod().invoke(source) == null) {
                continue;
            }
            for (int j = 0; j < destinationProperties.length; j++) {
                if (sourceProperty[i].getName().equals(destinationProperties[j].getName())) {
                    destinationProperties[j].getWriteMethod().invoke(destination, sourceProperty[i].getReadMethod().invoke(source));
                    break;
                }
            }
        }
    }


    /**
     */
    public static void copy(Object source, Object destination) throws Exception {
        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(), Object.class);
        PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();

        BeanInfo destinationBean = Introspector.getBeanInfo(destination.getClass(), Object.class);
        PropertyDescriptor[] destinationProperties = destinationBean.getPropertyDescriptors();

        for (int i = 0; i < sourceProperty.length; i++) {
            if (sourceProperty[i].getReadMethod().invoke(source) == null) {
                //空对象不赋值
                continue;
            }
            for (int j = 0; j < destinationProperties.length; j++) {
                if (sourceProperty[i].getName().equals(destinationProperties[j].getName())) {
                    destinationProperties[j].getWriteMethod().invoke(destination, sourceProperty[i].getReadMethod().invoke(source));
                    break;
                }
            }
        }
    }

    /**
     * 获取唯一流水号
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成随机密码
     */
    public static String randomPass() {
        String code = "";
        Random rand = new Random();//生成随机数
        for (int a = 0; a < 6; a++) {
            code += rand.nextInt(10);//生成6位验证码
        }
        return code;
    }

    /**
     * 生成随机订单号
     */
    public static String randomOrderId(){
        return  DateTime.now().toString("yyyyMMddHHmmss") + randomPass(); //订单号
    }
}
