package pl.edu.uj.tcs.quizhub.services.interfaces;

import com.corundumstudio.socketio.SocketIOClient;
import pl.edu.uj.tcs.quizhub.models.GameState;

public interface SocketService {
    void lobbyUpdate(String event, String room, SocketIOClient senderClient, String question, GameState state, String nickname);

    void startGame(String room, SocketIOClient senderClient, int quizId);

    void checkAnswer(String room, SocketIOClient senderClient, int usersAnswer, String username);
}
