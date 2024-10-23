package pl.edu.uj.tcs.quizhub.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.uj.tcs.quizhub.models.DTO.QuizDTO;
import pl.edu.uj.tcs.quizhub.services.interfaces.QuizService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizController.class)
public class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    @Autowired
    private ObjectMapper objectMapper;

    private QuizDTO sampleQuizDTO;

    @BeforeEach
    void setUp() {
        sampleQuizDTO = QuizDTO.builder()
                .id(1)
                .name("Sample Quiz")
                .description("Sample Description")
                .tags(Set.of("tag1", "tag2"))
                .creatorId(42)
                .build();
    }

    @Test
    public void testGetQuizById_success() throws Exception {
        Mockito.when(quizService.findById(1)).thenReturn(Optional.of(sampleQuizDTO));

        mockMvc.perform(get("/quiz/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Sample Quiz"))
                .andExpect(jsonPath("$.description").value("Sample Description"))
                .andExpect(jsonPath("$.creatorId").value(42))
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags", containsInAnyOrder("tag1", "tag2")));
    }

    @Test
    public void testSaveQuiz_success() throws Exception {
        QuizDTO quizDTO = QuizDTO.builder()
                .name("New Quiz")
                .description("New Description")
                .tags(Set.of("tag1", "tag2"))
                .creatorId(1)
                .build();

        QuizDTO savedQuizDTO = QuizDTO.builder()
                .id(1)
                .name("New Quiz")
                .description("New Description")
                .tags(Set.of("tag1", "tag2"))
                .creatorId(1)
                .build();

        Mockito.when(quizService.save(Mockito.any(QuizDTO.class))).thenReturn(savedQuizDTO);

        mockMvc.perform(post("/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quizDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Quiz"))
                .andExpect(jsonPath("$.description").value("New Description"))
                .andExpect(jsonPath("$.tags", containsInAnyOrder("tag1", "tag2")))
                .andExpect(jsonPath("$.creatorId").value(1));
    }

    @Test
    public void testPatchQuiz_success() throws Exception {
        QuizDTO quizDTO = QuizDTO.builder()
                .id(1)
                .name("Updated Quiz")
                .description("Updated Description")
                .tags(Set.of("tag1", "tag2"))
                .creatorId(1)
                .build();

        Mockito.when(quizService.patch(Mockito.eq(1), Mockito.any(QuizDTO.class))).thenReturn(quizDTO);

        mockMvc.perform(patch("/quiz/id/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quizDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Quiz"));
    }

    @Test
    public void testPutQuiz_success() throws Exception {
        QuizDTO newQuizDTO = QuizDTO.builder()
                .name("Updated Quiz")
                .description("Updated Description")
                .tags(Set.of("updated", "quiz"))
                .creatorId(2)
                .build();

        QuizDTO updatedQuizDTO = QuizDTO.builder()
                .id(1)
                .name("Updated Quiz")
                .description("Updated Description")
                .tags(Set.of("updated", "quiz"))
                .creatorId(2)
                .build();

        Mockito.when(quizService.update(Mockito.eq(1), Mockito.any(QuizDTO.class))).thenReturn(updatedQuizDTO);

        mockMvc.perform(put("/quiz/id/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newQuizDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Quiz"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags", containsInAnyOrder("updated", "quiz")))
                .andExpect(jsonPath("$.creatorId").value(2));
    }

    @Test
    public void testDeleteQuiz_success() throws Exception {
        Mockito.doNothing().when(quizService).delete(1);

        mockMvc.perform(delete("/quiz/id/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetQuizByNameSubstring_success() throws Exception {
        List<QuizDTO> quizzes = List.of(
                QuizDTO.builder().id(1).name("Math Quiz").description("Basic Math").tags(Set.of("math")).creatorId(1).build(),
                QuizDTO.builder().id(2).name("Advanced Math Quiz").description("Advanced Math").tags(Set.of("math")).creatorId(1).build()
        );

        Mockito.when(quizService.findByNameSubstring("Math")).thenReturn(quizzes);

        mockMvc.perform(get("/quiz/name/Math"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Math Quiz"))
                .andExpect(jsonPath("$[1].name").value("Advanced Math Quiz"));
    }

    @Test
    public void testGetQuizByTag_success() throws Exception {
        List<QuizDTO> quizzes = List.of(
                QuizDTO.builder().id(1).name("History Quiz").description("World History").tags(Set.of("history")).creatorId(1).build(),
                QuizDTO.builder().id(2).name("Modern History Quiz").description("Modern Times").tags(Set.of("history")).creatorId(1).build()
        );

        Mockito.when(quizService.findByTag("history")).thenReturn(quizzes);

        mockMvc.perform(get("/quiz/tag/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("History Quiz"))
                .andExpect(jsonPath("$[1].name").value("Modern History Quiz"));
    }

    @Test
    public void testGetUserQuizzes_success() throws Exception {
        List<QuizDTO> quizzes = List.of(
                QuizDTO.builder().id(1).name("User Quiz 1").description("Description 1").tags(Set.of("tag1")).creatorId(1).build(),
                QuizDTO.builder().id(2).name("User Quiz 2").description("Description 2").tags(Set.of("tag2")).creatorId(1).build()
        );

        Mockito.when(quizService.findByUserId(1)).thenReturn(quizzes);

        mockMvc.perform(get("/quiz/userId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("User Quiz 1"))
                .andExpect(jsonPath("$[1].name").value("User Quiz 2"));
    }

    @Test
    public void testSaveQuiz_invalidInput() throws Exception {
        QuizDTO invalidQuizDTO = QuizDTO.builder()
                .description("New Description")
                .tags(Set.of("tag1", "tag2"))
                .creatorId(1)
                .build();

        QuizDTO savedQuizDTO = QuizDTO.builder()
                .id(1)
                .name(null)
                .description("New Description")
                .tags(Set.of("tag1", "tag2"))
                .creatorId(1)
                .build();

        Mockito.when(quizService.save(Mockito.any(QuizDTO.class))).thenReturn(savedQuizDTO);

        mockMvc.perform(post("/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidQuizDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").doesNotExist())
                .andExpect(jsonPath("$.description").value("New Description"))
                .andExpect(jsonPath("$.tags", containsInAnyOrder("tag1", "tag2")))
                .andExpect(jsonPath("$.creatorId").value(1));
    }
}