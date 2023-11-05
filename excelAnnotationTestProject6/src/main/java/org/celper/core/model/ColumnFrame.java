package org.celper.core.model;

import lombok.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.celper.core.style.CellStyleConfigurer;
import org.celper.core.style.builder.CellStyleBuilder;
import org.celper.core.style.builder.SheetStyleBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * The type Column frame.
 */
@Getter
@ToString
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ColumnFrame implements Comparable<ColumnFrame> {
    @Getter(AccessLevel.NONE)
    private Workbook _wb;
    private ClassModel classModel;
    private List<String> importNameOptions;
    private CellStyle defaultCellStyle;
    private CellStyle headerAreaCellStyle;
    private CellStyle dataAreaCellStyle;
    private int headerRowPosition = -1;
    private int headerColumnPosition = -1;

    /**
     * Instantiates a new Column frame.
     *
     * @param workbook   the workbook
     * @param classModel the class model
     */
    public ColumnFrame(Workbook workbook, ClassModel classModel) {
        this._wb = workbook;
        this.classModel = classModel;
        this.importNameOptions = createImportNameOptions();
    }

    /**
     * Sets column style.
     */
    public void setColumnStyle() {
        this.headerAreaCellStyle = createCellStyle(classModel :: getHeaderAreaConfigurer);
        this.dataAreaCellStyle = createCellStyle(classModel :: getDataAreaConfigurer);
        setDataFormat();
    }

    /**
     * Sets sheet style.
     *
     * @param sheet the sheet
     */
    public void setSheetStyle(Sheet sheet) {
        this.defaultCellStyle = this._wb.createCellStyle();
        SheetStyleBuilder builder = new SheetStyleBuilder(this._wb, sheet, this.defaultCellStyle);
        this.classModel.getSheetStyleConfigurer().config(builder);
    }

    /**
     * Sets non sheet style.
     */
    public void setNonSheetStyle() {
        this.defaultCellStyle = this._wb.createCellStyle();
    }

    /**
     * Create import only column frame column frame.
     *
     * @param row the row
     * @param col the col
     * @return the column frame
     */
    public ColumnFrame createImportOnlyColumnFrame(int row, int col) {
        return ColumnFrame.builder()
                ._wb(this._wb)
                .classModel(this.classModel)
                .importNameOptions(this.importNameOptions)
                .defaultCellStyle(this.defaultCellStyle)
                .headerAreaCellStyle(this.headerAreaCellStyle)
                .dataAreaCellStyle(this.dataAreaCellStyle)
                .headerRowPosition(row)
                .headerColumnPosition(col)
                .build();
    }

    /**
     * Compare row position int.
     *
     * @param o the o
     * @return the int
     */
    public int compareRowPosition(ColumnFrame o) {
        return o.getHeaderRowPosition() - this.getHeaderRowPosition();
    }

    /**
     * Is default value exists boolean.
     *
     * @return the boolean
     */
    public boolean isDefaultValueExists() {
        return !"".equals(this.classModel.getDefaultValue());
    }

    /**
     * Is exist column boolean.
     *
     * @return the boolean
     */
    public boolean isExistColumn() {
        return headerRowPosition >= 0 && headerColumnPosition >= 0;
    }

    private List<String> createImportNameOptions() {
        List<String> titles = new ArrayList<>();
        titles.add(this.classModel.getColumn().value());
        if (!"".equals(this.classModel.getColumn().importNameOptions()[0])) {
            titles.addAll(List.of(this.classModel.getColumn().importNameOptions()));
        }
        return titles;
    }

    private CellStyle createCellStyle(Supplier<? extends CellStyleConfigurer> supplier) {
        CellStyle cellStyle = this._wb.createCellStyle();
        cellStyle.cloneStyleFrom(defaultCellStyle);
        supplier.get().config(new CellStyleBuilder(_wb, cellStyle));
        return cellStyle;
    }

    private void setDataFormat() {
        this.dataAreaCellStyle.setDataFormat(this._wb.createDataFormat().getFormat(this.classModel.getCellFormat()));
    }

    @Override
    public int compareTo(ColumnFrame o) {
        boolean b = o.classModel.getExportPriority() != this.classModel.getExportPriority();
        return b ? this.classModel.getExportPriority() - o.classModel.getExportPriority() :
                this.classModel.getColumn().value().compareTo(o.classModel.getColumn().value());
    }

}
