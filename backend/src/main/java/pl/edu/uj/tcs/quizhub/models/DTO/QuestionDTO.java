package pl.edu.uj.tcs.quizhub.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {
    private int id;
    private int correctAnswerId;
    private String question;
    private List<String> answers;
    private int quizId;
}