package org.celper.util;

import org.celper.exception.ExcelException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReflectionUtilsTest {
    static class ReflectionUtilTestClass{
        private String field1;
        private int field2;
        private double field3;
        private Date field4;
        private LocalDateTime field5;
        private LocalDate field6;
        private LocalTime field7;
        private boolean field8;
    }
    static class PrivateClass{
        private PrivateClass(){}
    }
    @Test
    @DisplayName("클래스 필드 생성 - 생성되는 필드개수가 동일하면 통과")
    void getDeclaredFields() {
        int fieldCount = 8;
        List<Field> declaredFields = ReflectionUtils.getDeclaredFields(ReflectionUtilTestClass.class);
        assertFalse(declaredFields.isEmpty());
        assertEquals(fieldCount, declaredFields.size());
    }

    @Test
    @DisplayName("인스턴스 생성 테스트 - 인스턴스 생성이 완료되면 통과 ")
    void getInstance() {
        ReflectionUtilTestClass instance = ReflectionUtils.getInstance(ReflectionUtilTestClass.class);
        assertNotNull(instance);
    }

    @Test
    @DisplayName("예외 ExcelException - ReflectionUtil에서 던지는 예외")
    void exception(){
        assertThrows(ExcelException.class,() -> ReflectionUtils.getInstance(PrivateClass.class));
    }
}