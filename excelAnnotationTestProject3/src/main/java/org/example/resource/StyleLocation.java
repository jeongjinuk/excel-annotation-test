package org.example.resource;

import org.example.BodyStyle;
import org.example.HeaderStyle;
import org.example.templates.style.StyleTemplate;

import java.lang.annotation.Annotation;
import java.util.function.Function;

public enum StyleLocation {
    HEADER(HeaderStyle.class, annotation -> ((HeaderStyle) annotation).style()),
    BODY(BodyStyle.class, annotation -> ((BodyStyle) annotation).style());

    private Class<? extends Annotation> type;
    private Function<Annotation,Class<? extends StyleTemplate>> styleTemplate;

    StyleLocation(Class<? extends Annotation> type,
                  Function<Annotation, Class<? extends StyleTemplate>> styleTemplate) {
        this.type = type;
        this.styleTemplate = styleTemplate;
    }

    public Class<? extends Annotation> getType(){
        return type;
    }

    public Class<? extends StyleTemplate> getStyleClassType(Annotation annotation){
        return styleTemplate.apply(annotation);
    }
}
