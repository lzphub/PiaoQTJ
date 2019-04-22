package cn.xunzi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * description: 生成Api注解
 *
 * @author vane
 * @since 2018/2/28
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ApiFactory {
    Class<?> value();

    String method() default "getRetrofit";
}
