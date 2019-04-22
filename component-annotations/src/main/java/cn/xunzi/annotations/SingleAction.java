package cn.xunzi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by fred
 * <p>
 * Date: 2017-08-01.
 * Time: 下午12:09
 * <p>
 * className: onSingleClick
 * classDescription: View的单击事件拦截
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SingleAction {

    // 间隔时间
    long value() default 750L;

}