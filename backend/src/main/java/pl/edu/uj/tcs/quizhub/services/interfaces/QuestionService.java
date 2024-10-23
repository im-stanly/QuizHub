package pl.edu.uj.tcs.quizhub.services.interfaces;

import jakarta.transaction.Transactional;
import pl.edu.uj.tcs.quizhub.models.DTO.QuestionDTO;

import java.util.List;
import java.util.Optional;

public interface
QuestionService {
    @Transactional
    int save(QuestionDTO question);

    List<QuestionDTO> getAll();

    List<QuestionDTO> findByQuizId(int quizId);

    Optional<QuestionDTO> findById(int id);

    QuestionDTO patch(int id, QuestionDTO question);

    QuestionDTO update(int id, QuestionDTO question);

    void delete(int id);
}
