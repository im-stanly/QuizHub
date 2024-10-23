package pl.edu.uj.tcs.quizhub.services.implementations;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.uj.tcs.quizhub.models.DTO.QuestionDTO;
import pl.edu.uj.tcs.quizhub.models.GameState;
import pl.edu.uj.tcs.quizhub.models.UserStatsModel;
import pl.edu.uj.tcs.quizhub.services.interfaces.GameService;
import pl.edu.uj.tcs.quizhub.services.interfaces.QuestionServiceSocket;
import pl.edu.uj.tcs.quizhub.services.interfaces.SocketService;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class SocketServiceImpl implements SocketService {
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Map<String, UserStatsModel> userStats = new HashMap<>();
    private Map<String, Set<String>> usersPlaying = new HashMap<>();
    private final int TIME_MS_TO_ANSWER = 20000;

    @Autowired
    QuestionServiceSocket serviceSocket;

    @Autowired
    GameService gameService;

    public void lobbyUpdate(String event, String room, SocketIOClient senderClient, String quizName, GameState state, String username) {
        usersPlaying.putIfAbsent(room, new HashSet<>());
        usersPlaying.get(room).add(username);
        sendObject(event, room, senderClient, List.of(state, quizName, getClientsInRoom(senderClient, room)));
    }

    public void startGame(String room, SocketIOClient senderClient, int quizId) {
        prepareStatsForUsers(room, quizId);

        executorService.submit(() -> {
            while (userStats.get(room).hasNextQuestion()) {
                try {
                    Thread.sleep(2000);

                    QuestionDTO questionNow = userStats.get(room).getCurrentQuestion();
                    sendObject("read_question", room, senderClient, List.of(userStats.get(room).getIndexQuestion() + 1, questionNow));

                    Thread.sleep(TIME_MS_TO_ANSWER);

                    sendObject("read_answer", room, senderClient, questionNow.getCorrectAnswerId());

                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread was interrupted, failed to complete the question cycle");
                    return;
                }
            }

            sendFinalResults(room, senderClient);
        });
    }

    public void checkAnswer(String room, SocketIOClient senderClient, int usersAnswer, String username) {
        userStats.get(room).checkAnswer(username, usersAnswer);
    }

    private Set<String> getClientsInRoom(SocketIOClient senderClient, String roomName) {
        return senderClient.getNamespace().getRoomOperations(roomName).getClients().stream().map(client -> client.getHandshakeData().getSingleUrlParam("username")).collect(Collectors.toSet());
    }

    private void sendObject(String event, String room, SocketIOClient senderClient, Object object) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients())
            client.sendEvent(event, object);
    }

    private void prepareStatsForUsers(String room, int quizId) {
        userStats.remove(room);

        List<QuestionDTO> questionsNow = serviceSocket.getNRandomQuestionsByQuizId(5, quizId);
        HashMap<String, Integer> usersPoints = new HashMap<>();

        for (String nick : usersPlaying.get(room).stream().toList())
            usersPoints.put(nick, 0);

        userStats.put(room, new UserStatsModel(usersPoints, questionsNow, -1));
    }

    private void sendFinalResults(String room, SocketIOClient senderClient) {
        sendObject("final_results", room, senderClient, userStats.get(room).getPointsForUser());

        userStats.remove(room);
        usersPlaying.get(room).clear();
        gameService.deleteGame(room);
    }
}