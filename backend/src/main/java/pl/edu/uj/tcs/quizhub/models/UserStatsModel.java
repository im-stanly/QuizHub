package pl.edu.uj.tcs.quizhub.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.uj.tcs.quizhub.models.DTO.QuestionDTO;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStatsModel {
    private Map<String, Integer> pointsForUser;
    private List<QuestionDTO> questions;

    @Builder.Default
    private int indexQuestion = -1;

    public QuestionDTO getCurrentQuestion() {
        if (indexQuestion >= 0 && indexQuestion < questions.size())
            return questions.get(indexQuestion);
        return null;
    }

    public boolean hasNextQuestion() {
        this.indexQuestion++;
        return indexQuestion < questions.size();
    }

    public void updatePointsForUser(String username) {
        pointsForUser.put(username, pointsForUser.getOrDefault(username, 0) + 1);
    }

    public int getPointsForUser(String username) {
        return pointsForUser.getOrDefault(username, 0);
    }

    public void checkAnswer(String username, int userAnswer) {
        if (userAnswer == getCurrentQuestion().getCorrectAnswerId())
            updatePointsForUser(username);
    }

    public String getCorrectAnswer() {
        QuestionDTO currentQuestion = getCurrentQuestion();
        return currentQuestion.getAnswers().get(currentQuestion.getCorrectAnswerId());
    }
}