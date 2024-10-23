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
import pl.edu.uj.tcs.quizhub.services.interfaces.QuestionService;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class QuestionServiceTest {
    @InjectMocks
    private QuestionService questionService;
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
        questionService = new QuestionServiceImpl();
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
    void testSaveQuestion_success() {
        when(quizRepository.findById(1)).thenReturn(Optional.of(sampleQuizModel));
        when(questionModelMapper.onto(sampleQuestionDTO)).thenReturn(sampleQuestionModel);
        when(questionRepository.save(any(QuestionModel.class))).thenReturn(sampleQuestionModel);
        when(questionRepository.count()).thenReturn(0L);

        int result = questionService.save(sampleQuestionDTO);

        assertEquals(HttpURLConnection.HTTP_ACCEPTED, result);
        verify(questionRepository, times(1)).save(sampleQuestionModel);
    }

    @Test
    void testGetAllQuestions() {
        when(questionRepository.findAll()).thenReturn(Collections.singletonList(sampleQuestionModel));
        when(questionModelMapper.from(sampleQuestionModel)).thenReturn(sampleQuestionDTO);

        List<QuestionDTO> result = questionService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleQuestionDTO, result.get(0));
    }

    @Test
    void testFindByQuizId() {
        when(questionRepository.findByQuizId(1)).thenReturn(Collections.singletonList(sampleQuestionModel));
        when(questionModelMapper.from(sampleQuestionModel)).thenReturn(sampleQuestionDTO);

        List<QuestionDTO> result = questionService.findByQuizId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleQuestionDTO, result.get(0));
    }

    @Test
    void testFindById_success() {
        when(questionRepository.findById(1)).thenReturn(Optional.of(sampleQuestionModel));
        when(questionModelMapper.from(sampleQuestionModel)).thenReturn(sampleQuestionDTO);

        Optional<QuestionDTO> result = questionService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(sampleQuestionDTO, result.get());
    }

    @Test
    void testFindById_notFound() {
        when(questionRepository.findById(1)).thenReturn(Optional.empty());

        Optional<QuestionDTO> result = questionService.findById(1);

        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateQuestion() {
        when(questionRepository.findById(1)).thenReturn(Optional.of(sampleQuestionModel));
        when(questionModelMapper.onto(sampleQuestionDTO)).thenReturn(sampleQuestionModel);

        QuestionModel updatedQuestionModel = QuestionModel.builder()
                .id(1)
                .question("Updated Question")
                .answers(Arrays.asList("Berlin", "Paris", "Madrid", "Rome"))
                .correctAnswerId(2)
                .quiz(sampleQuizModel)
                .build();

        when(questionRepository.save(any(QuestionModel.class))).thenReturn(updatedQuestionModel);
        when(questionModelMapper.from(updatedQuestionModel)).thenReturn(QuestionDTO.builder()
                .id(1)
                .question("Updated Question")
                .answers(Arrays.asList("Berlin", "Paris", "Madrid", "Rome"))
                .correctAnswerId(2)
                .quizId(1)
                .build());

        QuestionDTO result = questionService.update(1, sampleQuestionDTO);

        assertNotNull(result);
        assertEquals("Updated Question", result.getQuestion());
        assertEquals(sampleQuestionDTO.getCorrectAnswerId(), result.getCorrectAnswerId());
        assertEquals(sampleQuestionDTO.getAnswers(), result.getAnswers());

        verify(questionRepository).save(sampleQuestionModel);
    }

    @Test
    void testDeleteQuestion() {
        doNothing().when(questionRepository).deleteById(1);

        questionService.delete(1);

        verify(questionRepository, times(1)).deleteById(1);
    }

    @Test
    void testPatchQuestion() {
        when(questionRepository.findById(1)).thenReturn(Optional.of(sampleQuestionModel));

        QuestionModel patchedQuestionModel = QuestionModel.builder()
                .id(1)
                .question("Patched Question")
                .answers(Arrays.asList("Berlin", "Paris", "Madrid", "Rome"))
                .correctAnswerId(2)
                .quiz(sampleQuizModel)
                .build();

        when(questionRepository.save(any(QuestionModel.class))).thenReturn(patchedQuestionModel);
        when(questionModelMapper.from(patchedQuestionModel)).thenReturn(QuestionDTO.builder()
                .id(1)
                .question("Patched Question")
                .answers(Arrays.asList("Berlin", "Paris", "Madrid", "Rome"))
                .correctAnswerId(2)
                .quizId(1)
                .build());

        QuestionDTO patchDTO = QuestionDTO.builder()
                .question("Patched Question")
                .build();

        QuestionDTO result = questionService.patch(1, patchDTO);

        assertNotNull(result);
        assertEquals("Patched Question", result.getQuestion());
        assertEquals(sampleQuestionDTO.getCorrectAnswerId(), result.getCorrectAnswerId());
        assertEquals(sampleQuestionDTO.getAnswers(), result.getAnswers());

        verify(questionRepository).save(sampleQuestionModel);
    }

    @Test
    void testSaveQuestion_quizNotFound() {
        when(quizRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            questionService.save(sampleQuestionDTO);
        });
    }

    @Test
    void testUpdateQuestion_notFound() {
        when(questionRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            questionService.update(1, sampleQuestionDTO);
        });
    }

    @Test
    void testPatchQuestion_notFound() {
        when(questionRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            questionService.patch(1, sampleQuestionDTO);
        });
    }

    @Test
    void testDeleteQuestion_notFound() {
        doThrow(new RuntimeException()).when(questionRepository).deleteById(1);

        assertThrows(RuntimeException.class, () -> {
            questionService.delete(1);
        });
    }
}