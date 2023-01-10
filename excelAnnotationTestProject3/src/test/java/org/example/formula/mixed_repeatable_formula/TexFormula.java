package org.example.formula.mixed_repeatable_formula;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellUtil;
import org.example.excel.FormulaTemplate;
import org.example.excel.SheetAdapter;

import java.util.List;
import java.util.Map;

public class TexFormula implements FormulaTemplate<TestDto> {

    @Override
    public void render(SheetAdapter sheet, Map<String, Integer> fieldNameWithColumnIndexMap, int criteriaRowIndex, List<TestDto> data) {
        int start = 1;
        for (int i = start; i <= data.size(); i++) {
            CellAddress address = sheet.getRow(i).getCell(fieldNameWithColumnIndexMap.get("price")).getAddress();
            Cell cell = sheet.getCell(i, sheet.getRow(i).getLastCellNum());
            String formula = String.format("=%s*1.1", address);
            cell.setCellFormula(formula);
        }
    }
}

