package org.chamgin.framework;

import org.chamgin.framework.helper.BeanHelper;
import org.chamgin.framework.helper.ClassHelper;
import org.chamgin.framework.helper.ControllerHelper;
import org.chamgin.framework.helper.IocHelper;
import org.chamgin.framework.util.ClassUtil;

public final class HelperLoader {
    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for(Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(),false);
        }
    }
}
