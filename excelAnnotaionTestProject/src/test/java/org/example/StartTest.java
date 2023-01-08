package org.example;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.dto.CafeDto;
import org.example.dto.CarDto;
import org.example.render.CafeRender;
import org.example.render.CarRender;
import org.example.render.ViewRender;
import org.example.testDomain.ExcelDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StartTest {

    /**
     * HSSFWorkbook 97(-2007) 파일형식
     * XSSFWorkbook 2007 파일 형식
     * SXSSFWorkbook 큰 스프레드시트 생성 및 힙공간이 제한되어 있을때
     *  - 제한된 수의 행만 특정 시점에 액세스할 수 있다.
     *  - 클론 지원안함
     *  - 수식 평가 지원안함
     *
     * File 객체를 사용하면 메모리 소비를 줄일 수 있는 반면,
     * InputStream을 사용하면 전체 파일을 버퍼링해야 하므로 더 많은 메모리를 사용한다.
     *
     * 파일 읽는 방법
     * 동일한 TEST 가 적힌 필드가 있다면
     *
     * HSSFWorkbook
     * XSSFWorkbook
     * 등을 직접 사용하는 경우 일반적으로 POIFSFileSystem 또는 IPCPackage를 통해 수명주기를 완전 제어해야한다.
     * OLE (97~2003) - POIFSFileSystem
     * OOXML - OPCPackage
     * 하드 용량에서 좀 차이난다.
     *
     */

    List<ExcelDto> excelDtos;

    @BeforeEach
    @DisplayName("엑셀에 넣을 기본값 지정")
    void setCoffeeService(){
        excelDtos = List.of( new ExcelDto("엔젤인어스", "콜드브루", 6200, 5),
                new ExcelDto("파스쿠치", "콜드브루", 6000, 2.3),
                new ExcelDto("이디야커피", "콜드브루", 5200, 4.1),
                new ExcelDto("스타벅스", "콜드브루", 4200, 1.1));
    }


    @Test
    @DisplayName("POI 기본적인 사용")
    void startTest() throws IOException {
        Workbook xls = new HSSFWorkbook();
        Workbook bigXlsx = new XSSFWorkbook();
        Workbook xlsx = new SXSSFWorkbook();

        Sheet sheet = xlsx.createSheet();
        int rowIndex = 0;

        List<String> fields = List.of("브랜드", "제품", "가격", "평점");

        Row headerRow = sheet.createRow(rowIndex++);

        IntStream.rangeClosed(0,3)
                .forEach(value ->
                        headerRow.createCell(value).setCellValue(fields.get(value)));

        for (ExcelDto excelDto : excelDtos) {
            Row bodyRow = sheet.createRow(rowIndex++);
            // 여기서 보통 실수할 일이 발생한다. 0, 1, 2, 3 이부분!
            bodyRow.createCell(0).setCellValue(excelDto.getCafeName());
            bodyRow.createCell(1).setCellValue(excelDto.getName());
            bodyRow.createCell(2).setCellValue(excelDto.getPrice());
            bodyRow.createCell(3).setCellValue(excelDto.getGrade());
        }

        workBookOutput(xlsx);
        xlsx.close();
    }

    @Test
    @DisplayName("POI cafe 기본 추상화 테스트")
    void abstractTest() throws IOException {
        Workbook xlsx = new SXSSFWorkbook();
        List<CafeDto> collect = IntStream.rangeClosed(0, 100)
                .mapToObj(operand -> new CafeDto(operand + "", operand + "콜드브루", operand, operand + 0.1))
                .collect(Collectors.toList());

        ViewRender<CafeDto> cafeDtoViewRender = new CafeRender();
        cafeDtoViewRender.render(xlsx,collect);
        workBookOutput(xlsx);
        xlsx.close();
    }


    @Test
    @DisplayName("POI car 기본 추상화 테스트")
    void abstractCarTest() throws IOException {
        Workbook xlsx = new SXSSFWorkbook();
        List<CarDto> collect = IntStream.rangeClosed(0, 100)
                .mapToObj(operand -> new CarDto(operand + "", operand + "","suv",  operand, operand+0.1))
                .collect(Collectors.toList());

        ViewRender<CarDto> cafeDtoViewRender = new CarRender();
        cafeDtoViewRender.render(xlsx,collect);
        workBookOutput(xlsx);
        xlsx.close();
    }
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
}