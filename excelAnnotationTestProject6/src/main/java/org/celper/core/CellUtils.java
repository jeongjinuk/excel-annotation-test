package org.celper.core;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.celper.core.model.ColumnFrame;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * The type Cell utils.
 */
public final class CellUtils {

    private CellUtils(){
        throw new IllegalStateException("Utility class");
    }

    /**
     * Create row row.
     *
     * @param sheet    the sheet
     * @param rowIndex the row index
     * @param cellSize the cell size
     * @return the row
     */
    static Row createRow(Sheet sheet, int rowIndex, int cellSize){
        Row row = Objects.isNull(sheet.getRow(rowIndex)) ? sheet.createRow(rowIndex) : sheet.getRow(rowIndex);
        IntConsumer createCell = colIndex -> row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        IntStream.range(0, cellSize).forEach(createCell);
        return row;
    }

    /**
     * Get value object.
     *
     * @param sheet    the sheet
     * @param rowIndex the row index
     * @param colIndex the col index
     * @return the object
     */
    static Object getValue(Sheet sheet, int rowIndex, int colIndex){
        return getValue(
                sheet.getRow(rowIndex).getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
    }

    /**
     * Sets value.
     *
     * @param frame the frame
     * @param cell  the cell
     * @param o     the o
     */
    static void setValue(ColumnFrame frame, Cell cell, Object o) {
        try {
            Field field = frame.getClassModel().getField();
            field.setAccessible(true);
            Object val = field.get(o);
            val = val == null && frame.isDefaultValueExists() ? frame.getClassModel().getDefaultValue() : val;
            setValue(cell, val);
        }catch (IllegalAccessException | IllegalArgumentException ignored) {
        }catch (NullPointerException e) {
            if (frame.isDefaultValueExists()) {
                setValue(cell, frame.getClassModel().getDefaultValue());
            }
        }
    }

    /**
     * Sets value.
     *
     * @param cell the cell
     * @param o    the o
     */
    static void setValue(Cell cell, Object o) {
        if (o instanceof Double) {
            cell.setCellValue((double) o);
        } else if (o instanceof Integer) {
            cell.setCellValue((int) o);
        } else if (o instanceof Long) {
            cell.setCellValue((long) o);
        } else if (o instanceof Boolean) {
            cell.setCellValue((Boolean) o);
        } else if (o instanceof RichTextString) {
            cell.setCellValue((RichTextString) o);
        } else if (o instanceof Date) {
            cell.setCellValue((Date) o);
        } else if (o instanceof Calendar) {
            cell.setCellValue((Calendar) o);
        } else if (o instanceof LocalDate) {
            cell.setCellValue(LocalDateTime.of((LocalDate) o, LocalTime.NOON));
        } else if (o instanceof LocalTime) {
            cell.setCellValue(LocalDateTime.of(LocalDate.EPOCH, (LocalTime) o));
        } else {
            cell.setCellValue(String.valueOf(o));
        }
    }

    /**
     * Gets value.
     *
     * @param cell the cell
     * @return the value
     */
    static Object getValue(Cell cell) {
        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCachedFormulaResultType();
            case BLANK:
            case ERROR:
                return "";
            case STRING:
            default:
                return cell.getStringCellValue();
        }
    }
}







