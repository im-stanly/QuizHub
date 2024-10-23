package pl.edu.uj.tcs.quizhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.uj.tcs.quizhub.models.Database.GameModel;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<GameModel, Integer> {
    Optional<GameModel> findByRoom(String room);
}