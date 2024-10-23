package pl.edu.uj.tcs.quizhub.services.interfaces;

import pl.edu.uj.tcs.quizhub.models.DTO.QuizDTO;

import java.util.List;
import java.util.Optional;

public interface QuizService {
    QuizDTO save(QuizDTO quiz);

    List<QuizDTO> findByNameSubstring(String nameSubstring);

    List<QuizDTO> findByTag(String tag);

    Optional<QuizDTO> findById(int id);

    QuizDTO patch(int id, QuizDTO quiz);

    QuizDTO update(int id, QuizDTO quiz);

    List<QuizDTO> findByUserId(int userId);

    void delete(int id);
}
