package pl.edu.uj.tcs.quizhub.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.uj.tcs.quizhub.models.Database.GameModel;
import pl.edu.uj.tcs.quizhub.repositories.GameRepository;
import pl.edu.uj.tcs.quizhub.services.implementations.GameServiceImpl;

class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameServiceImpl gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveWithValidGameModel() {
        GameModel gameModel = new GameModel();
        when(gameRepository.save(any(GameModel.class))).thenReturn(gameModel);

        GameModel result = gameService.save(gameModel);

        assertNotNull(result);
        verify(gameRepository, times(1)).save(gameModel);
    }

    @Test
    void testSaveWithNullGameModel() {
        GameModel result = gameService.save(null);

        assertNull(result);
        verify(gameRepository, never()).save(any());
    }

    @Test
    void testAddUserToRoomWhenGameModelExistsAndUserNotInRoom() {
        GameModel gameModel = new GameModel();
        // Use mutable ArrayList instead of List.of()
        gameModel.setUsersPermitted(new ArrayList<>(List.of("User1")));
        when(gameRepository.findByRoom("Room1")).thenReturn(Optional.of(gameModel));
        when(gameRepository.save(any(GameModel.class))).thenReturn(gameModel);

        GameModel result = gameService.addUserToRoom("Room1", "User2");

        assertNotNull(result);
        assertTrue(result.getUsersPermitted().contains("User2"));
        verify(gameRepository, times(1)).findByRoom("Room1");
        verify(gameRepository, times(1)).save(gameModel);
    }

    @Test
    void testAddUserToRoomWhenGameModelDoesNotExist() {
        when(gameRepository.findByRoom("Room1")).thenReturn(Optional.empty());

        GameModel result = gameService.addUserToRoom("Room1", "User1");

        assertNull(result);
        verify(gameRepository, times(1)).findByRoom("Room1");
        verify(gameRepository, never()).save(any());
    }

    @Test
    void testGetAllUsersInRoomWhenGameModelExists() {
        GameModel gameModel = new GameModel();
        gameModel.setUsersPermitted(List.of("User1", "User2"));
        when(gameRepository.findByRoom("Room1")).thenReturn(Optional.of(gameModel));

        List<String> users = gameService.getAllUsersInRoom("Room1");

        assertEquals(2, users.size());
        assertTrue(users.contains("User1"));
        assertTrue(users.contains("User2"));
        verify(gameRepository, times(1)).findByRoom("Room1");
    }

    @Test
    void testGetAllUsersInRoomWhenGameModelDoesNotExist() {
        when(gameRepository.findByRoom("Room1")).thenReturn(Optional.empty());

        List<String> users = gameService.getAllUsersInRoom("Room1");

        assertEquals(0, users.size());
        verify(gameRepository, times(1)).findByRoom("Room1");
    }

    @Test
    void testFindGameByRoomWhenGameModelExists() {
        GameModel gameModel = new GameModel();
        when(gameRepository.findByRoom("Room1")).thenReturn(Optional.of(gameModel));

        GameModel result = gameService.findGameByRoom("Room1");

        assertNotNull(result);
        verify(gameRepository, times(1)).findByRoom("Room1");
    }

    @Test
    void testFindGameByRoomWhenGameModelDoesNotExist() {
        when(gameRepository.findByRoom("Room1")).thenReturn(Optional.empty());

        GameModel result = gameService.findGameByRoom("Room1");

        assertNull(result);
        verify(gameRepository, times(1)).findByRoom("Room1");
    }

    @Test
    void testDeleteGameWhenGameModelExists() {
        GameModel gameModel = new GameModel();
        when(gameRepository.findByRoom("Room1")).thenReturn(Optional.of(gameModel));

        gameService.deleteGame("Room1");

        verify(gameRepository, times(1)).findByRoom("Room1");
        verify(gameRepository, times(1)).delete(gameModel);
    }

    @Test
    void testDeleteGameWhenGameModelDoesNotExist() {
        when(gameRepository.findByRoom("Room1")).thenReturn(Optional.empty());

        gameService.deleteGame("Room1");

        verify(gameRepository, times(1)).findByRoom("Room1");
        verify(gameRepository, never()).delete(any());
    }
}