package pl.edu.uj.tcs.quizhub.models.Database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private int quizID;

    @Column(nullable = false)
    private String quizName;

    @Column(nullable = false)
    private String room;

    @Column(nullable = false)
    private List<String> usersPermitted;
}