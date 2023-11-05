package org.celper.tutorial.default_value_tutorial;

import org.celper.annotations.CellFormat;
import org.celper.annotations.Column;
import org.celper.annotations.DefaultValue;
import org.celper.type.BuiltinCellFormatType;

import java.time.LocalDate;

public class StudentModel {
    @Column("이름")
    private String name;

    @Column("주소")
    @DefaultValue("한국 주소가 존재 하지 않습니다.")
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
