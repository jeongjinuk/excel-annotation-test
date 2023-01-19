package org.example;

import org.example.templates.style.DefaultStyle;
import org.example.templates.style.StyleTemplate;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
    String headerName() default "";
    int columnIndex();
    HeaderStyle headerStyle() default @HeaderStyle(style = DefaultStyle.class);
//    Class<? extends StyleTemplate> bodyStyle() default DefaultStyle.class;
}
