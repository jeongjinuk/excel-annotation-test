package org.celper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Column.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    /**
     * Value string.
     *
     * @return the string
     */
    String value();

    /**
     * Import name options string [ ].
     *
     * @return the string [ ]
     */
    String[] importNameOptions() default {""};

    /**
     * Priority int.
     *
     * @return the int
     */
    int priority() default 0;
}
