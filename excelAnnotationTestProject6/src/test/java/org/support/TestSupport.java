package org.support;

import org.apache.poi.ss.usermodel.*;

import java.io.*;

public class TestSupport {
    public static OutputStream workBookOutput() {
        StringBuilder stringBuilder = new StringBuilder(System.getProperty("user.dir")).append("\\src\\test\\resources");
        stringBuilder.append(File.separator);
        try {
            return new FileOutputStream(stringBuilder.append("workbook.xlsx").toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static OutputStream workBookOutput(String fileName) {
        StringBuilder stringBuilder = new StringBuilder(System.getProperty("user.dir")).append("\\src\\test\\resources\\tutorial");
        stringBuilder.append(File.separator);
        try {
            return new FileOutputStream(stringBuilder.append(fileName).toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Workbook workBookInput(String path) {
        StringBuilder stringBuilder = new StringBuilder(System.getProperty("user.dir")).append("\\src\\test\\resources\\tutorial");
        stringBuilder.append(File.separator);
        try (InputStream fileInput = new FileInputStream(stringBuilder.append(path).toString())) {
            return WorkbookFactory.create(fileInput);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Workbook workBookInput() {
        StringBuilder stringBuilder = new StringBuilder(System.getProperty("user.dir")).append("\\src\\test\\resources\\");
        stringBuilder.append(File.separator);
        try (InputStream fileInput = new FileInputStream(stringBuilder.append("workbook.xlsx").toString())) {
            return WorkbookFactory.create(fileInput);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
