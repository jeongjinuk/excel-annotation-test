package org.celper.tutorial.import_name_option_tutorial;

import static org.junit.jupiter.api.Assertions.*;

import org.celper.core.ExcelWorkBook;
import org.support.TestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExcelService {
    List<StudentModel> findByStudentModels(){
        List<StudentModel> studentModels = new ArrayList<>();
        LocalDate defaultDate = LocalDate.of(2023, 11, 03);

        LocalDate date1 = LocalDate.of(2003, 11, 03);
        LocalDate date2 = LocalDate.of(2000, 3, 30);

        StudentModel student1 = new StudentModel("홍길동", "00001", defaultDate.getYear() - date1.getYear(), date1);
        StudentModel student2 = new StudentModel("김철수", "00002", defaultDate.getYear() - date2.getYear(), date2);

        studentModels.add(student1);
        studentModels.add(student2);
        return studentModels;
    }
    /**
     * - 기존 시트
     * |    이름  |   주소     |   나이   |   생년월일    |
     * |  홍길동  |    00001   |   20    |   37928.5   |
     * |  김철수  |    00002   |   23    |   36615.5   |
     *
     */
    @Test
    @DisplayName("Studen Model을 Excel Sheet로 변환")
    void modelToSheetService() {
        List<StudentModel> expectedList = new ArrayList<>();

        LocalDate defaultDate = LocalDate.of(2023, 11, 03);
        LocalDate date1 = LocalDate.of(2003, 11, 03);
        LocalDate date2 = LocalDate.of(2000, 3, 30);

        StudentModel student1 = new StudentModel("홍길동", "00001", defaultDate.getYear() - date1.getYear(), date1);
        StudentModel student2 = new StudentModel("김철수", "00002", defaultDate.getYear() - date2.getYear(), date2);

        expectedList.add(student1);
        expectedList.add(student2);

        ExcelWorkBook excelWorkBook = new ExcelWorkBook(TestSupport.workBookInput("Import-Name-Student.xlsx"));
        List<StudentModel> resultModel = excelWorkBook.getSheetAt(0).sheetToModel(StudentModel.class);

        System.out.println("celper 모듈을 통해 나온 결과");
        resultModel.forEach(System.out :: println);
        System.out.println("실제 데이터 모델");
        expectedList.forEach(System.out :: println);

        assertEquals(expectedList, resultModel);
    }
}
