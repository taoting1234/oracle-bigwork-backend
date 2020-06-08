package com.newitd.oracle.repository;

import com.newitd.oracle.model.SubmitLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmitLogRepository extends JpaRepository<SubmitLog, Integer> {
    List<SubmitLog> findAllByStudentIdAndExaminationIdOrderByCreateTimeDesc(Integer studentId, Integer examinationId);
}
