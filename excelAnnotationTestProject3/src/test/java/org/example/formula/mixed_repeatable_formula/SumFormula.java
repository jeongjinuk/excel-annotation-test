package org.example.formula.mixed_repeatable_formula;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellAddress;
import org.example.excel.FormulaTemplate;
import org.example.excel.SheetAdapter;

import java.util.List;
import java.util.Map;

public class SumFormula implements FormulaTemplate<TestDto> {

    @Override
    public void render(SheetAdapter sheet, Map<String, Integer> fieldNameWithColumnIndexMap, int criteriaRowIndex, List<TestDto> data) {
        CellAddress address1 = sheet.getRow(1).getCell(fieldNameWithColumnIndexMap.get("price")).getAddress();
        CellAddress address2 = sheet.getRow(sheet.getSheet().getLastRowNum()).getCell(fieldNameWithColumnIndexMap.get("price")).getAddress();

        Cell cell = sheet.getCell(sheet.getSheet().getLastRowNum()+1, fieldNameWithColumnIndexMap.get("price"));
        String formula = String.format("=sum(%s:%s)", address1, address2);
        cell.setCellFormula(formula);
    }
}

