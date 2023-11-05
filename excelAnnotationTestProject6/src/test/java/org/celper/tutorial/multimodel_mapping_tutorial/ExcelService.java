package org.celper.tutorial.multimodel_mapping_tutorial;

import org.celper.core.ExcelWorkBook;
import org.celper.tutorial.import_name_option_tutorial.StudentModel;
import org.celper.type.WorkBookType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.support.TestSupport;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExcelService {



    List<StudentViewModel> findByViewModels(){
        List<StudentViewModel> studentViewModels = new ArrayList<>();
        StudentViewModel student1 = new StudentViewModel("0001", "홍길동");
        StudentViewModel student2 = new StudentViewModel("0002", "김철수");
        studentViewModels.add(student1);
        studentViewModels.add(student2);
        return studentViewModels;
    }

    List<StudentInfoModel> findByInfoModels(){
        List<StudentInfoModel> studentInfoModels = new ArrayList<>();
        StudentInfoModel student1 = new StudentInfoModel("1번지", 20);
        StudentInfoModel student2 = new StudentInfoModel("2번지", 23);
        studentInfoModels.add(student1);
        studentInfoModels.add(student2);
        return studentInfoModels;
    }


    /**
     * 서로 다른 데이터 모델을 순서와 상관없이 결합하여 사용해야할 때가 있습니다.
     * 그에 대한 방법을 MultiModelToSheet를 이용할 수 있습니다.
     * 이 기능이 필요했던 이유는 다른 데이터를 결합하기 위하여 DTO를 만드는 작업이 불필요하다고 생각했습니다.
     * 불필요하다고 느끼는 데이터의 종류로는 일회성으로 쓰일 데이터일 때라고 할 수 있습니다.
     *
     * |    학번  |     이름    |           |  주소   |   나이    |
     * |  00001  |    홍길동   |     +    |  1번지  |   20     |
     * |  00002  |    김철수   |          |  2번지  |   23     |
     *
     * 단, 서로 다른 모델의 결합 기준은 존재하지 않으며 한번 모델을 결합하여 제공할 때를 고려하여 만들었습니다.
     *
     */
    @Test
    @DisplayName("StudentView + StudentInfo  Model을 하나의 Excel Sheet로 변환")
    void multiModelToSheetService() throws IOException {
        ExcelWorkBook excelWorkBook = new ExcelWorkBook(WorkBookType.XSSF);
        List<StudentViewModel> byViewModels = findByViewModels();
        List<StudentInfoModel> byInfoModels = findByInfoModels();
        excelWorkBook.createSheet().multiModelToSheet(byViewModels, byInfoModels);
        excelWorkBook.write(TestSupport.workBookOutput("StudentViewModel-StudentInfoModel-merged.xlsx"));
    }
}
