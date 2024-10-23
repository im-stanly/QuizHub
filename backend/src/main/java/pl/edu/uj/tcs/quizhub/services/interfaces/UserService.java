package pl.edu.uj.tcs.quizhub.services.interfaces;

import pl.edu.uj.tcs.quizhub.models.DTO.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();

    UserDTO save(UserDTO newUser);

    void delete(int id);

    UserDTO findById(int id);

    String getLoginToken(UserDTO loginData, String encryptedPassword);

    String getLoginToken(UserDTO loginData);

    boolean isUsernameTaken(String username);

    List<UserDTO> findByEmail(String email);

    UserDTO findByUsername(String username);

    UserDTO findByUsernameAndPassword(String username, String password);
}
