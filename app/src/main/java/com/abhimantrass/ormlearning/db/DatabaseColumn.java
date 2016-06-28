package com.abhimantrass.ormlearning.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhuhenk on 3/7/2015.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DatabaseColumn {
    String columnName() default "";
    boolean isIgnore() default false;
    boolean isPrimaryKey()default false;
    boolean autoincrement() default false;
    boolean isNotNull() default false;
    boolean isUnique() default false;
}
