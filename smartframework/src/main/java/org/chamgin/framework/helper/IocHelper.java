package org.chamgin.framework.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.chamgin.framework.annotation.Inject;
import org.chamgin.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

public final class IocHelper {
    static {
        //获取所有Bean类与实例的映射关系
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if(MapUtils.isNotEmpty(beanMap)) {
            for(Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                //bean和bean实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //bean类的所有成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                if(ArrayUtils.isNotEmpty(beanFields)) {
                    for(Field beanField : beanFields) {
                        //遍历当前成员是否有inject注解
                        if(beanField.isAnnotationPresent(Inject.class)) {
                            //根据类型得到对应的bean实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if(beanFieldInstance != null) {
                                //通过反射初始化beanfield的值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
