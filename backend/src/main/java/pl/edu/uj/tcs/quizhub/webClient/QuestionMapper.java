package pl.edu.uj.tcs.quizhub.webClient;

import pl.edu.uj.tcs.quizhub.models.DTO.QuestionDTO;

import java.util.List;

public interface QuestionMapper {
    List<QuestionDTO> getQuestions();
}
