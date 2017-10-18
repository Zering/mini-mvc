package com.mini.mvc.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhanghaojie on 2017/10/18.
 * properties文价读取工具类
 */
public class PropertiesUtil {

    /**
     * 默认配置文件后缀
     */
    private static final String SUFFIX = ".properties";

    private static final Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

    public static Properties getProperties(String filename) {
        if (StringUtils.isBlank(filename)) {
            throw new IllegalArgumentException();
        }

        if (filename.lastIndexOf(SUFFIX) == -1) {
            filename += SUFFIX;
        }

        Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            try {
                inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
                if (inputStream != null) {
                    properties.load(inputStream);
                }
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (Exception e) {
            log.error("配置文件{}加载出错", filename, e);
            throw new RuntimeException(e);
        }

        return properties;
    }

}
