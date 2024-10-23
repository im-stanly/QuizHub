package pl.edu.uj.tcs.quizhub.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.edu.uj.tcs.quizhub.models.Database.GameModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameModelTest {
    private GameModel gameModel;

    @BeforeEach
    void setUp() {
        gameModel = GameModel.builder()
                .quizID(123)
                .quizName("Sample Quiz")
                .room("Room 101")
                .usersPermitted(List.of("user1", "user2", "user3"))
                .build();
    }

    @Test
    void testGameModelCreation() {
        assertNotNull(gameModel);
    }

    @Test
    void testGetQuizID() {
        assertEquals(123, gameModel.getQuizID());
    }

    @Test
    void testGetQuizName() {
        assertEquals("Sample Quiz", gameModel.getQuizName());
    }

    @Test
    void testGetRoom() {
        assertEquals("Room 101", gameModel.getRoom());
    }

    @Test
    void testGetUsersPermitted() {
        List<String> users = gameModel.getUsersPermitted();
        assertEquals(3, users.size());
        assertTrue(users.contains("user1"));
        assertTrue(users.contains("user2"));
        assertTrue(users.contains("user3"));
    }

    @Test
    void testSetQuizID() {
        gameModel.setQuizID(456);
        assertEquals(456, gameModel.getQuizID());
    }

    @Test
    void testSetQuizName() {
        gameModel.setQuizName("New Quiz");
        assertEquals("New Quiz", gameModel.getQuizName());
    }

    @Test
    void testSetRoom() {
        gameModel.setRoom("Room 102");
        assertEquals("Room 102", gameModel.getRoom());
    }

    @Test
    void testSetUsersPermitted() {
        List<String> newUsers = List.of("newUser1", "newUser2");
        gameModel.setUsersPermitted(newUsers);
        assertEquals(2, gameModel.getUsersPermitted().size());
        assertTrue(gameModel.getUsersPermitted().contains("newUser1"));
        assertTrue(gameModel.getUsersPermitted().contains("newUser2"));
    }

    @Test
    void testEmptyUsersPermittedList() {
        gameModel.setUsersPermitted(List.of());
        assertEquals(0, gameModel.getUsersPermitted().size());
    }

    @Test
    void testNullUsersPermitted() {
        gameModel.setUsersPermitted(null);
        assertNull(gameModel.getUsersPermitted());
    }

    @Test
    void testGameModelEquality() {
        GameModel anotherGameModel = GameModel.builder()
                .quizID(123)
                .quizName("Sample Quiz")
                .room("Room 101")
                .usersPermitted(List.of("user1", "user2", "user3"))
                .build();

        assertEquals(gameModel, anotherGameModel);
    }

    @Test
    void testGameModelInequality() {
        GameModel differentGameModel = GameModel.builder()
                .quizID(124)
                .quizName("Different Quiz")
                .room("Room 102")
                .usersPermitted(List.of("user4", "user5"))
                .build();

        assertNotEquals(gameModel, differentGameModel);
    }

    @Test
    void testToString() {
        String expectedString = "GameModel(id=0, quizID=123, quizName=Sample Quiz, room=Room 101, usersPermitted=[user1, user2, user3])";
        assertEquals(expectedString, gameModel.toString());
    }

    @Test
    void testMockInteraction() {
        GameModel mockGameModel = Mockito.mock(GameModel.class);

        when(mockGameModel.getQuizID()).thenReturn(789);
        when(mockGameModel.getQuizName()).thenReturn("Mocked Quiz");
        when(mockGameModel.getRoom()).thenReturn("Mock Room");

        assertEquals(789, mockGameModel.getQuizID());
        assertEquals("Mocked Quiz", mockGameModel.getQuizName());
        assertEquals("Mock Room", mockGameModel.getRoom());

        verify(mockGameModel).getQuizID();
        verify(mockGameModel).getQuizName();
        verify(mockGameModel).getRoom();
    }

    @Test
    void testNullQuizName() {
        GameModel gameModelWithNullName = GameModel.builder()
                .quizID(123)
                .quizName(null)
                .room("Room 101")
                .usersPermitted(List.of("user1"))
                .build();

        assertNull(gameModelWithNullName.getQuizName());
    }

    @Test
    void testEmptyRoomName() {
        GameModel gameModelWithEmptyRoom = GameModel.builder()
                .quizID(123)
                .quizName("Sample Quiz")
                .room("")
                .usersPermitted(List.of("user1"))
                .build();

        assertEquals("", gameModelWithEmptyRoom.getRoom());
    }
}
