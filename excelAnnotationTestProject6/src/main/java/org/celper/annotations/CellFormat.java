package org.celper.annotations;

import org.celper.type.BuiltinCellFormatType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CellFormat {
    BuiltinCellFormatType builtinFormat() default BuiltinCellFormatType.GENERAL;

    String customFormat() default "";
}
