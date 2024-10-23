package pl.edu.uj.tcs.quizhub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.uj.tcs.quizhub.models.DTO.QuestionDTO;
import pl.edu.uj.tcs.quizhub.services.interfaces.QuestionService;
import pl.edu.uj.tcs.quizhub.webClient.QuestionMapper;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("")
    public List<QuestionDTO> getQuestions() {
        return questionMapper.getQuestions();
    }

    @GetMapping("/id/{id}")
    public QuestionDTO getQuestionById(@PathVariable("id") int id) {
        if (questionService.findById(id).isPresent()) return questionService.findById(id).get();
        throw new RuntimeException();
    }

    @GetMapping("quizId/{quizId}")
    public List<QuestionDTO> getQuestionsByQuizId(@PathVariable("quizId") int quizId) {
        return questionService.findByQuizId(quizId);
    }

    @GetMapping("quizId/{quizId}/count")
    public int getQuizSize(@PathVariable("quizId") int quizId) {
        return questionService.findByQuizId(quizId).size();
    }

    @GetMapping("quizId/{quizId}/n/{n}")
    public QuestionDTO getNthQuestionByQuizId(@PathVariable("quizId") int quizId, @PathVariable("n") int n) {
        return questionService.findByQuizId(quizId).get(n);
    }

    @GetMapping("/all")
    public List<QuestionDTO> getAllQuestions() {
        return questionService.getAll();
    }

    @PostMapping("")
    public int save(@RequestBody QuestionDTO question) {
        return questionService.save(question);
    }

    @PatchMapping("/id/{id}")
    public QuestionDTO patch(@PathVariable("id") int id, @RequestBody QuestionDTO newQuestion) {
        return questionService.patch(id, newQuestion);
    }

    @PutMapping("/id/{id}")
    public QuestionDTO put(@PathVariable("id") int id, @RequestBody QuestionDTO newQuestion) {
        return questionService.update(id, newQuestion);
    }

    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable("id") int id) {
        questionService.delete(id);
    }
}