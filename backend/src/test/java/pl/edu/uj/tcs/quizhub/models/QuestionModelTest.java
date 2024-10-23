package pl.edu.uj.tcs.quizhub.models;

import org.junit.jupiter.api.Test;
import pl.edu.uj.tcs.quizhub.models.Database.QuestionModel;
import pl.edu.uj.tcs.quizhub.models.Database.QuizModel;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionModelTest {

    @Test
    public void testDefaultConstructor() {
        QuestionModel questionModel = new QuestionModel();
        assertNotNull(questionModel);
    }

    @Test
    public void testAllArgsConstructor() {
        QuizModel quizModel = new QuizModel();
        QuestionModel questionModel = new QuestionModel(1, 2, "What is the capital of France?", Arrays.asList("Berlin", "Paris", "Madrid", "Rome"), quizModel);

        assertEquals(1, questionModel.getId());
        assertEquals(2, questionModel.getCorrectAnswerId());
        assertEquals("What is the capital of France?", questionModel.getQuestion());
        assertEquals(Arrays.asList("Berlin", "Paris", "Madrid", "Rome"), questionModel.getAnswers());
        assertEquals(quizModel, questionModel.getQuiz());
    }

    @Test
    public void testBuilderWithAllFields() {
        QuizModel quizModel = new QuizModel();
        QuestionModel questionModel = QuestionModel.builder()
                .id(1)
                .correctAnswerId(2)
                .question("What is the capital of France?")
                .answers(Arrays.asList("Berlin", "Paris", "Madrid", "Rome"))
                .quiz(quizModel)
                .build();

        assertEquals(1, questionModel.getId());
        assertEquals(2, questionModel.getCorrectAnswerId());
        assertEquals("What is the capital of France?", questionModel.getQuestion());
        assertEquals(Arrays.asList("Berlin", "Paris", "Madrid", "Rome"), questionModel.getAnswers());
        assertEquals(quizModel, questionModel.getQuiz());
    }

    @Test
    public void testBuilderWithPartialFields() {
        QuestionModel questionModel = QuestionModel.builder()
                .correctAnswerId(2)
                .question("What is the capital of France?")
                .answers(Arrays.asList("Berlin", "Paris", "Madrid", "Rome"))
                .build();

        assertEquals(0, questionModel.getId());
        assertEquals(2, questionModel.getCorrectAnswerId());
        assertEquals("What is the capital of France?", questionModel.getQuestion());
        assertEquals(Arrays.asList("Berlin", "Paris", "Madrid", "Rome"), questionModel.getAnswers());
        assertNull(questionModel.getQuiz());
    }
}