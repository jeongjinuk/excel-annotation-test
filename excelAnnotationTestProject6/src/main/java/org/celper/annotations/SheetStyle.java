package org.celper.annotations;

import org.celper.core.style.SheetStyleConfigurer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SheetStyle {
    Class<? extends SheetStyleConfigurer> value();
}
