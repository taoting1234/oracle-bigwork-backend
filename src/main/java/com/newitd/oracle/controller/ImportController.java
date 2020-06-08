package com.newitd.oracle.controller;

import com.newitd.oracle.model.Answer;
import com.newitd.oracle.model.Examination;
import com.newitd.oracle.model.Problem;
import com.newitd.oracle.model.Question;
import com.newitd.oracle.repository.AnswerRepository;
import com.newitd.oracle.repository.ExaminationRepository;
import com.newitd.oracle.repository.ProblemRepository;
import com.newitd.oracle.repository.QuestionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/import")
public class ImportController {
    private final QuestionRepository questionRepository;
    private final ProblemRepository problemRepository;
    private final ExaminationRepository examinationRepository;
    private final AnswerRepository answerRepository;

    public ImportController(QuestionRepository questionRepository, ProblemRepository problemRepository, ExaminationRepository examinationRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.problemRepository = problemRepository;
        this.examinationRepository = examinationRepository;
        this.answerRepository = answerRepository;
    }

    @PostMapping("")
    public void importData() {
        Examination examination = examinationRepository.findOneById(3);
        List<Question> questionList = questionRepository.findAll();
        for (Question question : questionList) {
            if (question.getPicName() != null) {
                continue;
            }
            Problem problem = new Problem();
            problem.setName(question.getQuestionBody());
            problem.setType((short) 0);
            problemRepository.save(problem);
            if (question.getBranch_A() == null) {//判断题
                Answer answer1 = new Answer();
                answer1.setAnswer("对");
                answer1.setProblem(problem);
                answerRepository.save(answer1);
                Answer answer2 = new Answer();
                answer2.setAnswer("错");
                answer2.setProblem(problem);
                answerRepository.save(answer2);
                if (question.getQuestionKey().equals("A")) {
                    problem.setTrueAnswer(answer1.getId() + "");
                } else {
                    problem.setTrueAnswer(answer2.getId() + "");
                }
                problem.setScore(2);
            } else {//选择题
                Answer answer1 = new Answer();
                answer1.setAnswer(question.getBranch_A());
                answer1.setProblem(problem);
                answerRepository.save(answer1);
                Answer answer2 = new Answer();
                answer2.setAnswer(question.getBranch_B());
                answer2.setProblem(problem);
                answerRepository.save(answer2);
                Answer answer3 = new Answer();
                answer3.setAnswer(question.getBranch_C());
                answer3.setProblem(problem);
                answerRepository.save(answer3);
                if (question.getQuestionKey().equals("A")) {
                    problem.setTrueAnswer(answer1.getId() + "");
                } else if (question.getQuestionKey().equals("B")) {
                    problem.setTrueAnswer(answer2.getId() + "");
                } else {
                    problem.setTrueAnswer(answer3.getId() + "");
                }
                problem.setScore(3);
            }
            problemRepository.save(problem);
            examination.getProblemList().add(problem);
        }
        examinationRepository.save(examination);
    }
}
