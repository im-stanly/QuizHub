package pl.edu.uj.tcs.quizhub.models;

import org.junit.jupiter.api.Test;
import pl.edu.uj.tcs.quizhub.models.Database.QuizModel;
import pl.edu.uj.tcs.quizhub.models.Database.TagModel;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TagModelTest {

    @Test
    public void testDefaultConstructor() {
        TagModel tagModel = new TagModel();
        assertNotNull(tagModel);
    }

    @Test
    public void testAllArgsConstructor() {
        QuizModel quizModel = new QuizModel();
        Set<QuizModel> quizSet = new HashSet<>();
        quizSet.add(quizModel);

        TagModel tagModel = new TagModel(1, "Sample Tag", quizSet);

        assertEquals(1, tagModel.getId());
        assertEquals("Sample Tag", tagModel.getName());
        assertEquals(quizSet, tagModel.getQuizzes());
    }

    @Test
    public void testBuilderWithAllFields() {
        QuizModel quizModel = new QuizModel();
        Set<QuizModel> quizSet = new HashSet<>();
        quizSet.add(quizModel);

        TagModel tagModel = TagModel.builder()
                .id(1)
                .name("Sample Tag")
                .quizzes(quizSet)
                .build();

        assertEquals(1, tagModel.getId());
        assertEquals("Sample Tag", tagModel.getName());
        assertEquals(quizSet, tagModel.getQuizzes());
    }

    @Test
    public void testBuilderWithPartialFields() {
        TagModel tagModel = TagModel.builder()
                .name("Sample Tag")
                .build();

        assertEquals(0, tagModel.getId());
        assertEquals("Sample Tag", tagModel.getName());
    }

    @Test
    public void testEqualSets() {
        Set<TagModel> set1 = new HashSet<>();
        set1.add(TagModel.builder().id(1).name("Tag1").build());
        set1.add(TagModel.builder().id(2).name("Tag2").build());

        Set<TagModel> set2 = new HashSet<>();
        set2.add(TagModel.builder().id(2).name("Tag2").build());
        set2.add(TagModel.builder().id(1).name("Tag1").build());

        assertEquals(set1, set2);
    }

    @Test
    public void testNotEqualSets() {
        Set<TagModel> set1 = new HashSet<>();
        set1.add(TagModel.builder().id(1).name("Tag1").build());
        set1.add(TagModel.builder().id(2).name("Tag2").build());

        Set<TagModel> set2 = new HashSet<>();
        set2.add(TagModel.builder().id(1).name("Tag1").build());
        set2.add(TagModel.builder().id(3).name("Tag3").build());

        assertNotEquals(set1, set2);
    }
}