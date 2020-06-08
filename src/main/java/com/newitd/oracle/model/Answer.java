package com.newitd.oracle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "problem_id")
    @JsonIgnore
    private Problem problem;

    private String answer;
}
