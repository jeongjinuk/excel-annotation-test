package org.example.formula;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellAddress;
import org.example.FormulaTemplate;
import org.example.excel.AdapterSheet;

import java.util.Map;

public class SumFormula implements FormulaTemplate<TestDto> {

    @Override
    public void validation(AdapterSheet sheet, int rowIndex, Map<String, Integer> fieldNameWithColumnIndexMap) {
    }
    @Override
    public void render(AdapterSheet sheet, int rowIndex, Map<String, Integer> fieldNameWithColumnIndexMap) {
        Cell cell = sheet.getCell(rowIndex, fieldNameWithColumnIndexMap.get("field1"));
        CellAddress address1 = sheet.getRow(1).getCell(fieldNameWithColumnIndexMap.get("field2")).getAddress();
        CellAddress address2 = sheet.getRow(rowIndex-1).getCell(fieldNameWithColumnIndexMap.get("field2")).getAddress();
        String formula = String.format("=sum(%s:%s)", address1, address2);
        cell.setCellFormula(formula);
    }
}

