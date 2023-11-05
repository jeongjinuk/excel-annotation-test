package org.celper.tutorial.multimodel_mapping_tutorial;

import org.celper.annotations.Column;

public class StudentViewModel {
    @Column("학번")
    private String studentID;

    @Column("이름")
    private String name;

    public StudentViewModel() {}

    public StudentViewModel(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
