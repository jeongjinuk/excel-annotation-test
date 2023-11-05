package org.celper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * String 타입의 Field에 적용가능
 * 왜냐하면 숫자가 0일 경우도 처리하기 때문에 null자체는 존재하지 않는건데
 * int나 double 은 0 또는 0.0으로 기본값이 나옴
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultValue {
    String value();
}
