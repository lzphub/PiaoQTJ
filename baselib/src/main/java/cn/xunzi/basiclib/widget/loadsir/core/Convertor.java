package cn.xunzi.basiclib.widget.loadsir.core;


import cn.xunzi.basiclib.widget.loadsir.callback.Callback;

/**
 * Create Time:2017/9/4 8:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface Convertor<T> {
   Class<?extends Callback> map(T t);
}
