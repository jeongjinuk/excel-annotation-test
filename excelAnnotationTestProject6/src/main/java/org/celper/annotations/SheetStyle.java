package org.celper.annotations;

import org.celper.core.style.SheetStyleConfigurer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Sheet style.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SheetStyle {
    /**
     * Value class.
     *
     * @return the class
     */
    Class<? extends SheetStyleConfigurer> value();
}
