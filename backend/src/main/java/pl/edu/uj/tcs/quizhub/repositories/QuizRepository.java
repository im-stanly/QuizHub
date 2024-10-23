package pl.edu.uj.tcs.quizhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.uj.tcs.quizhub.models.Database.QuizModel;
import pl.edu.uj.tcs.quizhub.models.Database.TagModel;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<QuizModel, Integer> {
    @Query("SELECT q FROM QuizModel q JOIN q.tags t WHERE t = ?1")
    List<QuizModel> findByTagModel(TagModel tagModel);

    @Query("SELECT q FROM QuizModel q WHERE LOWER(q.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<QuizModel> findByNameSubstring(String nameSubstring);

    @Query("SELECT q FROM QuizModel q WHERE q.creatorId = ?1")
    List<QuizModel> findByUserId(int userId);
}