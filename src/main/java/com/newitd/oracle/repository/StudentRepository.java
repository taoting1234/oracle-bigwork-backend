package com.newitd.oracle.repository;

import com.newitd.oracle.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query(value = "select login(?1,?2) from dual", nativeQuery = true)
    Integer login(String nickname, String idCard);
}
