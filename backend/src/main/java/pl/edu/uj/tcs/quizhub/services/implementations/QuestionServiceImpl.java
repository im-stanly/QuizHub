package pl.edu.uj.tcs.quizhub.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.uj.tcs.quizhub.models.DTO.QuestionDTO;
import pl.edu.uj.tcs.quizhub.models.Database.QuestionModel;
import pl.edu.uj.tcs.quizhub.repositories.QuestionRepository;
import pl.edu.uj.tcs.quizhub.repositories.QuizRepository;
import pl.edu.uj.tcs.quizhub.services.interfaces.ModelMapper;
import pl.edu.uj.tcs.quizhub.services.interfaces.QuestionService;
import pl.edu.uj.tcs.quizhub.services.interfaces.QuestionServiceSocket;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.min;
import static java.util.Collections.shuffle;

@Service
public class QuestionServiceImpl implements QuestionService, QuestionServiceSocket {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private ModelMapper<QuestionDTO, QuestionModel> questionModelMapper;

    @Override
    @Transactional
    public int save(QuestionDTO question) {
        QuestionModel questionModel = questionModelMapper.onto(question);
        questionModel.setId(0);
        questionModel.setQuiz(quizRepository.findById(question.getQuizId()).orElseThrow());
        questionRepository.save(questionModel);
        return HttpURLConnection.HTTP_ACCEPTED;
    }

    @Override
    public List<QuestionDTO> getAll() {
        return questionRepository.findAll().stream().map(x -> questionModelMapper.from(x)).toList();
    }

    @Override
    public List<QuestionDTO> findByQuizId(int quizId) {
        return questionRepository.findByQuizId(quizId).stream().map(x -> questionModelMapper.from(x)).toList();
    }

    @Override
    public Optional<QuestionDTO> findById(int id) {
        return questionRepository.findById(id).map(x -> questionModelMapper.from(x));
    }

    @Override
    public QuestionDTO patch(int id, QuestionDTO question) {
        return updateOrPatch(id, question, true);
    }

    @Override
    public QuestionDTO update(int id, QuestionDTO question) {
        return updateOrPatch(id, question, false);
    }

    @Override
    public void delete(int id) {
        questionRepository.deleteById(id);
    }

    private QuestionDTO updateOrPatch(int id, QuestionDTO updatedQuestion, boolean isPatch) {
        QuestionModel oldQuestion = questionRepository.findById(id).orElseThrow();

        if (isPatch) {
            if (updatedQuestion.getQuestion() != null) {
                oldQuestion.setQuestion(updatedQuestion.getQuestion());
            }
            if (updatedQuestion.getCorrectAnswerId() != -1) {
                oldQuestion.setCorrectAnswerId(updatedQuestion.getCorrectAnswerId());
            }
            if (updatedQuestion.getAnswers() != null) {
                oldQuestion.setAnswers(updatedQuestion.getAnswers());
            }
        } else {
            oldQuestion = questionModelMapper.onto(updatedQuestion);
        }

        return questionModelMapper.from(questionRepository.save(oldQuestion));
    }

    public List<QuestionDTO> getNRandomQuestionsByQuizId(int n, int quizId) {
        List<QuestionDTO> questions = new ArrayList<>(findByQuizId(quizId));
        shuffle(questions);
        return questions.subList(0, min(n, questions.size()));
    }
}
