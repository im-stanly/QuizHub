package pl.edu.uj.tcs.quizhub.models.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pl.edu.uj.tcs.quizhub.models.Answers;
import pl.edu.uj.tcs.quizhub.models.CorrectAnswer;
import pl.edu.uj.tcs.quizhub.models.Database.TagModel;

import java.util.List;

@Data
public class QuestionModelDTO {
    private int id;
    private String question;
    private String description;
    private Answers answers;
    @JsonProperty("multiple_correct_answers")
    private boolean multipleCorrectAnswers;
    @JsonProperty("correct_answers")
    private CorrectAnswer correctAnswers;
    @JsonProperty("correct_answer")
    private String correctAnswer;
    private String explanation;
    private String tip;
    private List<TagModel> tags;
    private String category;
    private String difficulty;
}
