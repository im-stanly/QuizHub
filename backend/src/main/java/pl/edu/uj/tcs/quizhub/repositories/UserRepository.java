package pl.edu.uj.tcs.quizhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.uj.tcs.quizhub.models.Database.UserModel;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    @Query("select u from #{#entityName} u where u.id = ?1")
    UserModel findById(int id);

    @Query("select u from #{#entityName} u where u.username = ?1")
    List<UserModel> findByUsername(String username);

    @Query("select u from #{#entityName} u where u.email = ?1")
    List<UserModel> findByEmail(String email);

    @Query("select u from #{#entityName} u where u.username = ?1 and password = ?2")
    UserModel findByUsernameAndPassword(String username, String password);
}