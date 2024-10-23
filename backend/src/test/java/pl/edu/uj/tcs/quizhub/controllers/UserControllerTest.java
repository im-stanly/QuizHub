package pl.edu.uj.tcs.quizhub.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.edu.uj.tcs.quizhub.models.DTO.UserDTO;
import pl.edu.uj.tcs.quizhub.services.interfaces.Security;
import pl.edu.uj.tcs.quizhub.services.interfaces.UserService;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Autowired
    Security security;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDTO = UserDTO.builder().id(1).email("username@domain.com").username("user").password("password").build();
    }

    @Test
    void testGetUsers() {
        List<UserDTO> users = List.of(userDTO);
        when(userService.getUsers()).thenReturn(users);

        List<UserDTO> result = userController.getUsers();

        assertEquals(1, result.size());
        assertEquals("username@domain.com", result.get(0).getEmail());
        verify(userService, times(1)).getUsers();
    }

    @Test
    void testGetById() {
        when(userService.findById(1)).thenReturn(userDTO);

        UserDTO result = userController.findById(1);

        assertEquals("username@domain.com", result.getEmail());
        verify(userService, times(1)).findById(1);
    }

    @Test
    void testAddUserInvalidEmail() {
        userDTO.setEmail("invalid-email");

        ResponseEntity<Map<String, String>> response = userController.add(userDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().get("error").contains("Error"));
    }

    @Test
    void testGetUserByToken() {
        String token = "valid-token";
        when(userService.findById(anyInt())).thenReturn(userDTO);

        UserDTO result = userController.getUserByToken(token);

        assertEquals("username@domain.com", result.getEmail());
        verify(userService, times(1)).findById(anyInt());
    }

    @Test
    void testAddUser() {
        when(userService.save(any(UserDTO.class))).thenReturn(userDTO);

        ResponseEntity<Map<String, String>> response = userController.add(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().get("message").contains("Account created successfully."));
    }

    @Test
    void testLoginUser() {
        when(userService.getLoginToken(any(UserDTO.class))).thenReturn("valid-token");

        ResponseEntity<Map<String, String>> response = userController.loginUser(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().get("token").contains("valid-token"));
    }

    @Test
    void testDeleteUserUnauthorized() {
        String token = "invalid-token";

        ResponseEntity<Map<String, String>> response = userController.deleteUser(1, token);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().get("error").contains("Your session has end, please log in again"));
    }
}