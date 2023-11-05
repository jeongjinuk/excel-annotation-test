package org.celper.tutorial.style_tutorial.cell_style_tutorial;

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
     * SheetStyle 적용 이후
     * CellStyle 적용
     */
    @Test
    @DisplayName("Studen Model에 [이름, 주소] 항목에 HelloCellStyle 적용")
    void modelToSheetService() throws IOException {
        ExcelWorkBook excelWorkBook = new ExcelWorkBook(WorkBookType.XSSF);

        List<StudentModel> byStudentModels = findByStudentModels();
        excelWorkBook.createSheet().modelToSheet(byStudentModels);

        OutputStream outputStream = TestSupport.workBookOutput("Hello-Cell-Style-Student.xlsx");
        excelWorkBook.write(outputStream);
    }
}
