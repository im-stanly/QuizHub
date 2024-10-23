package pl.edu.uj.tcs.quizhub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.uj.tcs.quizhub.config.TokenUtils;
import pl.edu.uj.tcs.quizhub.exception.UserAccountException;
import pl.edu.uj.tcs.quizhub.models.DTO.UserDTO;
import pl.edu.uj.tcs.quizhub.services.interfaces.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/id/{id}")
    public UserDTO findById(@PathVariable("id") int id) {
        return userService.findById(id);
    }

    @GetMapping("/token")
    public UserDTO getUserByToken(@RequestHeader(value = "user-token") String userToken) {
        return userService.findById(TokenUtils.getUserID(userToken));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> add(@RequestBody UserDTO newAccount) {
        if (userService.save(newAccount) != null) {
            return ResponseEntity.ok(createSuccessResponse("Account created successfully."));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorResponse("Server Error."));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody UserDTO loginData) {
        Map<String, String> response = new HashMap<>();
        try {
            String token = userService.getLoginToken(loginData);

            response.put("success", "true");
            response.put("message", "User authenticated successfully.");
            response.put("token", token);
        } catch (UserAccountException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createErrorResponse("Wrong username or password."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorResponse("Server Error."));
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable("id") int id, @RequestHeader(value = "user-token") String userToken) {
        ResponseEntity<Map<String, String>> checkIfAccess = isOwnerOrAdmin(id, userToken);

        if (checkIfAccess != null) return checkIfAccess;

        Map<String, String> response = new HashMap<>();

        try {
            userService.delete(id);
            response.put("success", "true");
        } catch (UserAccountException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorResponse("Server Error."));
        }

        return ResponseEntity.ok(response);
    }

    private Map<String, String> createErrorResponse(String errorMessage) {
        Map<String, String> response = new HashMap<>();
        response.put("success", "false");
        response.put("error", errorMessage);
        return response;
    }

    private Map<String, String> createSuccessResponse(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("success", "true");
        response.put("message", message);
        return response;
    }

    private ResponseEntity<Map<String, String>> isOwnerOrAdmin(int id, String userToken) {
        if (!TokenUtils.isTokenValid(userToken))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createErrorResponse("Your session has end, please log in again"));

        int userIdFromToken = TokenUtils.getUserID(userToken);
        if (!TokenUtils.isAdmin(userToken) && (userIdFromToken == -1 || userIdFromToken != id))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createErrorResponse("You don't have permissions to do delete this user"));

        return null;
    }
}
