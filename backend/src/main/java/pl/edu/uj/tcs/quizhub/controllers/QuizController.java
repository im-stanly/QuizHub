package pl.edu.uj.tcs.quizhub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.uj.tcs.quizhub.models.DTO.QuizDTO;
import pl.edu.uj.tcs.quizhub.services.interfaces.QuizService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping("/id/{id}")
    public QuizDTO getQuizById(@PathVariable("id") int id) {
        return quizService.findById(id).orElseThrow();
    }

    @GetMapping("/name/{name}")
    public List<QuizDTO> getQuizByNameSubstring(@PathVariable("name") String nameSubstring) {
        return quizService.findByNameSubstring(nameSubstring);
    }

    @GetMapping("/userId/{userId}")
    public List<QuizDTO> getUserQuizzes(@PathVariable("userId") int userId) {
        return quizService.findByUserId(userId);
    }

    @GetMapping("/tag/{tag}")
    public List<QuizDTO> getQuizByTag(@PathVariable("tag") String tag) {
        return quizService.findByTag(tag);
    }

    @PostMapping("")
    public QuizDTO save(@RequestBody QuizDTO newQuiz) {
        return quizService.save(newQuiz);
    }

    @PatchMapping("/id/{id}")
    public QuizDTO patch(@PathVariable("id") int id, @RequestBody QuizDTO newQuiz) {
        return quizService.patch(id, newQuiz);
    }

    @PutMapping("/id/{id}")
    public QuizDTO put(@PathVariable("id") int id, @RequestBody QuizDTO newQuiz) {
        return quizService.update(id, newQuiz);
    }

    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable("id") int id) {
        quizService.delete(id);
    }
}