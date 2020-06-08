package com.newitd.oracle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Problem {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private Integer score;

    private Short type;

    private String trueAnswer;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Answer> answerList;

    @ManyToMany(mappedBy = "problemList")
    @JsonIgnore
    private List<Examination> examinationList;
}
