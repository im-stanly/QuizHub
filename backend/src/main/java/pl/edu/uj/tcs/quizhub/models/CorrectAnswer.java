package pl.edu.uj.tcs.quizhub.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CorrectAnswer {
    @JsonProperty("answer_a_correct")
    private boolean answerACorrect;
    @JsonProperty("answer_b_correct")
    private boolean answerBCorrect;
    @JsonProperty("answer_c_correct")
    private boolean answerCCorrect;
    @JsonProperty("answer_d_correct")
    private boolean answerDCorrect;
    @JsonProperty("answer_e_correct")
    private boolean answerECorrect;
    @JsonProperty("answer_f_correct")
    private boolean answerFCorrect;
}
