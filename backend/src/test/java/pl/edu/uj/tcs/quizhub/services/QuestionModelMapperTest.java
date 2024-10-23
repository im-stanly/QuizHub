package pl.edu.uj.tcs.quizhub.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.uj.tcs.quizhub.models.DTO.QuestionDTO;
import pl.edu.uj.tcs.quizhub.models.Database.QuestionModel;
import pl.edu.uj.tcs.quizhub.models.Database.QuizModel;
import pl.edu.uj.tcs.quizhub.services.interfaces.ModelMapper;
import pl.edu.uj.tcs.quizhub.services.mappers.QuestionModelMapper;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionModelMapperTest {

    private ModelMapper<QuestionDTO, QuestionModel> questionModelMapper;

    @BeforeEach
    void setUp() {
        questionModelMapper = new QuestionModelMapper();
    }

    @Test
    void testOnto() {
        QuestionDTO questionDTO = QuestionDTO.builder()
                .id(1)
                .question("What is the capital of France?")
                .answers(Arrays.asList("Berlin", "Paris", "Madrid", "Rome"))
                .correctAnswerId(2)
                .quizId(10)
                .build();

        QuestionModel questionModel = questionModelMapper.onto(questionDTO);

        assertNotNull(questionModel);
        assertEquals(1, questionModel.getId());
        assertEquals("What is the capital of France?", questionModel.getQuestion());
        assertEquals(2, questionModel.getCorrectAnswerId());
        assertEquals(Arrays.asList("Berlin", "Paris", "Madrid", "Rome"), questionModel.getAnswers());
    }

    @Test
    void testFrom() {
        QuizModel quizModel = QuizModel.builder()
                .id(10)
                .name("Geography Quiz")
                .build();

        QuestionModel questionModel = QuestionModel.builder()
                .id(1)
                .question("What is the capital of France?")
                .answers(Arrays.asList("Berlin", "Paris", "Madrid", "Rome"))
                .correctAnswerId(2)
                .quiz(quizModel)
                .build();

        QuestionDTO questionDTO = questionModelMapper.from(questionModel);

        assertNotNull(questionDTO);
        assertEquals(1, questionDTO.getId());
        assertEquals("What is the capital of France?", questionDTO.getQuestion());
        assertEquals(2, questionDTO.getCorrectAnswerId());
        assertEquals(10, questionDTO.getQuizId());
        assertEquals(Arrays.asList("Berlin", "Paris", "Madrid", "Rome"), questionDTO.getAnswers());
    }

    @Test
    void testOnto_Null() {
        QuestionModel questionModel = questionModelMapper.onto(null);
        assertNull(questionModel);
    }

    @Test
    void testFrom_Null() {
        QuestionDTO questionDTO = questionModelMapper.from(null);
        assertNull(questionDTO);
    }
}
