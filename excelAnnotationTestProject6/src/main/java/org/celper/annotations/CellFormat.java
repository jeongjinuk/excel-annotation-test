package org.celper.annotations;

import org.celper.type.BuiltinCellFormatType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Cell format.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CellFormat {
    /**
     * Builtin format builtin cell format type.
     *
     * @return the builtin cell format type
     */
    BuiltinCellFormatType builtinFormat() default BuiltinCellFormatType.GENERAL;

    /**
     * Custom format string.
     *
     * @return the string
     */
    String customFormat() default "";
}
