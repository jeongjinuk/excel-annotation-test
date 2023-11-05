package org.celper.core;

import org.apache.poi.ss.usermodel.*;
import org.celper.core.model.ClassModel;
import org.celper.core.model.ColumnFrame;
import org.celper.exception.DataListEmptyException;
import org.celper.exception.NoSuchFieldException;
import org.celper.util.ModelMapperFactory;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * The type Excel sheet.
 */
public class ExcelSheet {
    private final Workbook _wb;
    private final Sheet sheet;

    /**
     * Instantiates a new Excel sheet.
     *
     * @param workbook the workbook
     * @param sheet    the sheet
     */
    public ExcelSheet(Workbook workbook, Sheet sheet) {
        this._wb = workbook;
        this.sheet = sheet;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return this.sheet.getSheetName();
    }

    /**
     * Gets sheet.
     *
     * @return the sheet
     */
    public Sheet getSheet() {
        return this.sheet;
    }

    /**
     * Model to sheet.
     *
     * @param <T>   the type parameter
     * @param model the model
     */
// API 제공 메서드
    public <T> void modelToSheet(List<T> model){
        modelToSheet(header -> false, model);
    }

    /**
     * Model to sheet.
     *
     * @param <T>            the type parameter
     * @param excludedHeader the excluded header
     * @param model          the model
     */
    public <T> void modelToSheet(Predicate<String> excludedHeader, List<T> model) {
        if (Objects.isNull(model) || model.isEmpty()) {
            throw new DataListEmptyException("data list is empty exception");
        }
        List<ColumnFrame> columnFrames = createColumnFrames(excludedHeader, ClassModelRegistrator.getOrDefault(model.get(0).getClass()));

        int headerRowIndex = 0;
        int dataRow = headerRowIndex + 1;
        int modelSize = model.size();

        headerWrite(columnFrames, headerRowIndex);
        IntStream.rangeClosed(dataRow, modelSize)
                .forEach(rowIndex -> dataWrite(columnFrames, rowIndex, model.get(rowIndex - 1)));
    }

    /**
     * Multi model to sheet.
     *
     * @param modelLists the model lists
     */
    public void multiModelToSheet(List<?>... modelLists) {
        multiModelToSheet(s -> false, modelLists);
    }

    /**
     * Multi model to sheet.
     *
     * @param excludedHeader the excluded header
     * @param modelLists     the model lists
     */
    public void multiModelToSheet(Predicate<String> excludedHeader, List<?>... modelLists) {
        for (List<?> modelList : modelLists) {
            if (Objects.isNull(modelList)) {
                throw new DataListEmptyException("data list is empty exception");
            }
        }

        List<Object[]> multiModel = convertMultiModel(modelLists);
        List<ColumnFrame> multiColumnFrames = createMultiColumnFrames(excludedHeader, multiModel);

        int headerRowIndex = 0;
        int dataRow = headerRowIndex + 1;
        int multiModelSize = multiModel.size();

        headerWrite(multiColumnFrames, headerRowIndex);
        IntStream.rangeClosed(dataRow, multiModelSize)
                .forEach(rowIndex -> multiModelDataWrite(multiColumnFrames, rowIndex, multiModel.get(rowIndex - 1)));
    }

    /**
     * Sheet to model list.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the list
     */
    public <T> List<T> sheetToModel(Class<T> clazz) {
        ModelMapper modelMapper = ModelMapperFactory.defaultModelMapper(); // Model Mapper 가져오기

        List<ColumnFrame> columnFrames = createColumnFrames(header -> false, ClassModelRegistrator.getOrDefault(clazz));
        List<ColumnFrame> importList = convertImportColumnFrameList(columnFrames);

        int startRow = importList.stream()// 병합을 고려해서 startRow 추출 병합 고려할려면 isMerged 만들어야함
                .max(ColumnFrame :: compareRowPosition)
                .orElseThrow(() -> new NoSuchFieldException(String.format("'%s' 시트에 매칭된 필드가 없습니다.", this.sheet.getSheetName())))
                .getHeaderRowPosition() + 1;

        return IntStream.rangeClosed(startRow, this.sheet.getLastRowNum())
                .filter(rowIdx -> Objects.nonNull(this.sheet.getRow(rowIdx)))
                .mapToObj(rowIdx -> createModelMap(importList, rowIdx))
                .map(entry -> modelMapper.map(entry, clazz))
                .collect(Collectors.toList());
    }


