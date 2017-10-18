package com.mini.mvc;

import java.util.Properties;

import com.mini.mvc.util.PropertiesUtil;

/**
 * Created by zhanghaojie on 2017/10/18.
 * 配置文件
 */
public class Config {

    public static final String DEFAULT_HOME_PATH = "/index.html";

    public static final String DEFAULT_ENCODING = "UTF-8";

    private static final String PROPERTY_FILE_DEFAULT_NAME = "mini-mvc";

    private static final String PACKAGE_SCAN = "package.scan";

    private static final String PACKAGE_SCAN_DEFAULT = "";

    private static final Properties PROPERTIES = PropertiesUtil.getProperties(PROPERTY_FILE_DEFAULT_NAME);

    public static String getScanPackage() {
        return PROPERTIES.getProperty(PACKAGE_SCAN, PACKAGE_SCAN_DEFAULT);
    }

    public static String getPropertiesValue(String key) {
        return PROPERTIES.getProperty(key);
    }

}
