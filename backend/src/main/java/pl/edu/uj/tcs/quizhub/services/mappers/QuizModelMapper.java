package pl.edu.uj.tcs.quizhub.services.mappers;

import org.springframework.stereotype.Service;
import pl.edu.uj.tcs.quizhub.models.DTO.QuizDTO;
import pl.edu.uj.tcs.quizhub.models.Database.QuizModel;
import pl.edu.uj.tcs.quizhub.models.Database.TagModel;
import pl.edu.uj.tcs.quizhub.services.interfaces.ModelMapper;

import java.util.stream.Collectors;

@Service
public class QuizModelMapper implements ModelMapper<QuizDTO, QuizModel> {
    public QuizModel onto(QuizDTO quizDTO) {
        if (quizDTO == null) return null;
        return QuizModel.builder()
                .id(quizDTO.getId())
                .creatorId(quizDTO.getCreatorId())
                .name(quizDTO.getName())
                .description(quizDTO.getDescription())
                .build();
    }

    public QuizDTO from(QuizModel quizModel) {
        if (quizModel == null) return null;
        return QuizDTO.builder()
                .id(quizModel.getId())
                .creatorId(quizModel.getCreatorId())
                .name(quizModel.getName())
                .description(quizModel.getDescription())
                .tags(quizModel.getTags().stream().map(TagModel::getName).collect(Collectors.toSet()))
                .build();
    }
}
