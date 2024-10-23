package pl.edu.uj.tcs.quizhub.models.Database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Questions")
public class QuestionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private int correctAnswerId;
    @Column(nullable = false)
    private String question;
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private List<String> answers;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizId")
    @JsonBackReference
    private QuizModel quiz;
}
