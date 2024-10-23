package pl.edu.uj.tcs.quizhub.services.interfaces;

import pl.edu.uj.tcs.quizhub.models.DTO.QuizDTO;

import java.util.Optional;

public interface QuizGameService {
    Optional<QuizDTO> findById(int id);
}
