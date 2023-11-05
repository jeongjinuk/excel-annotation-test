package org.celper.tutorial.exculded_header_option_tutorial;

import org.celper.core.ExcelWorkBook;
import org.celper.type.WorkBookType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.support.TestSupport;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ExcelService {

    List<StudentModel> findByStudentModels(){
        List<StudentModel> studentModels = new ArrayList<>();

        LocalDate date1 = LocalDate.of(2003, 11, 03);
        LocalDate date2 = LocalDate.of(2000, 3, 30);

        StudentModel student1 = new StudentModel("홍길동", "00001", LocalDate.now().getYear() - date1.getYear(), date1);
        StudentModel student2 = new StudentModel("김철수", "00002", LocalDate.now().getYear() - date2.getYear(), date2);

        studentModels.add(student1);
        studentModels.add(student2);
        return studentModels;
    }
    /**
     * multiModelToSheet에서도 지원
     * - 기존 시트
     * |    이름  |   주소     |   나이   |   생년월일    |
     * |  홍길동  |    00001   |   20    |   37928.5   |
     * |  김철수  |    00002   |   23    |   36615.5   |
     *
     * - excluded Header Option [생년월일] 적용
     * |    이름  |   주소     |   나이   |
     * |  홍길동  |    00001   |   20    |
     * |  김철수  |    00002   |   23    |
     */
    @Test
    @DisplayName("Studen Model에서 생년월일 제외 Excel Sheet로 변환")
    void modelToSheetSingleExcludedService() throws IOException {
        ExcelWorkBook excelWorkBook = new ExcelWorkBook(WorkBookType.XSSF);

        List<StudentModel> byStudentModels = findByStudentModels();

        excelWorkBook.createSheet().modelToSheet(this::excludedHeaders, byStudentModels);

        OutputStream outputStream = TestSupport.workBookOutput("excluded-Header-Single-Student.xlsx");
        excelWorkBook.write(outputStream);
    }
    boolean excludedHeaders(String header){
        return header.equals("생년월일");
    }

    @Test
    @DisplayName("Studen Model에서 생년월일, 이름 제외 Excel Sheet로 변환")
    void modelToSheetMultiExcludedService() throws IOException {
        ExcelWorkBook excelWorkBook = new ExcelWorkBook(WorkBookType.XSSF);

        List<StudentModel> byStudentModels = findByStudentModels();

        Predicate<String> excluded = s -> false;
        excluded = excluded
                .or(s -> s.equals("이름"))
                .or(s -> s.equals("생년월일"));

        excelWorkBook.createSheet().modelToSheet(excluded, byStudentModels);
//        아래의 코드도 가능
//        excelWorkBook.createSheet().modelToSheet(this::multiExcludedHeaders, byStudentModels);

        OutputStream outputStream = TestSupport.workBookOutput("excluded-Header-Multi-Student.xlsx");
        excelWorkBook.write(outputStream);
    }

    boolean multiExcludedHeaders(String header){
        return header.equals("이름") ||
                header.equals("생년월일");
    }

}
