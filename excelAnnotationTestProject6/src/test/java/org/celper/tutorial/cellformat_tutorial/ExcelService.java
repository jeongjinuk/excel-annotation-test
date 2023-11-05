package org.celper.tutorial.cellformat_tutorial;

import org.celper.core.ExcelWorkBook;
import org.celper.type.WorkBookType;
import org.support.TestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
     * - 기존 시트
     * |    이름  |   주소     |   나이   |   생년월일    |
     * |  홍길동  |    00001   |   20    |   37928.5   |
     * |  김철수  |    00002   |   23    |   36615.5   |
     *
     * - 포멧 적용 시트
     * |    이름  |   주소     |   나이   |   생년월일    |
     * |  홍길동  |    00001   |   20(세)|   2003-11-03|
     * |  김철수  |    00002   |   23(세)|   2000-03-30|
     */
    @Test
    @DisplayName("Studen Model을 Excel Sheet로 변환")
    void modelToSheetService() throws IOException {
        ExcelWorkBook excelWorkBook = new ExcelWorkBook(WorkBookType.XSSF);

        List<StudentModel> byStudentModels = findByStudentModels();
        excelWorkBook.createSheet().modelToSheet(byStudentModels);

        OutputStream outputStream = TestSupport.workBookOutput("Format-Student.xlsx");
        excelWorkBook.write(outputStream);
    }
}
