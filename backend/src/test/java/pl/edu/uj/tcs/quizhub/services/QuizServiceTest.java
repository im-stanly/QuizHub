package pl.edu.uj.tcs.quizhub.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.uj.tcs.quizhub.models.DTO.QuizDTO;
import pl.edu.uj.tcs.quizhub.models.Database.QuizModel;
import pl.edu.uj.tcs.quizhub.models.Database.TagModel;
import pl.edu.uj.tcs.quizhub.repositories.QuizRepository;
import pl.edu.uj.tcs.quizhub.repositories.TagRepository;
import pl.edu.uj.tcs.quizhub.services.implementations.QuizServiceImpl;
import pl.edu.uj.tcs.quizhub.services.interfaces.ModelMapper;
import pl.edu.uj.tcs.quizhub.services.interfaces.QuizService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class QuizServiceTest {

    @InjectMocks
    private QuizService quizService;
    @Mock
    private QuizRepository quizRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ModelMapper<QuizDTO, QuizModel> quizModelMapper;

    private QuizDTO sampleQuizDTO;
    private QuizModel sampleQuizModel;

    @BeforeEach
    void setUp() {
        quizService = new QuizServiceImpl();
        MockitoAnnotations.openMocks(this);

        sampleQuizDTO = QuizDTO.builder()
                .id(1)
                .name("Sample Quiz")
                .description("Sample Description")
                .tags(Set.of("tag1", "tag2"))
                .creatorId(1)
                .build();

        sampleQuizModel = QuizModel.builder()
                .id(1)
                .name("Sample Quiz")
                .description("Sample Description")
                .tags(new HashSet<>())
                .creatorId(1)
                .build();
    }

    @Test
    void testSaveQuiz_success() {
        when(quizModelMapper.onto(sampleQuizDTO)).thenReturn(sampleQuizModel);
        when(quizRepository.count()).thenReturn(0L);
        when(tagRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(quizRepository.save(any(QuizModel.class))).thenReturn(sampleQuizModel);
        when(quizModelMapper.from(any(QuizModel.class))).thenReturn(sampleQuizDTO);

        QuizDTO result = quizService.save(sampleQuizDTO);

        assertNotNull(result);
        assertEquals(sampleQuizDTO.getName(), result.getName());
        assertEquals(sampleQuizDTO.getDescription(), result.getDescription());
        assertEquals(sampleQuizDTO.getTags(), result.getTags());

        verify(quizRepository).save(sampleQuizModel);
    }

    @Test
    void testFindById_success() {
        when(quizRepository.findById(1)).thenReturn(Optional.of(sampleQuizModel));
        when(quizModelMapper.from(sampleQuizModel)).thenReturn(sampleQuizDTO);

        Optional<QuizDTO> result = quizService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(sampleQuizDTO, result.get());
    }

    @Test
    void testFindById_notFound() {
        when(quizRepository.findById(1)).thenReturn(Optional.empty());

        Optional<QuizDTO> result = quizService.findById(1);

        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteQuiz() {
        doNothing().when(quizRepository).deleteById(1);

        quizService.delete(1);

        verify(quizRepository, times(1)).deleteById(1);
    }

    @Test
    void testUpdateQuiz_success() {
        when(quizRepository.findById(1)).thenReturn(Optional.of(sampleQuizModel));
        when(quizModelMapper.onto(sampleQuizDTO)).thenReturn(sampleQuizModel);
        when(quizRepository.save(any(QuizModel.class))).thenReturn(sampleQuizModel);
        when(quizModelMapper.from(any(QuizModel.class))).thenReturn(sampleQuizDTO);

        QuizDTO result = quizService.update(1, sampleQuizDTO);

        assertNotNull(result);
        assertEquals(sampleQuizDTO.getName(), result.getName());
        assertEquals(sampleQuizDTO.getDescription(), result.getDescription());
        assertEquals(sampleQuizDTO.getTags(), result.getTags());

        verify(quizRepository).save(sampleQuizModel);
    }

    @Test
    void testPatchQuiz_success() {
        when(quizRepository.findById(1)).thenReturn(Optional.of(sampleQuizModel));
        when(quizModelMapper.onto(sampleQuizDTO)).thenReturn(sampleQuizModel);
        when(quizRepository.save(any(QuizModel.class))).thenReturn(sampleQuizModel);
        when(quizModelMapper.from(any(QuizModel.class))).thenReturn(sampleQuizDTO);

        QuizDTO result = quizService.patch(1, sampleQuizDTO);

        assertNotNull(result);
        assertEquals(sampleQuizDTO.getName(), result.getName());
        assertEquals(sampleQuizDTO.getDescription(), result.getDescription());
        assertEquals(sampleQuizDTO.getTags(), result.getTags());

        verify(quizRepository).save(sampleQuizModel);
    }

    @Test
    void testFindByUserId() {
        when(quizRepository.findByUserId(1)).thenReturn(List.of(sampleQuizModel));
        when(quizModelMapper.from(sampleQuizModel)).thenReturn(sampleQuizDTO);

        List<QuizDTO> result = quizService.findByUserId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleQuizDTO, result.get(0));
    }

    @Test
    void testFindByNameSubstring() {
        when(quizRepository.findByNameSubstring("Sample")).thenReturn(List.of(sampleQuizModel));
        when(quizModelMapper.from(sampleQuizModel)).thenReturn(sampleQuizDTO);

        List<QuizDTO> result = quizService.findByNameSubstring("Sample");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleQuizDTO, result.get(0));
    }

    @Test
    void testFindByTag() {
        TagModel tagModel = TagModel.builder().name("tag1").build();
        when(tagRepository.findByName("tag1")).thenReturn(Optional.of(tagModel));
        when(quizRepository.findByTagModel(tagModel)).thenReturn(List.of(sampleQuizModel));
        when(quizModelMapper.from(sampleQuizModel)).thenReturn(sampleQuizDTO);

        List<QuizDTO> result = quizService.findByTag("tag1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleQuizDTO, result.get(0));
    }
}