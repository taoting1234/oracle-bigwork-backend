package com.newitd.oracle.controller;

import com.alibaba.fastjson.JSONObject;
import com.newitd.oracle.base.ApiException;
import com.newitd.oracle.model.Student;
import com.newitd.oracle.repository.StudentRepository;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/session")
public class SessionController {
    private final StudentRepository studentRepository;

    public SessionController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("")
    public Student get(HttpSession session) {
        Integer id = (Integer) session.getAttribute("student_id");
        if (id == null || !studentRepository.findById(id).isPresent()) {
            throw new ApiException("用户未登录");
        }
        return studentRepository.findById(id).get();
    }

    @PostMapping("")
    public void post(@RequestBody JSONObject jsonParam, HttpSession session) {
        String idCard = jsonParam.getString("idCard");
        String password = jsonParam.getString("password");
        if (idCard == null || password == null) {
            throw new ApiException("输入不合法");
        }
        password += idCard;
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        password += idCard;
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        Integer studentId = studentRepository.login(idCard, password);
        if (studentId == null) {
            throw new ApiException("用户不存在，请检查姓名和身份证信息");
        }
        session.setAttribute("student_id", studentId);
        throw new ApiException("登录成功", 0);
    }
}
