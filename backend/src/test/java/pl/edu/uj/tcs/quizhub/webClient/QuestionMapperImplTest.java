package pl.edu.uj.tcs.quizhub.webClient;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.uj.tcs.quizhub.models.Answers;
import pl.edu.uj.tcs.quizhub.models.CorrectAnswer;
import pl.edu.uj.tcs.quizhub.models.DTO.QuestionDTO;
import pl.edu.uj.tcs.quizhub.models.DTO.QuestionModelDTO;
import pl.edu.uj.tcs.quizhub.models.Database.QuestionModel;
import pl.edu.uj.tcs.quizhub.services.interfaces.ModelMapper;

import java.util.LinkedList;
import java.util.List;

class QuestionMapperImplTest {

    @Mock
    private QuestionsClient questionsClient;

    @Mock
    private ModelMapper<QuestionDTO, QuestionModel> questionModelMapper;

    @InjectMocks
    private QuestionMapperImpl questionMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetQuestionsLoopUntilEnoughValidQuestions() {
        List<QuestionModelDTO> mockQuestionList = new LinkedList<>();
        mockQuestionList.add(createValidQuestionModelDTO(1));
        mockQuestionList.add(createValidQuestionModelDTO(2));

        when(questionsClient.getQuestions()).thenReturn(mockQuestionList);

        List<QuestionModel> mockQuestionModels = new LinkedList<>();
        mockQuestionModels.add(createValidQuestionModel(1));
        mockQuestionModels.add(createValidQuestionModel(2));
        when(questionModelMapper.from(any(QuestionModel.class))).thenAnswer(invocation -> {
            QuestionModel questionModel = invocation.getArgument(0);
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setId(questionModel.getId());
            return questionDTO;
        });

        List<QuestionDTO> result = questionMapper.getQuestions();

        assertTrue(result.size() >= 10); // The loop should run until at least 10 valid questions
        verify(questionsClient, atLeastOnce()).getQuestions();
        verify(questionModelMapper, atLeast(10)).from(any(QuestionModel.class));
    }

    private QuestionModelDTO createValidQuestionModelDTO(int id) {
        QuestionModelDTO questionModelDTO = new QuestionModelDTO();
        questionModelDTO.setId(id);
        questionModelDTO.setQuestion("Sample question?");
        questionModelDTO.setMultipleCorrectAnswers(false);

        // Set valid answers
        Answers answers = new Answers();
        answers.setAnswerA("A");
        answers.setAnswerB("B");
        answers.setAnswerC("C");
        answers.setAnswerD("D");
        questionModelDTO.setAnswers(answers);

        // Set correct answers
        CorrectAnswer correctAnswers = new CorrectAnswer();
        correctAnswers.setAnswerACorrect(true);  // Assume Answer A is correct
        correctAnswers.setAnswerBCorrect(false);
        correctAnswers.setAnswerCCorrect(false);
        correctAnswers.setAnswerDCorrect(false);
        questionModelDTO.setCorrectAnswers(correctAnswers);

        return questionModelDTO;
    }

    // Helper method to create a valid QuestionModel
    private QuestionModel createValidQuestionModel(int id) {
        QuestionModel questionModel = new QuestionModel();
        questionModel.setId(id);
        questionModel.setCorrectAnswerId(0);
        questionModel.setQuestion("Sample question?");
        questionModel.setAnswers(List.of("A", "B", "C", "D"));
        return questionModel;
    }
}