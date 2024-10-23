package pl.edu.uj.tcs.quizhub.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameInitialDataDTO {
    private int userID;
    private int quizID;
}