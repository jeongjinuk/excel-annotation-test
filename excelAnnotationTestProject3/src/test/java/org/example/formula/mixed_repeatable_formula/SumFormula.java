package org.example.formula.mixed_repeatable_formula;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellAddress;
import org.example.templates.formula.FormulaTemplate;
import org.example.SheetAdapter;

import java.util.List;
import java.util.Map;

public class SumFormula implements FormulaTemplate{

    @Override
    public void render(SheetAdapter sheet, Map<String, Integer> fieldNameWithColumnIndexMap, int criteriaRowIndex, List<?> data) {
        CellAddress address1 = sheet.getCell(criteriaRowIndex, fieldNameWithColumnIndexMap.get("price")).getAddress();
        CellAddress address2 = sheet.getCell(data.size(), fieldNameWithColumnIndexMap.get("price")).getAddress();

        Cell cell = sheet.getCell(data.size()+1, fieldNameWithColumnIndexMap.get("price"));
        String formula = String.format("=sum(%s:%s)", address1, address2);
        cell.setCellFormula(formula);
    }
}
