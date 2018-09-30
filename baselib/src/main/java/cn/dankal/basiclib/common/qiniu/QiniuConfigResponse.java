package cn.dankal.basiclib.common.qiniu;

/**
 * description: 七牛配置
 *
 * @author vane
 * @since 2018/5/22
 */

public class QiniuConfigResponse {

    /**
     * url : https://cdn.dankal.cn/
     * token : Jyi6Ntprm38nI6n1heGjwXyQmzie8ZjY7l9Cq_Je:ho3g8smjvKjeB2cUDPZmHIhT7Rc=:eyJzY29wZSI6ImRhbmthbC1jZG4iLCJkZWFkbGluZSI6MTUyNjk4MTg5M30=
     */
    private String bucket_domain;
    private String token;

    public String getBucket_domain() {
        return bucket_domain;
    }

    public QiniuConfigResponse setBucket_domain(String bucket_domain) {
        this.bucket_domain = bucket_domain;
        return this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
