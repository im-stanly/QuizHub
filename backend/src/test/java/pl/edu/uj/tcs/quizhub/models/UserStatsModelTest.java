package pl.edu.uj.tcs.quizhub.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.uj.tcs.quizhub.models.DTO.QuestionDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserStatsModelTest {
    private UserStatsModel userStatsModel;
    @Mock
    private QuestionDTO mockQuestion1;
    @Mock
    private QuestionDTO mockQuestion2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<QuestionDTO> questions = new ArrayList<>();
        questions.add(mockQuestion1);
        questions.add(mockQuestion2);

        when(mockQuestion1.getCorrectAnswerId()).thenReturn(0);
        when(mockQuestion2.getCorrectAnswerId()).thenReturn(1);

        when(mockQuestion1.getAnswers()).thenReturn(List.of("Answer1", "WrongAnswer"));
        when(mockQuestion2.getAnswers()).thenReturn(List.of("WrongAnswer", "Answer2"));

        userStatsModel = new UserStatsModel(new HashMap<>(), questions, -1);
    }

    @Test
    void testGetQuestion_ValidIndex() {
        userStatsModel.setIndexQuestion(0);

        assertEquals(mockQuestion1, userStatsModel.getCurrentQuestion());
    }

    @Test
    void testGetQuestion_ValidIndex2() {
        userStatsModel.hasNextQuestion();

        assertEquals(mockQuestion1, userStatsModel.getCurrentQuestion());
    }

    @Test
    void testGetQuestion_InvalidIndex() {
        userStatsModel.setIndexQuestion(-1);
        assertNull(userStatsModel.getCurrentQuestion());

        userStatsModel.setIndexQuestion(10);
        assertNull(userStatsModel.getCurrentQuestion());
    }

    @Test
    void testHasNextQuestion() {
        assertTrue(userStatsModel.hasNextQuestion());
        assertEquals(0, userStatsModel.getIndexQuestion());

        assertTrue(userStatsModel.hasNextQuestion());
        assertEquals(1, userStatsModel.getIndexQuestion());

        assertFalse(userStatsModel.hasNextQuestion());
    }

    @Test
    void testUpdatePointsForUser() {
        assertEquals(0, userStatsModel.getPointsForUser("user1"));

        userStatsModel.updatePointsForUser("user1");
        assertEquals(1, userStatsModel.getPointsForUser("user1"));

        userStatsModel.updatePointsForUser("user1");
        assertEquals(2, userStatsModel.getPointsForUser("user1"));

        assertEquals(0, userStatsModel.getPointsForUser("user2"));
    }

    @Test
    void testCheckAnswer_Correct() {
        userStatsModel.setIndexQuestion(0);

        userStatsModel.checkAnswer("user1", 0);

        assertEquals(1, userStatsModel.getPointsForUser("user1"));
    }

    @Test
    void testCheckAnswer_Incorrect() {
        userStatsModel.setIndexQuestion(0);

        userStatsModel.checkAnswer("user1", 1);

        assertEquals(0, userStatsModel.getPointsForUser("user1"));
    }

    @Test
    void testGetCorrectAnswer() {
        userStatsModel.setIndexQuestion(0);
        assertEquals("Answer1", userStatsModel.getCorrectAnswer());

        userStatsModel.setIndexQuestion(1);
        assertEquals("Answer2", userStatsModel.getCorrectAnswer());
    }
}