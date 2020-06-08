package com.newitd.oracle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class AnswerLog {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "submit_log_id")
    @JsonIgnore
    private SubmitLog submitLog;

    private String answer;

    private Double score;
}
