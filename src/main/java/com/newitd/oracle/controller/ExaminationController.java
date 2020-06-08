package com.newitd.oracle.controller;

import com.alibaba.fastjson.JSONArray;
import com.newitd.oracle.base.ApiException;
import com.newitd.oracle.model.AnswerLog;
import com.newitd.oracle.model.Examination;
import com.newitd.oracle.model.Problem;
import com.newitd.oracle.model.SubmitLog;
import com.newitd.oracle.repository.*;
import com.newitd.oracle.util.CheckProblem;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/examination")
public class ExaminationController {
    private final ExaminationRepository examinationRepository;
    private final StudentRepository studentRepository;
    private final SubmitLogRepository submitLogRepository;
    private final AnswerLogRepository answerLogRepository;
    private final ProblemRepository problemRepository;

    public ExaminationController(ExaminationRepository examinationRepository, StudentRepository studentRepository, SubmitLogRepository submitLogRepository, AnswerLogRepository answerLogRepository, ProblemRepository problemRepository) {
        this.examinationRepository = examinationRepository;
        this.studentRepository = studentRepository;
        this.submitLogRepository = submitLogRepository;
        this.answerLogRepository = answerLogRepository;
        this.problemRepository = problemRepository;
    }


    @GetMapping("/{id}")
    public Examination get(@PathVariable Integer id, HttpSession session) {
        Integer student_id = (Integer) session.getAttribute("student_id");
        if (student_id == null || !studentRepository.findById(student_id).isPresent()) {
            throw new ApiException("用户未登录");
        }
        if (!examinationRepository.findById(id).isPresent()) {
            throw new ApiException("考试不存在");
        }
        return examinationRepository.findById(id).get();
    }

    @PostMapping("/{id}/submit")
    public void submit(@PathVariable Integer id, @RequestBody JSONArray jsonParam, HttpSession session) {
        Integer student_id = (Integer) session.getAttribute("student_id");
        if (student_id == null || !studentRepository.findById(student_id).isPresent()) {
            throw new ApiException("用户未登录");
        }
        if (!examinationRepository.findById(id).isPresent()) {
            throw new ApiException("考试不存在");
        }
        Examination examination = examinationRepository.findById(id).get();
        int exam_len = examination.getProblemList().size();
        int answer_len = jsonParam.size();
        if (exam_len != answer_len) {
//            throw new ApiException("非法提交");
        }
        Map<Integer, String> ansMap = new HashMap<>();
        for (Object o : jsonParam) {
            Integer problemId = (Integer) ((Map) o).get("problem_id");
            String answer = (String) ((Map) o).get("answer");
            ansMap.put(problemId, answer);
        }
        for(Map.Entry<Integer,String> item:ansMap.entrySet()){
            Problem problem = problemRepository.findById(item.getKey()).get();
            String ans = item.getValue();
            if (ans == null) {
                throw new ApiException("非法提交");
            }
            if (problem.getType() == 0 && ans.split(",").length != 1) {
                throw new ApiException("非法提交");
            }
        }
        SubmitLog submitLog = new SubmitLog();
        submitLog.setStudentId(student_id);
        submitLog.setExaminationId(id);
        submitLog.setCreateTime(new Date());
        submitLogRepository.save(submitLog);
        double allScore = 0.0;
        for(Map.Entry<Integer,String> item:ansMap.entrySet()){
            Problem problem = problemRepository.findById(item.getKey()).get();
            String ans = item.getValue();
            Set<Integer> ansSet = new HashSet<>();
            for (String s : ans.split(",")) {
                ansSet.add(Integer.parseInt(s));
            }
            Set<Integer> trueAnsSet = new HashSet<>();
            for (String s : problem.getTrueAnswer().split(",")) {
                trueAnsSet.add(Integer.parseInt(s));
            }
            Double realScore = CheckProblem.check(trueAnsSet, ansSet, problem.getScore());
            allScore += realScore;
            AnswerLog answerLog = new AnswerLog();
            answerLog.setProblem(problem);
            answerLog.setAnswer(ans);
            answerLog.setScore(realScore);
            answerLog.setSubmitLog(submitLog);
            answerLogRepository.save(answerLog);
        }
        submitLog.setScore(allScore);
        submitLogRepository.save(submitLog);
        throw new ApiException(submitLog.getId().toString(), 0);
    }

}
