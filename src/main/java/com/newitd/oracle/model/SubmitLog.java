package com.newitd.oracle.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class SubmitLog {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer examinationId;

    private Integer studentId;

    private Double score;

    private Date createTime;

    @OneToMany(mappedBy = "submitLog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnswerLog> answerLogList;
}
