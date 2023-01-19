package org.example.formula.mixed_repeatable_formula;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellAddress;
import org.example.templates.SheetAdapter;
import org.example.templates.formula.FormulaTemplate;

import java.util.List;
import java.util.Map;

public class SumFormula implements FormulaTemplate {
//null get -> 예외가 발생
//값이 있을때 createw -> 값을 덮
//값이 있을 때 들고 오면 -> value
//값이 없을 때 create -> 값을 만들고
    @Override
    public void render(SheetAdapter sheet, Map<String, Integer> fieldNameWithColumnIndexMap, int criteriaRowIndex, List<?> data) {
        CellAddress address1 = sheet.getCell(criteriaRowIndex, fieldNameWithColumnIndexMap.get("price")).getAddress();
        CellAddress address2 = sheet.getCell(data.size(), fieldNameWithColumnIndexMap.get("price")).getAddress();

        Cell cell = sheet.getCell(data.size()+1, fieldNameWithColumnIndexMap.get("price"));
        String formula = String.format("=sum(%s:%s)", address1, address2);
        cell.setCellFormula(formula);
    }
}
