package cn.xunzi.basiclib.exception;

import android.net.ParseException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.ConnectException;

import retrofit2.HttpException;

/**
 * 异常格式化
 *
 * @author leaflc
 */

public class ExceptionHandle {

    /**
     * 处理异常
     *
     * @param e Throwable
     * @return 自定义的LocalException
     */
    public static Exception handleException(Throwable e) {
        LocalException ex;
        if (e instanceof LocalException) {
            ex = (LocalException) e;
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new LocalException(e, ERROR.HTTP_ERROR);
            ex.setErrorCode(httpException.code());
            try {
                String json = httpException.response().errorBody().string();
                HttpErrorResponse errorResponse = JSON.parseObject(json, HttpErrorResponse.class);
                String msg = errorResponse.getError().getMessage();
                ex.setMsg(msg);
            } catch (Exception exception) {
                ex.setMsg("网络错误");
            }

        } else if (e instanceof org.json.JSONException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new LocalException(e, ERROR.PARSE_ERROR);
            ex.setMsg("解析错误");

        } else if (e instanceof ConnectException) {
            ex = new LocalException(e, ERROR.NETWORD_ERROR);
            ex.setMsg("连接失败");

        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new LocalException(e, ERROR.SSL_ERROR);
            ex.setMsg("证书验证失败");

        } else if (e instanceof ConnectTimeoutException) {
            ex = new LocalException(e, ERROR.TIMEOUT_ERROR);
            ex.setMsg("连接超时");

        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new LocalException(e, ERROR.TIMEOUT_ERROR);
            ex.setMsg("连接超时");

        } else {
            ex = new LocalException(e, ERROR.UNKNOWN);
            ex.setMsg("网络异常");
        }
        return ex;
    }


    /**
     * 约定异常
     */
    interface ERROR {
        /**
         * 未知错误
         */
        int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        int TIMEOUT_ERROR = 1006;
    }

}
