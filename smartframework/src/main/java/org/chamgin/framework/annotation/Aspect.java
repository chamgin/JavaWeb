package org.chamgin.framework.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE) //应用在类上
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
