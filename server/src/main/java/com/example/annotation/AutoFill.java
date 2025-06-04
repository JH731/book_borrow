package com.example.annotation;

import com.example.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //设置这个注解里面属性值可以取的有哪些,用于进行公共字段填充的操作只有insert和update
    //这里使用一个枚举类,它里面的两个成员就是insert和update
    OperationType value();//这个Value方法的作用应该就是获取枚举类里面定义的属性了
}
