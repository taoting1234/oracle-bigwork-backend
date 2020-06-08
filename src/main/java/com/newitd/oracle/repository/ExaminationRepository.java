package com.newitd.oracle.repository;

import com.newitd.oracle.model.Examination;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExaminationRepository extends JpaRepository<Examination, Integer> {
    Examination findOneById(Integer id);
}