    private void add(List<ColumnFrame> columnFrames, List<ColumnFrame> newList,Cell cell) {
        for (ColumnFrame frame : columnFrames) {
            if (frame.getImportNameOptions().contains(CellUtils.getValue(cell).toString().trim())) {
                columnFrames.remove(frame);
                newList.add(frame.createImportOnlyColumnFrame(cell.getRowIndex(), cell.getColumnIndex()));
                return;
            }
        }
    }
    private void headerWrite(List<ColumnFrame> columnFrames, int rowIndex) {
        Row headerRow = CellUtils.createRow(this.sheet, rowIndex, columnFrames.size());
        IntConsumer setHeader = colIdx -> CellUtils.setValue(headerRow.getCell(colIdx), columnFrames.get(colIdx).getClassModel().getColumn().value());
        IntConsumer setStyle = colIdx -> headerRow.getCell(colIdx).setCellStyle(columnFrames.get(colIdx).getHeaderAreaCellStyle());
        write(columnFrames, setHeader, setStyle);
    }
    private void multiModelDataWrite(List<ColumnFrame> columnFrames, int rowIndex, Object[] model) {
        Stream.of(model)
                .forEach(o -> dataWrite(columnFrames, rowIndex, o));
    }
    private void dataWrite(List<ColumnFrame> columnFrames, int rowIndex, Object o) {
        Row row = CellUtils.createRow(this.sheet, rowIndex, columnFrames.size());
        IntConsumer setValue = colIdx -> CellUtils.setValue(columnFrames.get(colIdx), row.getCell(colIdx), o);
        IntConsumer setStyle = colIdx -> row.getCell(colIdx).setCellStyle(columnFrames.get(colIdx).getDataAreaCellStyle());
        write(columnFrames, setValue, setStyle);
    }
    private void write(List<ColumnFrame> columnFrames, IntConsumer setValue, IntConsumer setStyle) {
        IntStream.range(0, columnFrames.size())
                .peek(setValue)
                .forEach(setStyle);
    }

    private List<Object[]> convertMultiModel(List<?>[] modelLists) {
        Arrays.sort(modelLists, (o1, o2) -> o2.size() - o1.size());
        int maxModelSize = modelLists[0].size();
        int size = modelLists.length;
        List<Object[]> convertModel = new ArrayList<>(maxModelSize);
        IntStream.range(0, maxModelSize).forEach(i -> convertModel.add(new Object[size]));
        for (int i = 0; i < size; i++) {
            List<?> objects = modelLists[i];
            int dataSize = objects.size();
            while (--dataSize >= 0) {
                convertModel.get(dataSize)[i] = objects.get(dataSize);
            }
        }
        return convertModel;
    }
    private List<ColumnFrame> createMultiColumnFrames(Predicate<String> excludedHeader, List<Object[]> multiModel) {
        return Stream.of(multiModel.get(0))
                .map(o -> ClassModelRegistrator.getOrDefault(o.getClass()))
                .flatMap(classModels -> createColumnFrames(classModels, ColumnFrame :: setNonSheetStyle))
                .filter(columnFrame -> excludedHeader
                        .negate()
                        .test(columnFrame.getClassModel().getColumn().value()))
                .collect(Collectors.toList());
    }

