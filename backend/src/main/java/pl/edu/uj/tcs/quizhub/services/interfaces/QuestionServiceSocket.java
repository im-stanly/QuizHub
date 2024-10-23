package pl.edu.uj.tcs.quizhub.services.interfaces;

import pl.edu.uj.tcs.quizhub.models.DTO.QuestionDTO;

import java.util.List;

public interface QuestionServiceSocket {
    List<QuestionDTO> getNRandomQuestionsByQuizId(int n, int quizId);
}
