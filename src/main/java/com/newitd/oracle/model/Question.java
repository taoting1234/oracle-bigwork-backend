package com.newitd.oracle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue
    private Integer questionId;

    private String questionBody;

    private String questionForm;

    private String branch_A;

    private String branch_B;

    private String branch_C;

    private String picName;

    private String questionKey;

    private String questionTypeId;

    private String questionMark;

    private String questionSts;
}
