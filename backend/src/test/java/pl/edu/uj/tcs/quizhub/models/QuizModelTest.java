package pl.edu.uj.tcs.quizhub.models;

import org.junit.jupiter.api.Test;
import pl.edu.uj.tcs.quizhub.models.Database.QuestionModel;
import pl.edu.uj.tcs.quizhub.models.Database.QuizModel;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class QuizModelTest {

    @Test
    public void testDefaultConstructor() {
        QuizModel quizModel = new QuizModel();
        assertNotNull(quizModel);
    }

    @Test
    public void testAllArgsConstructor() {
        Set<QuestionModel> questions = new HashSet<>();
        QuizModel quizModel = new QuizModel(1, "Sample Quiz", "Sample Description", new HashSet<>(), questions, 42);

        assertEquals(1, quizModel.getId());
        assertEquals("Sample Quiz", quizModel.getName());
        assertEquals("Sample Description", quizModel.getDescription());
        assertEquals(questions, quizModel.getQuestions());
        assertEquals(42, quizModel.getCreatorId());
    }

    @Test
    public void testBuilderWithAllFields() {
        Set<QuestionModel> questions = new HashSet<>();
        QuizModel quizModel = QuizModel.builder()
                .id(1)
                .name("Sample Quiz")
                .description("Sample Description")
                .questions(questions)
                .tags(new HashSet<>())
                .creatorId(42)
                .build();

        assertEquals(1, quizModel.getId());
        assertEquals("Sample Quiz", quizModel.getName());
        assertEquals("Sample Description", quizModel.getDescription());
        assertEquals(questions, quizModel.getQuestions());
        assertEquals(42, quizModel.getCreatorId());
    }

    @Test
    public void testBuilderWithPartialFields() {
        QuizModel quizModel = QuizModel.builder()
                .name("Sample Quiz")
                .description("Sample Description")
                .build();

        assertEquals(0, quizModel.getId());
        assertEquals("Sample Quiz", quizModel.getName());
        assertEquals("Sample Description", quizModel.getDescription());
        assertNull(quizModel.getQuestions());
        assertEquals(0, quizModel.getCreatorId());
    }

    @Test
    public void testEqualSets() {
        Set<QuizModel> set1 = new HashSet<>();
        set1.add(QuizModel.builder().id(1).name("Quiz1").build());
        set1.add(QuizModel.builder().id(2).name("Quiz2").build());

        Set<QuizModel> set2 = new HashSet<>();
        set2.add(QuizModel.builder().id(1).name("Quiz1").build());
        set2.add(QuizModel.builder().id(2).name("Quiz2").build());

        assertEquals(set1, set2);
    }

    @Test
    public void testNotEqualSets() {
        Set<QuizModel> set1 = new HashSet<>();
        set1.add(QuizModel.builder().id(1).name("Quiz1").build());
        set1.add(QuizModel.builder().id(2).name("Quiz2").build());

        Set<QuizModel> set2 = new HashSet<>();
        set2.add(QuizModel.builder().id(1).name("Quiz1").build());
        set2.add(QuizModel.builder().id(3).name("Quiz3").build());

        assertNotEquals(set1, set2);
    }
}