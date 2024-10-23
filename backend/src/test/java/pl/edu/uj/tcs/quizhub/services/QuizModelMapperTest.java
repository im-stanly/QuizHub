package pl.edu.uj.tcs.quizhub.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.uj.tcs.quizhub.models.DTO.QuizDTO;
import pl.edu.uj.tcs.quizhub.models.Database.QuizModel;
import pl.edu.uj.tcs.quizhub.models.Database.TagModel;
import pl.edu.uj.tcs.quizhub.services.mappers.QuizModelMapper;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class QuizModelMapperTest {

    private QuizModelMapper quizModelMapper;

    @BeforeEach
    void setUp() {
        quizModelMapper = new QuizModelMapper();
    }

    @Test
    void testOnto_NullInput() {
        QuizModel quizModel = quizModelMapper.onto(null);
        assertNull(quizModel);
    }

    @Test
    void testOnto_ValidInput() {
        Set<String> tags = new HashSet<>();
        tags.add("Java");
        tags.add("Spring");

        QuizDTO quizDTO = QuizDTO.builder()
                .id(1)
                .creatorId(100)
                .name("Java Quiz")
                .description("A quiz about Java")
                .tags(tags)
                .build();

        QuizModel quizModel = quizModelMapper.onto(quizDTO);

        assertEquals(quizDTO.getId(), quizModel.getId());
        assertEquals(quizDTO.getCreatorId(), quizModel.getCreatorId());
        assertEquals(quizDTO.getName(), quizModel.getName());
        assertEquals(quizDTO.getDescription(), quizModel.getDescription());
    }

    @Test
    void testFrom_NullInput() {
        QuizDTO quizDTO = quizModelMapper.from(null);
        assertNull(quizDTO);
    }

    @Test
    void testFrom_ValidInput() {
        Set<TagModel> tagModels = new HashSet<>();
        tagModels.add(TagModel.builder().id(1).name("Java").build());
        tagModels.add(TagModel.builder().id(2).name("Spring").build());

        QuizModel quizModel = QuizModel.builder()
                .id(1)
                .creatorId(100)
                .name("Java Quiz")
                .description("A quiz about Java")
                .tags(tagModels)
                .build();

        QuizDTO quizDTO = quizModelMapper.from(quizModel);

        assertEquals(quizModel.getId(), quizDTO.getId());
        assertEquals(quizModel.getCreatorId(), quizDTO.getCreatorId());
        assertEquals(quizModel.getName(), quizDTO.getName());
        assertEquals(quizModel.getDescription(), quizDTO.getDescription());
        assertEquals(2, quizDTO.getTags().size());
        assertEquals(Set.of("Java", "Spring"), quizDTO.getTags());
    }
}
