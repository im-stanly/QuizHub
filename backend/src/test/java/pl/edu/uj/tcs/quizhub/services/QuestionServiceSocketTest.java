package pl.edu.uj.tcs.quizhub.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.uj.tcs.quizhub.models.DTO.QuestionDTO;
import pl.edu.uj.tcs.quizhub.models.Database.QuestionModel;
import pl.edu.uj.tcs.quizhub.models.Database.QuizModel;
import pl.edu.uj.tcs.quizhub.repositories.QuestionRepository;
import pl.edu.uj.tcs.quizhub.repositories.QuizRepository;
import pl.edu.uj.tcs.quizhub.services.implementations.QuestionServiceImpl;
import pl.edu.uj.tcs.quizhub.services.interfaces.ModelMapper;
import pl.edu.uj.tcs.quizhub.services.interfaces.QuestionServiceSocket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuestionServiceSocketTest {
    @InjectMocks
    private QuestionServiceSocket questionServiceSocket;
    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private ModelMapper<QuestionDTO, QuestionModel> questionModelMapper;

    private QuestionDTO sampleQuestionDTO;
    private QuestionModel sampleQuestionModel;
    private QuizModel sampleQuizModel;

    @BeforeEach
    void setUp() {
        questionServiceSocket = new QuestionServiceImpl();
        MockitoAnnotations.openMocks(this);

        sampleQuizModel = QuizModel.builder()
                .id(1)
                .name("Sample Quiz")
                .build();

        sampleQuestionDTO = QuestionDTO.builder()
                .id(1)
                .question("What is the capital of France?")
                .answers(Arrays.asList("Berlin", "Paris", "Madrid", "Rome"))
                .correctAnswerId(2)
                .quizId(1)
                .build();

        sampleQuestionModel = QuestionModel.builder()
                .id(1)
                .question("What is the capital of France?")
                .answers(Arrays.asList("Berlin", "Paris", "Madrid", "Rome"))
                .correctAnswerId(2)
                .quiz(sampleQuizModel)
                .build();
    }

    @Test
    void testGetNRandomQuestionsByQuizId_success() {
        QuestionModel sampleQuestionModel2 = QuestionModel.builder()
                .id(2)
                .question("What is the capital of Germany?")
                .answers(Arrays.asList("Berlin", "Paris", "Madrid", "Rome"))
                .correctAnswerId(1)
                .quiz(sampleQuizModel)
                .build();

        when(questionRepository.findByQuizId(1)).thenReturn(List.of(sampleQuestionModel, sampleQuestionModel2));

        List<QuestionDTO> result = questionServiceSocket.getNRandomQuestionsByQuizId(2, 1);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(questionRepository, times(1)).findByQuizId(1);
    }

    @Test
    void testGetNRandomQuestionsByQuizId_fewerQuestionsThanRequested() {
        when(questionRepository.findByQuizId(1)).thenReturn(Collections.singletonList(sampleQuestionModel));

        List<QuestionDTO> result = questionServiceSocket.getNRandomQuestionsByQuizId(3, 1);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(questionRepository, times(1)).findByQuizId(1);
    }

    @Test
    void testGetNRandomQuestionsByQuizId_noQuestionsAvailable() {
        when(questionRepository.findByQuizId(1)).thenReturn(Collections.emptyList());

        List<QuestionDTO> result = questionServiceSocket.getNRandomQuestionsByQuizId(3, 1);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(questionRepository, times(1)).findByQuizId(1);
    }
}
