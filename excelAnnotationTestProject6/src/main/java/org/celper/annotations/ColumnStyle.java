package org.celper.annotations;

import org.celper.core.style.CellStyleConfigurer;
import org.celper.core.style._NoCellStyle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnStyle {
    Class<? extends CellStyleConfigurer> headerAreaStyle() default _NoCellStyle.class;
    Class<? extends CellStyleConfigurer> dataAreaStyle() default _NoCellStyle.class;
}

