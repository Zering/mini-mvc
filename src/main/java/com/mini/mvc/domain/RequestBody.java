package com.mini.mvc.domain;

/**
 * Created by zhanghaojie on 2017/10/27.
 * 请求信息
 */
public class RequestBody {

    private RequestMethod requestMethod;

    private String requestPath;

    public RequestBody(RequestMethod requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestBody that = (RequestBody) o;

        if (requestMethod != that.requestMethod) return false;
        return requestPath != null ? requestPath.equals(that.requestPath) : that.requestPath == null;
    }

    @Override
    public int hashCode() {
        int result = requestMethod != null ? requestMethod.hashCode() : 0;
        result = 31 * result + (requestPath != null ? requestPath.hashCode() : 0);
        return result;
    }
}
