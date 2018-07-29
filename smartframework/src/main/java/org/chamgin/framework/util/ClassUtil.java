package org.chamgin.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public final class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /*
    获取类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /*
    加载类
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        //TODO
        return null;
    }

    /*
    获取指定包名下的所有类
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        //TODO
        return null;
    }
}
