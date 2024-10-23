package pl.edu.uj.tcs.quizhub.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.uj.tcs.quizhub.models.DTO.QuizDTO;
import pl.edu.uj.tcs.quizhub.models.Database.QuizModel;
import pl.edu.uj.tcs.quizhub.models.Database.TagModel;
import pl.edu.uj.tcs.quizhub.repositories.QuizRepository;
import pl.edu.uj.tcs.quizhub.repositories.TagRepository;
import pl.edu.uj.tcs.quizhub.services.interfaces.ModelMapper;
import pl.edu.uj.tcs.quizhub.services.interfaces.QuizGameService;
import pl.edu.uj.tcs.quizhub.services.interfaces.QuizService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class QuizServiceImpl implements QuizService, QuizGameService {
    @Autowired
    ModelMapper<QuizDTO, QuizModel> quizModelMapper;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    public QuizDTO save(QuizDTO quiz) {
        if (quiz == null) throw new RuntimeException("Provided data is null!");

        QuizModel quizModel = quizModelMapper.onto(quiz);
        Set<TagModel> tags = new HashSet<>();

        for (String tagName : quiz.getTags()) {
            System.out.println("Processing tag: " + tagName);

            TagModel tag = tagRepository.findByName(tagName).orElseGet(() -> TagModel.builder().name(tagName).quizzes(new HashSet<>()).build());
            tag.getQuizzes().add(quizModel);
            tags.add(tag);
        }
        quizModel.setTags(tags);
        quizModel.setId(0);

        return quizModelMapper.from(quizRepository.save(quizModel));
    }

    public List<QuizDTO> findByUserId(int userId) {
        return quizRepository.findByUserId(userId).stream().map(x -> quizModelMapper.from(x)).toList();
    }

    public List<QuizDTO> findByNameSubstring(String nameSubstring) {
        return quizRepository.findByNameSubstring(nameSubstring).stream().map(x -> quizModelMapper.from(x)).toList();
    }

    public List<QuizDTO> findByTag(String tag) {
        TagModel tagModel = tagRepository.findByName(tag).orElseThrow();
        return quizRepository.findByTagModel(tagModel).stream().map(x -> quizModelMapper.from(x)).toList();
    }

    public Optional<QuizDTO> findById(int id) {
        return quizRepository.findById(id).map(x -> quizModelMapper.from(x));
    }

    public QuizDTO patch(int id, QuizDTO quiz) {
        QuizModel quizModel = quizModelMapper.onto(quiz);
        Set<TagModel> tags = new HashSet<>();

        for (String tagName : quiz.getTags()) {
            System.out.println("Processing tag: " + tagName);

            TagModel tag = tagRepository.findByName(tagName).orElseGet(() -> TagModel.builder().name(tagName).quizzes(new HashSet<>()).build());
            tag.getQuizzes().add(quizModel);
            tags.add(tag);
        }
        quizModel.setTags(tags);
        return quizModelMapper.from(updateOrPatch(id, quizModel, true));
    }

    public QuizDTO update(int id, QuizDTO quiz) {
        return quizModelMapper.from(updateOrPatch(id, quizModelMapper.onto(quiz), false));
    }

    public void delete(int id) {
        quizRepository.deleteById(id);
    }

    private QuizModel updateOrPatch(int id, QuizModel quiz, boolean isPatch) {
        QuizModel oldQuiz = quizRepository.findById(id).orElseThrow();

        if (isPatch) {
            if (quiz.getDescription() != null) oldQuiz.setDescription(quiz.getDescription());
            if (quiz.getTags() != null) oldQuiz.setTags(quiz.getTags());
            if (quiz.getName() != null) oldQuiz.setName(quiz.getName());
            if (quiz.getQuestions() != null) oldQuiz.setQuestions(quiz.getQuestions());
        } else {
            oldQuiz = quiz;
        }

        return quizRepository.save(oldQuiz);
    }
}
