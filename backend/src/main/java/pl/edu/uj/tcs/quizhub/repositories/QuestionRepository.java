package pl.edu.uj.tcs.quizhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.uj.tcs.quizhub.models.Database.QuestionModel;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionModel, Integer> {
    @Query("select qm from #{#entityName} qm where qm.quiz.id = ?1 ORDER BY qm.id")
    List<QuestionModel> findByQuizId(int quizId);
}