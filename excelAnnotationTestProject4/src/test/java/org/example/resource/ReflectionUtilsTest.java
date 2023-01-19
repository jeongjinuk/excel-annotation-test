package org.example.resource;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.time.StopWatch;
import org.example.ExcelColumn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
class ReflectionUtilsTest {
    @AllArgsConstructor
    class Car{
        @ExcelColumn(headerName = "field0", columnIndex = 0)
        private int field0;
        @ExcelColumn(headerName = "field1", columnIndex = 1)
        private int field1;
        @ExcelColumn(headerName = "field2", columnIndex = 2)
        private int field2;
        @ExcelColumn(headerName = "field3", columnIndex = 3)
        private int field3;
    }
    class SuperCar extends Car{
        @ExcelColumn(headerName = "field4", columnIndex = 4)
        private int field4;
        private int field5;
        public SuperCar(int field0, int field1, int field2, int field3, int field4, int field5) {
            super(field0, field1, field2, field3);
            this.field4 = field4;
            this.field5 = field5;
        }
    }

    @Test
    @DisplayName("리플렉션 정상 동작 여부 테스트")
    void findAllIncludingAnnotationFieldsTest() {
        String field5 = "field5";

        List<Field> allIncludingAnnotationFields = ReflectionUtils.findAllIncludingAnnotationFields(SuperCar.class, ExcelColumn.class);
        List<String> collect = allIncludingAnnotationFields.stream()
                .map(field -> field.getName())
                .collect(Collectors.toList());

        assertEquals(5, allIncludingAnnotationFields.size());
        assertFalse(collect.contains(field5));
    }

    @Test
    @DisplayName("어노테이션이 없는 경우 빈컬렉션 반환하는지 테스트")
    void emptyCollectionTest(){
        List<Field> allIncludingAnnotationFields = ReflectionUtils.findAllIncludingAnnotationFields(ReflectionUtils.class, ExcelColumn.class);
        assertTrue(allIncludingAnnotationFields.isEmpty());
    }

    @DisplayName("수행시간 parallel 하고 아니고 테스트하려고 만든것, for문으로 만든거랑도 바교했는데 이게 가독성이 더 좋아서 이걸 사용하기로함")
    void timeTest(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ReflectionUtils.findAllIncludingAnnotationFields(SuperCar.class, ExcelColumn.class);
        stopWatch.stop();
    }
}