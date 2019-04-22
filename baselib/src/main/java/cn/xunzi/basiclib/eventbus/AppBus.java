package cn.xunzi.basiclib.eventbus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Fred on 2018/07/17.
 * https://www.jianshu.com/p/4eb68b206fe0
 */

public class AppBus {

    private static class LazyHolder{
        private static final AppBus instance = new AppBus();
    }

    private AppBus() {}

    public static final AppBus getInstance() {
        return LazyHolder.instance;
    }

    private final Subject<Object> bus = PublishSubject.create().toSerialized();

    private final Map<String, List<Disposable>> subscriptionArray = new LinkedHashMap<>();

    public void register(final Object observable) {
        boolean isRegisterSuccess = false;
        final String subscriptionKey = observable.getClass().getName();
        //反射获取方法列表
        Method[] methods = observable.getClass().getMethods();
        for (Method method : methods) {
            //如果方法不存在Subscribe注解则不做处理
            if (!method.isAnnotationPresent(Subscribe.class)) {
                continue;
            }
            final Method subscriptionMethod = method;
            //获取方法参数类型，即：MessageEvent.class
            Class<?> key = method.getParameterTypes()[0];
            //订阅MessageEvent.class类型实例，ofType作用已filter相近
            Disposable subscription = bus.ofType(key).subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object value) throws Exception {
                    try {
                        //反射赋值，即传递事件值
                        subscriptionMethod.setAccessible(true);
                        subscriptionMethod.invoke(observable, value);
                    } catch (Exception e) {
                        throw new RuntimeException(subscriptionKey + " isn't allowed to register!");
                    }
                }
            });
            //存储订阅实例列表，用于后续解除注册
            List<Disposable> subscriptions;
            if (subscriptionArray.containsKey(subscriptionKey)) {
                subscriptions = subscriptionArray.get(subscriptionKey);
            } else {
                subscriptions = new ArrayList<>();
            }
            subscriptions.add(subscription);
            subscriptionArray.put(subscriptionKey, subscriptions);
            isRegisterSuccess = true;
        }
        //如果注册的类中不存在Subscribe注解方法，则抛出异常提醒
        if (!isRegisterSuccess) {
            throw new RuntimeException(subscriptionKey + " has no any RxBuxSubscribe Event!");
        }
    }


    public void unregister(Object observable) {
        String subscriptionKey = observable.getClass().getName();
        if (!subscriptionArray.containsKey(subscriptionKey)) return;
        List<Disposable> subscriptions = subscriptionArray.get(subscriptionKey);
        for (Disposable subscription : subscriptions) {
            //如果已订阅，则取消订阅
            if (!subscription.isDisposed()) subscription.dispose();
        }
        subscriptionArray.remove(subscriptionKey);
    }

    public void post(Object event) {
        bus.onNext(event);
    }


}
