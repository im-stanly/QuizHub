package pl.edu.uj.tcs.quizhub.services.mappers;

import org.springframework.stereotype.Service;
import pl.edu.uj.tcs.quizhub.models.DTO.QuestionDTO;
import pl.edu.uj.tcs.quizhub.models.Database.QuestionModel;
import pl.edu.uj.tcs.quizhub.services.interfaces.ModelMapper;

@Service
public class QuestionModelMapper implements ModelMapper<QuestionDTO, QuestionModel> {
    public QuestionModel onto(QuestionDTO questionDTO) {
        if (questionDTO == null) return null;
        return QuestionModel.builder()
                .id(questionDTO.getId())
                .question(questionDTO.getQuestion())
                .answers(questionDTO.getAnswers())
                .correctAnswerId(questionDTO.getCorrectAnswerId())
                .build();
    }

    public QuestionDTO from(QuestionModel questionModel) {
        if (questionModel == null) return null;
        return QuestionDTO.builder()
                .id(questionModel.getId())
                .question(questionModel.getQuestion())
                .answers(questionModel.getAnswers())
                .correctAnswerId(questionModel.getCorrectAnswerId())
                .quizId(questionModel.getQuiz().getId())
                .build();
    }
}
