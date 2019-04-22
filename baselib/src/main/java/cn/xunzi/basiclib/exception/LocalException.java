package cn.xunzi.basiclib.exception;

import com.alibaba.fastjson.JSON;

/**
 * @author vane
 */
public class LocalException extends Exception {

    private int code = -1;
    private int errorCode = -1;
    private String msg;
    private Object extra;

    public LocalException(String message) {
        super(message);
        this.msg = message;
    }

    public LocalException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public LocalException(Throwable throwable, String message) {
        super(throwable);
        this.msg = message;
    }

    public LocalException(Throwable throwable, int code, String message) {
        super(throwable);
        this.code = code;
        this.msg = message;
    }

    public int getCode() {
        return code;
    }

    public LocalException setCode(int code) {
        this.code = code;
        return this;
    }

    /**
     * 获取异常信息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param message 异常信息
     */
    public LocalException setMsg(String message) {
        this.msg = message;
        return this;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getExtra() {
        return extra == null ? null : JSON.toJSONString(extra);
    }

    public LocalException setExtra(Object extra) {
        this.extra = extra;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder()
                .append("LocalException{")
                .append("code='").append(code).append('\'')
                .append(", message='").append(msg).append('\'');
        if (errorCode != -1) {
            output.append(", errorCode='").append(errorCode).append('\'');
        }
        if (extra != null) {
            output.append(", extra='").append(extra).append('\'');
        }
        output.append(", super=").append(super.toString())
                .append('}');

        return output.toString();
    }

}
