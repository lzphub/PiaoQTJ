package cn.xunzi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zero
 * <p>
 * Date: 2017-08-21.
 * Time: 15:40
 * <p>
 * className: CheckLogin
 * classDescription: 验证登录
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CheckLogin {

    // boolean required() default true;
    // String message() default "";

    // 页面跳转的请求码
    // ps: 如果需要使用带用请求值的页面跳转时  requesrCode >= 0
    int requesrCode() default -1;

}
