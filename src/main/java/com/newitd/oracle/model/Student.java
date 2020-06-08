package com.newitd.oracle.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Student {
    @Id
    @GeneratedValue
    private Integer id;

    private String idCard;

    private String password;

    @ManyToMany
    private List<Examination> examinationList;
}
