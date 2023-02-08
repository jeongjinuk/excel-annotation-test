package org.example.resource;

import org.apache.poi.ss.usermodel.CellStyle;
import org.example.ExcelStyle;
import org.example.excel.style.NoStyle;
import org.example.excel.style.Style;
import org.example.excel.style.DefaultStyleKey;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public final class ExcelStyleResourceFactory {

    private ExcelStyleResourceFactory() {
    }
    static void put(Supplier<CellStyle> cellStyleSupplier,
                    ExcelStyleResource styleResource,
                    ExcelStyle excelStyle,
                    String key){
        put(cellStyleSupplier, styleResource::putHeaderStyle, excelStyle::headerStyleClass, key);
        put(cellStyleSupplier, styleResource::putBodyStyle, excelStyle::bodyStyleClass, key);
    }

    static void put(Supplier<CellStyle> cellStyleSupplier,
                    ExcelStyleResource styleResource,
                    ExcelStyle excelStyle){
        put(cellStyleSupplier, styleResource::putHeaderStyle, excelStyle::headerStyleClass, DefaultStyleKey.DEFAULT_HEADER_STYLE.getLocation());
        put(cellStyleSupplier, styleResource::putBodyStyle, excelStyle::bodyStyleClass, DefaultStyleKey.DEFAULT_BODY_STYLE.getLocation());
    }

    private static void put(Supplier<CellStyle> cellStyleSupplier,
                    BiConsumer<String, CellStyle> cellStylePutter,
                    Supplier<Class<? extends Style>> styleSupplier,
                    String key){
        if (noStyleValidate(styleSupplier.get())){
            cellStylePutter.accept(key, createStyle(cellStyleSupplier, styleSupplier));
        }
    }
    private static boolean noStyleValidate(Class<?> style){
        return !NoStyle.class.equals(style);
    }

    private static CellStyle createStyle(Supplier<CellStyle> cellStyleSupplier,
                                  Supplier<Class<? extends Style>> styleSupplier){
        CellStyle cellStyle = cellStyleSupplier.get();
        Style style = ReflectionUtils.getInstance(styleSupplier.get());
        style.setStyle(cellStyle);
        return cellStyle;
    }
}
