package org.example.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.example.A;
import org.example.annotation.ExcelColumn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class SimpleExcelFileTest {
    @DisplayName("Workbook 만들기")
    void workBookOutput(Workbook workbook){
        StringBuilder stringBuilder = new StringBuilder(System.getProperty("user.dir")).append("\\src\\test\\resources");
        stringBuilder.append(File.separator);
        try (OutputStream fileOut = new FileOutputStream(stringBuilder.append("workbook.xlsx").toString())){
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Excel file test")
    void abstractCarTest() throws IOException {
        Workbook xlsx = new SXSSFWorkbook();
        List<A> collect = IntStream.rangeClosed(0, 10)
                .mapToObj(value -> new A("" + value, "히ㅣ히" + value, value))
                .collect(Collectors.toList());
        SimpleExcelFile<A> excelFile = new SimpleExcelFile<>(xlsx, collect, A.class);
        workBookOutput(xlsx);
        xlsx.close();
    }

    @Test
    void ExcelFileVersionTest() throws IllegalAccessException {
        Field[] declaredFields = A.class.getDeclaredFields();
        List<A> collect = IntStream.rangeClosed(0, 10)
                .mapToObj(value -> new A("" + value, "히ㅣ히" + value, value))
                .collect(Collectors.toList());

        LinkedHashMap<String, String> fieldDataMap = new LinkedHashMap<>();
        ArrayList<String> fields = new ArrayList<>();


        for (Field declaredField :  declaredFields) {
            if (declaredField.isAnnotationPresent(ExcelColumn.class)){
                ExcelColumn annotation = declaredField.getAnnotation(ExcelColumn.class);
                fields.add(declaredField.getName());
                fieldDataMap.put(declaredField.getName(), annotation.headerName());
            }

/*            System.out.println(annotation.headerName());
            System.out.println(annotation.columnIndex());
            System.out.println(declaredField.getName());
//            declaredField.setAccessible(true);
            System.out.println(declaredField.getName());
            System.out.println(declaredField.get(znznznznznz));*/
            // annotation 만있으면 값 정보는 얻을 수 있다.
        }
        for (String field : fields) {
            try {
                Field field1 = getField(A.class, field);
                collect.forEach(a -> {
                    try {
                        System.out.println(field1.get(a));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        System.out.println("--------");

    }

    public Field getField(Class<?> clazz, String fieldName) throws Exception{
        for (Field declaredField : clazz.getDeclaredFields()) {
            if (declaredField.getName().equals(fieldName)){
                return clazz.getDeclaredField(fieldName);
            }
        }
        throw new NoSuchFieldException();
    }

    List<Class<?>> classTest(Class<?> t){
        List<Class<?>> classes = new ArrayList<>();
        while (t != null){
            classes.add(t);
            t = t.getSuperclass();
        }
        return classes;
    }

}

