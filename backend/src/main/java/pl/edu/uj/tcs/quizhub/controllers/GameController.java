package pl.edu.uj.tcs.quizhub.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.uj.tcs.quizhub.models.DTO.GameInitialDataDTO;
import pl.edu.uj.tcs.quizhub.models.DTO.QuizDTO;
import pl.edu.uj.tcs.quizhub.models.Database.GameModel;
import pl.edu.uj.tcs.quizhub.services.interfaces.GameService;
import pl.edu.uj.tcs.quizhub.services.interfaces.QuizGameService;
import pl.edu.uj.tcs.quizhub.services.interfaces.UserGameService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
    @Autowired
    private final QuizGameService quizService;
    @Autowired
    private final UserGameService userService;
    @Autowired
    private final GameService gameService;

    @GetMapping("/{room}")
    public GameModel getGameDetails(@PathVariable("room") String room) {
        return gameService.findGameByRoom(room);
    }

    @PostMapping("/new")
    public String getGameRoom(@RequestBody GameInitialDataDTO data) {
        QuizDTO quiz = quizService.findById(data.getQuizID()).orElseThrow();

        String creatorNickName = userService.findById(data.getUserID()).getUsername();
        String room = getHashedRoom(quiz.getCreatorId());

        gameService.save(GameModel.builder()
                .quizID(quiz.getId())
                .usersPermitted(List.of(creatorNickName))
                .room(room)
                .quizName(quiz.getName())
                .build());

        return room;
    }

    @PostMapping("/{room}/add_user")
    public GameModel addUserToRoom(@PathVariable("room") String room, @RequestBody String userName) {
        return gameService.addUserToRoom(room, userName);
    }

    private String getHashedRoom(int creatorId) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            String input = creatorId + "-" + currentTimeMillis;

            byte[] idBytes = input.getBytes(StandardCharsets.UTF_8);

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(idBytes);

            int part1 = ((digest[0] & 0xFF) << 16) | ((digest[1] & 0xFF) << 8) | (digest[2] & 0xFF);
            int part2 = ((digest[3] & 0xFF) << 16) | ((digest[4] & 0xFF) << 8) | (digest[5] & 0xFF);

            part1 = Math.abs(part1 % 1000);
            part2 = Math.abs(part2 % 1000);

            return String.format("%03d%03d", part1, part2);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }
}