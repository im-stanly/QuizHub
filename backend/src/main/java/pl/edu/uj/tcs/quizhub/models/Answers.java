package pl.edu.uj.tcs.quizhub.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Answers {
    @JsonProperty("answer_a")
    private String answerA;
    @JsonProperty("answer_b")
    private String answerB;
    @JsonProperty("answer_c")
    private String answerC;
    @JsonProperty("answer_d")
    private String answerD;
    @JsonProperty("answer_e")
    private String answerE;
    @JsonProperty("answer_f")
    private String answerF;
}
