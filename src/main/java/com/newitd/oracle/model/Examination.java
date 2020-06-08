package com.newitd.oracle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
public class Examination {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @ManyToMany
    private List<Problem> problemList;

    @ManyToMany(mappedBy = "examinationList")
    @JsonIgnore
    private List<Student> studentList;
}
