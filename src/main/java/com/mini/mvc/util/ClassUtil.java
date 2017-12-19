package com.mini.mvc.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
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
     * jar文件后缀
     */
    private static final String JAR_SUFFIX = ".jar";

    /**
     * 内部类识别符号
     */
    private static final String INNER_CLASS_IDENTIFY = "$";
    /**
     * jar包识别
     */
    private static final String JAR_IDENTIFY = "!";

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
            Enumeration<URL> resources = getCurrentClassLoader().getResources("com/sample/controller");
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                String protocol = url.getProtocol();
                if (RESOURCE_TYPE_FILE.equals(protocol)) {
                    String packagePath = url.getPath().replaceAll("%20", " ");
                    // 递归添加class文件
                    addClass(classList, packagePath, scanPackage);
                } else if (RESOURCE_TYPE_JAR.equals(protocol)) {
                    //                    String packagePath = url.getPath().replaceAll("%20", " ");
                    //                    addJarClass(classList, packagePath, scanPackage);
                }
            }

        } catch (Exception e) {
            log.error("资源包扫描错误", e);
        }

        return classList;
    }

    /**
     * TODO 解析第三方jar包
     *
     * @param classList
     * @param path
     * @param basePackage
     */
    private static void addJarClass(List<Class<?>> classList, String path, String basePackage) {
        try {
            String[] jarInfo = path.split(JAR_IDENTIFY);
            String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf(DIR_PATH_SPLITE));
            String packagePath = jarInfo[1].substring(1);
            File file = new File(jarFilePath);
            String jarPath = file.toURI().getPath();
            JarFile jar = new JarFile(file);

            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String jarpathname = jarEntry.getName(); // 类型 sun/xx/xxx.class
                if (validateClassName(jarpathname)) {
                    String className = jarpathname.replace(DIR_PATH_SPLITE, PACKAGE_PATH_SPLITE).substring(0, jarpathname.lastIndexOf("."));
                    if (StringUtils.isNotBlank(path)) {
                        className = jarPath + PACKAGE_PATH_SPLITE + className;
                    }
                    Class<?> clazz = loadClass(className);
                    classList.add(clazz);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 递归添加 path下的所有class文件
     *
     * @param classList
     * @param path 扫描的包路径 --> 系统绝对路径
     * @param basePackage 类所在的包路径
     */
    private static void addClass(List<Class<?>> classList, String path, String basePackage) {
        // 只取class文件(排除内部类)和子包
        File[] files = new File(path).listFiles(file -> (checkLegalClass(file)) || file.isDirectory());
        if (files == null || files.length == 0) {
            return;
        }
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

    /**
     * 筛选合法的类文件 --> class结尾且不是内部类
     *
     * @return
     */
    private static boolean checkLegalClass(File file) {
        System.out.println(file.getName());
        return file.isFile() && validateClassName(file.getName());
    }

    /**
     * 验证类名
     *
     * @param classname
     * @return
     */
    private static boolean validateClassName(String classname) {
        Preconditions.checkNotNull(classname);
        return classname.endsWith(CLASS_SUFFIX) && !classname.contains(INNER_CLASS_IDENTIFY);
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
