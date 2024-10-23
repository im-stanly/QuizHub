package pl.edu.uj.tcs.quizhub.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.uj.tcs.quizhub.models.DTO.QuestionDTO;
import pl.edu.uj.tcs.quizhub.services.interfaces.QuestionService;
import pl.edu.uj.tcs.quizhub.webClient.QuestionMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private QuestionMapper questionMapper;
    private QuestionDTO sampleQuestion;

    @BeforeEach
    void setUp() {
        sampleQuestion = QuestionDTO.builder()
                .id(1)
                .correctAnswerId(2)
                .question("What is the capital of France?")
                .answers(Arrays.asList("Berlin", "Paris", "Madrid", "Rome"))
                .quizId(1)
                .build();
    }

    @Test
    void testGetQuestionById_success() throws Exception {
        when(questionService.findById(1)).thenReturn(Optional.of(sampleQuestion));

        mockMvc.perform(get("/question/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.correctAnswerId").value(2))
                .andExpect(jsonPath("$.question").value("What is the capital of France?"))
                .andExpect(jsonPath("$.answers[1]").value("Paris"));
    }

    @Test
    void testGetAllQuestions() throws Exception {
        QuestionDTO question2 = QuestionDTO.builder()
                .id(2)
                .correctAnswerId(3)
                .question("What is 2 + 2?")
                .answers(Arrays.asList("3", "4", "5", "6"))
                .quizId(1)
                .build();

        when(questionService.getAll()).thenReturn(Arrays.asList(sampleQuestion, question2));

        mockMvc.perform(get("/question/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void testSaveQuestion() throws Exception {
        when(questionService.save(any(QuestionDTO.class))).thenReturn(1);

        mockMvc.perform(post("/question")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(sampleQuestion)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void testGetQuestionsByQuizId_success() throws Exception {
        QuestionDTO question2 = QuestionDTO.builder()
                .id(2)
                .correctAnswerId(3)
                .question("What is 2 + 2?")
                .answers(Arrays.asList("3", "4", "5", "6"))
                .quizId(1)
                .build();

        when(questionService.findByQuizId(1)).thenReturn(Arrays.asList(sampleQuestion, question2));

        mockMvc.perform(get("/question/quizId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[0].quizId").value(1))
                .andExpect(jsonPath("$[1].quizId").value(1));
    }

    @Test
    void testGetQuestionsByQuizId_noQuestionsFound() throws Exception {
        when(questionService.findByQuizId(1)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/question/quizId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetQuestionsByQuizId_multipleQuestions() throws Exception {
        QuestionDTO question2 = QuestionDTO.builder()
                .id(2)
                .correctAnswerId(3)
                .question("What is 2 + 2?")
                .answers(Arrays.asList("3", "4", "5", "6"))
                .quizId(1)
                .build();

        QuestionDTO question3 = QuestionDTO.builder()
                .id(3)
                .correctAnswerId(1)
                .question("What is the capital of Germany?")
                .answers(Arrays.asList("Berlin", "Munich", "Hamburg", "Frankfurt"))
                .quizId(1)
                .build();

        when(questionService.findByQuizId(1)).thenReturn(Arrays.asList(sampleQuestion, question2, question3));

        mockMvc.perform(get("/question/quizId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3));
    }

    @Test
    void testGetQuestionsByQuizId_emptyQuiz() throws Exception {
        when(questionService.findByQuizId(1)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/question/quizId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testDeleteQuestion() throws Exception {
        doNothing().when(questionService).delete(1);

        mockMvc.perform(delete("/question/id/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetNthQuestionByQuizId_success() throws Exception {
        QuestionDTO question2 = QuestionDTO.builder()
                .id(2)
                .correctAnswerId(3)
                .question("What is 2 + 2?")
                .answers(Arrays.asList("3", "4", "5", "6"))
                .quizId(1)
                .build();

        when(questionService.findByQuizId(1)).thenReturn(Arrays.asList(sampleQuestion, question2));

        mockMvc.perform(get("/question/quizId/1/n/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.correctAnswerId").value(3))
                .andExpect(jsonPath("$.question").value("What is 2 + 2?"))
                .andExpect(jsonPath("$.answers[1]").value("4"));
    }

    @Test
    void testGetQuizSize_success() throws Exception {
        QuestionDTO question2 = QuestionDTO.builder()
                .id(2)
                .correctAnswerId(3)
                .question("What is 2 + 2?")
                .answers(Arrays.asList("3", "4", "5", "6"))
                .quizId(1)
                .build();

        when(questionService.findByQuizId(1)).thenReturn(Arrays.asList(sampleQuestion, question2));

        mockMvc.perform(get("/question/quizId/1/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    void testGetQuizSize_emptyQuiz() throws Exception {
        when(questionService.findByQuizId(1)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/question/quizId/1/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    void testGetQuizSize_invalidQuizId() throws Exception {
        when(questionService.findByQuizId(999)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/question/quizId/999/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    void testPatchQuestion_success() throws Exception {
        QuestionDTO updatedQuestion = QuestionDTO.builder()
                .id(1)
                .correctAnswerId(3)
                .question("Updated Question")
                .answers(Arrays.asList("Answer 1", "Answer 2", "Answer 3", "Answer 4"))
                .quizId(1)
                .build();

        when(questionService.patch(eq(1), any(QuestionDTO.class))).thenReturn(updatedQuestion);

        mockMvc.perform(patch("/question/id/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedQuestion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.correctAnswerId").value(3))
                .andExpect(jsonPath("$.question").value("Updated Question"))
                .andExpect(jsonPath("$.answers[2]").value("Answer 3"));
    }

    @Test
    void testPutQuestion_success() throws Exception {
        QuestionDTO updatedQuestion = QuestionDTO.builder()
                .id(1)
                .correctAnswerId(3)
                .question("Updated Question")
                .answers(Arrays.asList("Answer 1", "Answer 2", "Answer 3", "Answer 4"))
                .quizId(1)
                .build();

        when(questionService.update(eq(1), any(QuestionDTO.class))).thenReturn(updatedQuestion);

        mockMvc.perform(put("/question/id/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedQuestion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.correctAnswerId").value(3))
                .andExpect(jsonPath("$.question").value("Updated Question"))
                .andExpect(jsonPath("$.answers[2]").value("Answer 3"));
    }

    @Test
    void testGetQuestions_success() throws Exception {
        when(questionMapper.getQuestions()).thenReturn(Collections.singletonList(sampleQuestion));

        mockMvc.perform(get("/question"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].correctAnswerId").value(2))
                .andExpect(jsonPath("$[0].question").value("What is the capital of France?"))
                .andExpect(jsonPath("$[0].answers[1]").value("Paris"));
    }
}