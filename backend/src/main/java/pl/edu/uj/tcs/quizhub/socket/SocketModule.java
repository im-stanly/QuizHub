package pl.edu.uj.tcs.quizhub.socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.uj.tcs.quizhub.models.Database.GameModel;
import pl.edu.uj.tcs.quizhub.models.GameState;
import pl.edu.uj.tcs.quizhub.services.interfaces.GameService;
import pl.edu.uj.tcs.quizhub.services.interfaces.SocketService;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SocketModule {
    private final SocketIOServer server;
    private final SocketService socketService;
    private final Map<String, GameState> roomState;
    @Autowired
    GameService gameService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.roomState = new HashMap<>();
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());

        server.addEventListener("start_game", Object.class, startGame());
        server.addEventListener("check_answer", Integer.class, checkAnswer());
    }

    private ConnectListener onConnected() {
        return client -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            String nickname = client.getHandshakeData().getSingleUrlParam("username");

            client.joinRoom(room);

            synchronized (roomState) {
                roomState.putIfAbsent(room, GameState.LOBBY);
            }

            socketService.lobbyUpdate("room_members_update", room, client, gameService.findGameByRoom(room).getQuizName(), roomState.get(room), nickname);
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            String nickname = client.getHandshakeData().getSingleUrlParam("username");

            socketService.lobbyUpdate("room_members_update", room, client, gameService.findGameByRoom(room).getQuizName(), roomState.get(room), nickname);
        };
    }

    private DataListener<Object> startGame() {
        return (client, data, ackSender) -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            String nickname = client.getHandshakeData().getSingleUrlParam("username");
            GameModel game = gameService.findGameByRoom(room);

            roomState.replace(room, GameState.PLAYING);

            socketService.lobbyUpdate("room_members_update", room, client, gameService.findGameByRoom(room).getQuizName(), roomState.get(room), nickname);
            socketService.startGame(room, client, game.getQuizID());
        };
    }

    private DataListener<Integer> checkAnswer() {
        return (client, data, ackSender) -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            String nickname = client.getHandshakeData().getSingleUrlParam("username");

            socketService.checkAnswer(room, client, data, nickname);
        };
    }
}