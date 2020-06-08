package com.newitd.oracle.controller;

import com.newitd.oracle.base.ApiException;
import com.newitd.oracle.model.SubmitLog;
import com.newitd.oracle.repository.StudentRepository;
import com.newitd.oracle.repository.SubmitLogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/submitLog")
public class SubmitLogController {
    private final SubmitLogRepository submitLogRepository;
    private final StudentRepository studentRepository;

    public SubmitLogController(SubmitLogRepository submitLogRepository, StudentRepository studentRepository) {
        this.submitLogRepository = submitLogRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("")
    public List<SubmitLog> getList(@PathParam("exam_id") Integer exam_id, HttpSession session) {
        Integer student_id = (Integer) session.getAttribute("student_id");
        if (student_id == null || !studentRepository.findById(student_id).isPresent()) {
            throw new ApiException("用户未登录");
        }
        return submitLogRepository.findAllByStudentIdAndExaminationIdOrderByCreateTimeDesc(student_id, exam_id);
    }

    @GetMapping("/{id}")
    public SubmitLog get(@PathVariable Integer id, HttpSession session) {
        Integer student_id = (Integer) session.getAttribute("student_id");
        if (student_id == null || !studentRepository.findById(student_id).isPresent()) {
            throw new ApiException("用户未登录");
        }
        if (!submitLogRepository.findById(id).isPresent()) {
            throw new ApiException("答题记录不存在");
        }
        return submitLogRepository.findById(id).get();
    }
}
