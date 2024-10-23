package pl.edu.uj.tcs.quizhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.uj.tcs.quizhub.models.Database.TagModel;

import java.util.Optional;

public interface TagRepository extends JpaRepository<TagModel, Integer> {
    @Query("select tag from #{#entityName} tag where tag.name = ?1")
    Optional<TagModel> findByName(String name);
}
