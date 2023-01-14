package org.example.support;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestSupport {
    public static void workBookOutput(Workbook workbook) {
        StringBuilder stringBuilder = new StringBuilder(System.getProperty("user.dir")).append("\\src\\test\\resources");
        stringBuilder.append(File.separator);
        try (OutputStream fileOut = new FileOutputStream(stringBuilder.append("workbook.xlsx").toString())) {
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
