package pl.edu.uj.tcs.quizhub.services.interfaces;

import pl.edu.uj.tcs.quizhub.models.Database.GameModel;

import java.util.List;

public interface GameService {
    GameModel save(GameModel gameModel);

    GameModel addUserToRoom(String room, String nickName);

    List<String> getAllUsersInRoom(String room);

    GameModel findGameByRoom(String room);

    void deleteGame(String room);
}