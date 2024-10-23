package pl.edu.uj.tcs.quizhub.webClient;

import pl.edu.uj.tcs.quizhub.models.DTO.QuestionModelDTO;

import java.util.List;

public interface QuestionsClient {
    List<QuestionModelDTO> getQuestions();
}
