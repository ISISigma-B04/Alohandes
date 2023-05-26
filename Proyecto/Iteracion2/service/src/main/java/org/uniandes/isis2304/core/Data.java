package org.uniandes.isis2304.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface Data {
    /** Table name for the class entity */
    String value();
}
