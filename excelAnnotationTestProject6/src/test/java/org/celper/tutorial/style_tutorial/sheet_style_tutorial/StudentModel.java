package org.celper.tutorial.style_tutorial.sheet_style_tutorial;

import org.celper.annotations.Column;
import org.celper.annotations.ColumnStyle;
import org.celper.annotations.SheetStyle;
import org.celper.tutorial.style_tutorial.HelloCellStyle;
import org.celper.tutorial.style_tutorial.HelloSheetStyle;

import java.time.LocalDate;

/**
 *
 */
@SheetStyle(HelloSheetStyle.class)
public class StudentModel {
    @Column("이름")
    private String name;

    @Column("주소")
    private String address;

    @Column("나이")
    private int age;

    @Column("생년월일")
    private LocalDate date;

    public StudentModel() {
    }

    public StudentModel(String name, String address, int age, LocalDate date) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
