package com.mini.mvc.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * Created by zhanghaojie on 2017/10/28.
 * 查找class文价工具类
 */
public class ClassUtil {

    private static final Logger log = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 包类名分隔符
     */
    private static final String PACKAGE_PATH_SPLITE = ".";

    /**
     * 包目录分隔符
     */
    private static final String DIR_PATH_SPLITE = "/";

    /**
     * 扫描包下文件类型: file
     */
    private static final String RESOURCE_TYPE_FILE = "file";
    /**
     * 扫描包下文件类型: jar
     */
    private static final String RESOURCE_TYPE_JAR = "jar";

    /**
     * 文件后缀
     */
    private static final String CLASS_SUFFIX = ".class";

    /**
     * 获取包下被特定注解的所有类
     *
     * @param scanPackage 被扫描的包
     * @param annotation 筛选注解
     * @return
     */
    public static List<Class<?>> getClassLists(String scanPackage, Class<? extends Annotation> annotation) {
        return getClassLists(scanPackage).stream().filter(clazz -> clazz.isAnnotationPresent(annotation)).collect(Collectors.toList());
    }

    public static List<Class<?>> getClassLists(String scanPackage) {
        List<Class<?>> classList = Lists.newArrayList();
        try {
            Enumeration<URL> resources = getCurrentClassLoader().getResources(scanPackage.replace(PACKAGE_PATH_SPLITE, DIR_PATH_SPLITE));
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                String protocol = url.getProtocol();
                if (RESOURCE_TYPE_FILE.equals(protocol)) {
                    String packagePath = url.getPath().replaceAll("%20", " ");
                    // 递归添加class文件
                    addClass(classList, packagePath, scanPackage);
                }
                // TODO 解析包下的jar包
            }

        } catch (Exception e) {
            log.error("资源包扫描错误", e);
        }

        return classList;
    }

    /**
     * 递归添加 path下的所有class文件
     *
     * @param classList
     * @param path 扫描的包路径 --> 系统绝对路径
     * @param basePackage 类所在的包路径
     */
    private static void addClass(List<Class<?>> classList, String path, String basePackage) {
        // 只取class文件和子包
        File[] files = new File(path).listFiles(file -> (file.isFile() && file.getName().endsWith(CLASS_SUFFIX)) || file.isDirectory());
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = StringUtils.substringBeforeLast(fileName, CLASS_SUFFIX);
                if (StringUtils.isNotBlank(basePackage)) {
                    className = basePackage + PACKAGE_PATH_SPLITE + className;
                }
                Class<?> clazz = loadClass(className);
                classList.add(clazz);
            } else {
                // 子包
                String subPath = fileName;
                if (StringUtils.isNotBlank(path)) {
                    subPath = path + DIR_PATH_SPLITE + subPath;
                }

                // 类在子包里
                String subPackage = fileName;
                if (StringUtils.isNotBlank(basePackage)) {
                    subPackage = basePackage + PACKAGE_PATH_SPLITE + subPackage;
                }

                addClass(classList, subPath, subPackage);
            }
        }
    }

    private static Class<?> loadClass(String classname) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(classname, Boolean.FALSE, getCurrentClassLoader());
        } catch (Exception e) {
            log.error("加载类{}错误", classname, e);
        }

        return clazz;
    }

    private static ClassLoader getCurrentClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

}
