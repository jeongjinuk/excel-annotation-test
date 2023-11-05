package org.celper.tutorial.multimodel_mapping_tutorial;

import org.celper.annotations.Column;

public class StudentInfoModel {
    @Column("주소")
    private String address;

    @Column("나이")
    private int age;

    public StudentInfoModel(String address, int age) {
        this.address = address;
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }
}
