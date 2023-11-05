package org.celper.core.model;

import lombok.Getter;
import org.celper.annotations.*;
import org.celper.core.style.CellStyleConfigurer;
import org.celper.core.style.SheetStyleConfigurer;
import org.celper.core.style._NoCellStyle;
import org.celper.type.BuiltinCellFormatType;
import org.celper.util.ReflectionUtils;

import java.lang.reflect.Field;

@Getter
public final class ClassModel {
    private final Field field;
    private String fieldName;
    private Column column;
    private String defaultValue;
    private String cellFormat;
    private int exportPriority;
    private SheetStyleConfigurer sheetStyleConfigurer;
    private CellStyleConfigurer headerAreaConfigurer =  builder -> {};
    private CellStyleConfigurer dataAreaConfigurer = builder -> {};

    public ClassModel(Field field) {
        this.field = field;
    }
    public ClassModel setColumn() {
        this.column = this.field.getDeclaredAnnotation(Column.class);
        return this;
    }
    public ClassModel setDefaultValue(){
        if (this.field.isAnnotationPresent(DefaultValue.class)){
            this.defaultValue = this.field.getDeclaredAnnotation(DefaultValue.class).value();
        }
        return this;
    }
    public ClassModel setFieldName(){
        this.fieldName = this.field.getName();
        return this;
    }
    public ClassModel setPriority(int definedOrder){
        this.exportPriority = this.column.priority() * 1000 + definedOrder;
        return this;
    }
    public ClassModel setCellFormat() {
        if (!this.field.isAnnotationPresent(CellFormat.class)){
            this.cellFormat = BuiltinCellFormatType.GENERAL.getCellFormat();
            return this;
        }
        CellFormat annotation = this.field.getDeclaredAnnotation(CellFormat.class);
        this.cellFormat = "".equals(annotation.customFormat()) ? annotation.builtinFormat().getCellFormat() : annotation.customFormat();
        return this;
    }
    public ClassModel setSheetStyleConfigurer(SheetStyleConfigurer sheetStyleConfigurer) {
        this.sheetStyleConfigurer = sheetStyleConfigurer;
        return this;
    }
    public ClassModel setCellStyleConfigurer(){
        if (this.field.isAnnotationPresent(ColumnStyle.class)){
            ColumnStyle annotation = this.field.getDeclaredAnnotation(ColumnStyle.class);

            if (!_NoCellStyle.class.equals(annotation.headerAreaStyle())){
                this.headerAreaConfigurer = ReflectionUtils.getInstance(annotation.headerAreaStyle());
            }

            if (!_NoCellStyle.class.equals(annotation.dataAreaStyle())){
                this.dataAreaConfigurer = ReflectionUtils.getInstance(annotation.dataAreaStyle());
            }

        }
        return this;
    }
    public static SheetStyleConfigurer createSheetStyle(Class<?> clazz) {
        if (clazz.isAnnotationPresent(SheetStyle.class)){
            return ReflectionUtils.getInstance(clazz.getDeclaredAnnotation(SheetStyle.class).value());
        }
        return builder -> {};
    }


}
