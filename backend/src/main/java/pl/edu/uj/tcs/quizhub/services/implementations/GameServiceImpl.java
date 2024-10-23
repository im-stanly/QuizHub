package pl.edu.uj.tcs.quizhub.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.uj.tcs.quizhub.models.Database.GameModel;
import pl.edu.uj.tcs.quizhub.repositories.GameRepository;
import pl.edu.uj.tcs.quizhub.services.interfaces.GameService;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    GameRepository gameRepository;

    @Override
    public GameModel save(GameModel gameModel) {
        if (gameModel == null)
            return null;
        return gameRepository.save(gameModel);
    }

    @Override
    public GameModel addUserToRoom(String room, String nickName) {
        Optional<GameModel> gameModelOptional = gameRepository.findByRoom(room);
        if (gameModelOptional.isPresent()) {
            GameModel gameModel = gameModelOptional.get();

            if (!gameModel.getUsersPermitted().contains(nickName)) {
                gameModel.getUsersPermitted().add(nickName);
                return gameRepository.save(gameModel);
            }
        }
        return null;
    }

    @Override
    public List<String> getAllUsersInRoom(String room) {
        Optional<GameModel> gameModelOptional = gameRepository.findByRoom(room);
        return gameModelOptional.map(GameModel::getUsersPermitted).orElse(List.of());
    }

    @Override
    public GameModel findGameByRoom(String room) {
        return gameRepository.findByRoom(room).orElse(null);
    }

    @Override
    public void deleteGame(String room) {
        Optional<GameModel> gameModelOptional = gameRepository.findByRoom(room);
        gameModelOptional.ifPresent(gameRepository::delete);
    }
}