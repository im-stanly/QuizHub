package pl.edu.uj.tcs.quizhub.webClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.uj.tcs.quizhub.models.DTO.QuestionDTO;
import pl.edu.uj.tcs.quizhub.models.DTO.QuestionModelDTO;
import pl.edu.uj.tcs.quizhub.models.Database.QuestionModel;
import pl.edu.uj.tcs.quizhub.services.interfaces.ModelMapper;

import java.util.LinkedList;
import java.util.List;

@Component
public class QuestionMapperImpl implements QuestionMapper {
    @Autowired
    private QuestionsClient questionsClient;
    @Autowired
    private ModelMapper<QuestionDTO, QuestionModel> questionModelMapper;

    public List<QuestionDTO> getQuestions() {
        List<QuestionModel> questions = new LinkedList<>();
        do {
            List<QuestionModelDTO> questionList = questionsClient.getQuestions();
            questionList.forEach(qDTO -> {
                boolean isNot4Answers = qDTO.getAnswers().getAnswerE() != null || qDTO.getAnswers().getAnswerF() != null || qDTO.getAnswers().getAnswerD() == null || qDTO.getAnswers().getAnswerC() == null;

                if (qDTO.isMultipleCorrectAnswers() || isNot4Answers)
                    return;

                QuestionModel model = new QuestionModel();
                model.setId(qDTO.getId());
                model.setCorrectAnswerId(-1);

                if (qDTO.getCorrectAnswers().isAnswerACorrect())
                    model.setCorrectAnswerId(0);
                else if (qDTO.getCorrectAnswers().isAnswerBCorrect())
                    model.setCorrectAnswerId(1);
                else if (qDTO.getCorrectAnswers().isAnswerCCorrect())
                    model.setCorrectAnswerId(2);
                else if (qDTO.getCorrectAnswers().isAnswerDCorrect())
                    model.setCorrectAnswerId(3);
                else
                    return;

                model.setQuestion(qDTO.getQuestion());
                List<String> answers = new LinkedList<>();
                answers.add(qDTO.getAnswers().getAnswerA());
                answers.add(qDTO.getAnswers().getAnswerB());
                answers.add(qDTO.getAnswers().getAnswerC());
                answers.add(qDTO.getAnswers().getAnswerD());

                model.setAnswers(answers);
                questions.add(model);
            });
        } while (questions.size() < 10);

        return questions.stream().map(x -> questionModelMapper.from(x)).toList();
    }
}