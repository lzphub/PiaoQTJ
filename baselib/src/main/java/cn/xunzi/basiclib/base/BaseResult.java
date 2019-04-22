package cn.xunzi.basiclib.base;

/**
 * Date: 2018/7/30.
 * Time: 14:37
 * classDescription:
 *
 * @author fred
 */
public class BaseResult<T> {
    T result;

    public T getResult() {
        return result;
    }

    public BaseResult setResult(T result) {
        this.result = result;
        return this;
    }
}
