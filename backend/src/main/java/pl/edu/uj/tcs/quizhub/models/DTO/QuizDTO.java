package pl.edu.uj.tcs.quizhub.models.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class QuizDTO {
    private int id;
    private String name;
    private String description;
    private Set<String> tags;
    private int creatorId;
}
