package pl.edu.uj.tcs.quizhub.webClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.edu.uj.tcs.quizhub.models.DTO.QuestionModelDTO;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class QuestionsClientImpl implements QuestionsClient {
    private static final String API_URL = "https://quizapi.io/api/v1/questions";
    private static final String API_KEY = "cjN5xQkxhRK3bfLdXIhskBR1O0Vl8daPUGnWSRLZ";
    private static final int LIMIT = 20;
    private static final String CATEGORY = "Linux";

    // docs for api https://quizapi.io/docs/1.0/overview

    public List<QuestionModelDTO> getQuestions() {
        RestTemplate restTemplate = new RestTemplate();
        String url = buildUrl();

        String jsonResponse = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonResponse, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private String buildUrl() {
        return String.format("%s?apiKey=%s&limit=%d&category=%s", API_URL, API_KEY, LIMIT, CATEGORY);
    }
}