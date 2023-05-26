package org.uniandes.isis2304.core;

import java.lang.annotation.*;

@Documented @Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface FK {
    /** What class does it reffers to */
    Class<?> value();
}
