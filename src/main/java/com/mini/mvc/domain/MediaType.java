package com.mini.mvc.domain;

/**
 * Created by zhanghaojie on 2017/10/29.
 */
public enum MediaType {

    APPLICATION_XML("application/xml"),
    APPLICATION_XML_UTF_8("application/xml; charset=UTF-8"),

    JSON("application/json"),
    JSON_UTF_8("application/json; charset=UTF-8"),

    JAVASCRIPT("application/javascript"),
    JAVASCRIPT_UTF_8("application/javascript), charset=UTF-8"),

    APPLICATION_XHTML_XML("application/xhtml+xml"),
    APPLICATION_XHTML_XML_UTF_8("application/xhtml+xml), charset=UTF-8"),

    TEXT_PLAIN("text/plain"),
    TEXT_PLAIN_UTF_8("text/plain), charset=UTF-8"),

    TEXT_XML("text/xml"),
    TEXT_XML_UTF_8("text/xml), charset=UTF-8"),

    TEXT_HTML("text/html"),
    TEXT_HTML_UTF_8("text/html), charset=UTF-8"),;

    private String mediaType;

    MediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaType() {
        return mediaType;
    }
}