    private Map<String, Object> createModelMap(List<ColumnFrame> columnFrames, int rowIndex) {
        return columnFrames.stream()
                .filter(ColumnFrame :: isExistColumn)
                .collect(Collectors.toMap(
                                frame -> frame.getClassModel().getFieldName(),
                                frame -> CellUtils.getValue(this.sheet, rowIndex, frame.getHeaderColumnPosition())
                        )
                );
    }
    private List<ColumnFrame> convertImportColumnFrameList(List<ColumnFrame> columnFrames) {
        List<ColumnFrame> newList = new ArrayList<>(columnFrames.size());
        int searchRowRange = Math.min(this.sheet.getLastRowNum(), 100);
        for (int i = 0; i < searchRowRange; i++) {
            Stream.of(this.sheet.getRow(i))
                    .filter(Objects::nonNull)
                    .flatMap(row -> StreamSupport.stream(row.spliterator(), false))
                    .filter(Objects::nonNull)
                    .filter(cell -> cell.getCellType() == CellType.STRING) // 숫자일때 비교 모할텐데 이건 내문제가 아님 1.0이래 나옴 그래서 막아야함
                    .forEach(cell -> add(columnFrames, newList, cell));
        }
        return newList;
    }

    private List<ColumnFrame> createColumnFrames(Predicate<String> excludedHeader, List<ClassModel> classModels) {
        return createColumnFrames(classModels, frame -> frame.setSheetStyle(this.sheet))
                .filter(columnFrame -> excludedHeader
                        .negate()
                        .test(columnFrame.getClassModel().getColumn().value()))
                .collect(Collectors.toList());
    }
    private Stream<ColumnFrame> createColumnFrames(List<ClassModel> classModels, Consumer<ColumnFrame> sheetStyleConsumer) {
        return classModels.stream()
                .map(classModel -> new ColumnFrame(this._wb, classModel))
                .peek(sheetStyleConsumer)
                .peek(ColumnFrame :: setColumnStyle)
                .sorted();
    }


// TODO 리팩토링 전 메서드
// TODO 리팩토링 전 메서드 코드 복잡성 작성
/*private List<ColumnFrame> findHeaderIndex(List<ColumnFrame> columnFrames) {
        List<ColumnFrame> newList = new ArrayList<>();
        int maxRow = Math.min(this.sheet.getLastRowNum(), 100);
        int size = columnFrames.size(); // size-- 해서 0이되면 종료
        for (short i = 0; i < maxRow && size > 0; i++) {
            Row row = this.sheet.getRow(i);
            if (row == null) continue;


            for (Cell next : row) {
                if (next == null || next.getCellType() != CellType.STRING) {
                    continue;
                }
                String headerName = next.getStringCellValue();

                if (headerName == null) {
                    continue;
                }
                for (ColumnFrame f : columnFrames) {
                    if (f.getImportNameOptions().contains(headerName.trim())) {
                        ColumnFrame importOnlyColumnFrame = f.createImportOnlyColumnFrame(next.getRowIndex(), next.getColumnIndex());
                        newList.add(importOnlyColumnFrame);
                        size--;
                        break;
                    }
                }
            }
        }
        return newList;
    }


public <T> List<T> sheetToModel(Class<T> clazz) {
ModelMapper modelMapper = ModelMapperFactory.defaultModelMapper(); // Model Mapper 가져오기

List<ColumnFrame> columnFrames = createColumnFrames(CelPerFrameRegistrator.getOrDefault(clazz));
List<ColumnFrame> importList = findHeaderIndex(columnFrames);//헤더 위치 excel sheet에서 찾기 바로 set 시켜서 따로 리턴 없음 Import 전용

int startRow = importList.stream()// 병합을 고려해서 startRow 추출
        .max(ColumnFrame :: compareRowPosition)
        .orElseThrow(() -> new NoSuchFieldException(String.format("'%s' 시트에 매칭된 필드가 없습니다.", this.sheet.getSheetName()))) // 매칭 안될 때 어케해야하지?
        .getHeaderRowPosition() + 1;


// rowIndex -> Map<String:FieldName, Object:CellValue> 길어서 때냄
// Map<String:FieldName,Object:CellValue> -> T:UserModel modelMapper는 캐싱할꺼라서 때냄
Function<Integer, Map<String, Object>> RowToMapMapper = ridx -> importList.stream()
        .map(columnFrame -> getCell(ridx, columnFrame.getHeaderColumnPosition(), columnFrame))
        .collect(Collectors.toMap(
                excelCell -> excelCell.getColumnFrame().getClassModel().getFieldName(),
                ExcelCell :: getValue));

Function<Map<String, Object>, T> mapToObjectMapper = map -> modelMapper.map(map, clazz);

// 여기 성능 느림 이건 개선하자 20000개 넣을때 대충 2분걸림 개선함 문제는 getCell에서 정책설정 문제였음 -> 이게 에러 또는 null 처리가 사용자가 만들어서 사용해서 느렸던거 -> 이거 전자정부프레임워크도 이렇게 되있음
// 개선 -> 45000개 5개 컬럼 45000 * 5 넣었을 때 5~7초
return IntStream.rangeClosed(startRow, this.sheet.getLastRowNum())
        .mapToObj(RowToMapMapper :: apply)
        .map(mapToObjectMapper)
        .collect(Collectors.toList());
}

public ExcelCell getCell(int row, int col, ColumnFrame columnFrame) {
Row sheetRow = this.sheet.getRow(row);
if (sheetRow == null) {
    sheetRow = this.sheet.createRow(row);
}
Cell cell = sheetRow.getCell(col, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
return new ExcelCell(cell, columnFrame);
}

public <T> void modelToSheet(List<T> list) {
if (list == null || list.isEmpty()) {
    throw new DataListEmptyException("data list is empty exception");
}
//준비 작업
List<ColumnFrame> columnFrames = createColumnFrames(CelPerFrameRegistrator.getOrDefault(list.get(0).getClass()))
        .stream()
        .sorted()
        .collect(Collectors.toList());
// write에 쓰일 함수 준비 작업
int headerRow = 0;
int dataRow = headerRow + 1;
int dataSize = list.size();
Consumer<Integer> writeData = i ->
        write(i, excelCell -> excelCell.setValue(list.get(i - 1)), columnFrames, ExcelCell :: setDataCellStyle);

// 실제 write작업
write(headerRow, ExcelCell :: setHeaderValue, columnFrames, ExcelCell :: setHeaderCellStyle);
IntStream.range(dataRow, dataSize).forEach(writeData :: accept);
}

public void multiModelToSheet(List<?>... multiModelArray) {
multiModelToSheet(s -> false, multiModelArray);
}
public void multiModelToSheet(Predicate<String> excludedHeader, List<?>... multiModelArray) {
if (multiModelArray == null || multiModelArray.length == 0) {
    throw new DataListEmptyException("data list is empty exception");
}
//준비 작업
List<Object[]> multiModel = convertMultiModel(multiModelArray);
List<ColumnFrame> multiColumnFrames = Stream.of(multiModel.get(0))
        .map(o -> CelPerFrameRegistrator.getOrDefault(o.getClass()))
        .map(this :: createMultiModelColumnFrames)
        .flatMap(List :: stream)
        .filter(columnFrame -> !excludedHeader.test(columnFrame.getClassModel().getColumn().value()))
        .sorted()
        .collect(Collectors.toList());

// write에 쓰일 함수 준비 작업
int headerRow = 0;
int dataRow = headerRow + 1;
int multiModelSize = multiModel.size();
BiConsumer<Integer, Object[]> writeData = (row, objects) -> IntStream.range(0, objects.length)
        .forEach(objectOrder -> write(row, excelCell -> excelCell.setValue(objects[objectOrder]), multiColumnFrames, ExcelCell :: setDataCellStyle));

// 실제 write작업
write(headerRow, ExcelCell :: setHeaderValue, multiColumnFrames, ExcelCell :: setHeaderCellStyle);
IntStream.rangeClosed(dataRow, multiModelSize).forEach(i -> writeData.accept(i, multiModel.get(i - 1)));
}

private void write(int ridx, Function<ExcelCell, ExcelCell> setValueFunction, List<ColumnFrame> columnFrames, Consumer<ExcelCell> styleConsumer) {
Function<Integer, ExcelCell> excelCellMapper = col -> getCell(ridx, col, columnFrames.get(col));
IntStream.range(0, columnFrames.size())
        .mapToObj(excelCellMapper :: apply)// ExcelCell 생성
        .map(setValueFunction)// Cell에 value setting
        .forEach(styleConsumer); //  Cell에 style setting
}*/
}