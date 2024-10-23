package pl.edu.uj.tcs.quizhub.models.Database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Quizzes")
public class QuizModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(insertable = false, updatable = false, nullable = false)
    private int id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "Quizzes_Tags", joinColumns = @JoinColumn(name = "quizId"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagModel> tags;
    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private Set<QuestionModel> questions;
    @Column(nullable = false)
    private int creatorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizModel quizModel = (QuizModel) o;
        return id == quizModel.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}