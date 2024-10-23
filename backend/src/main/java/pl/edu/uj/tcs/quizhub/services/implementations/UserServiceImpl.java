package pl.edu.uj.tcs.quizhub.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.uj.tcs.quizhub.config.TokenUtils;
import pl.edu.uj.tcs.quizhub.exception.UserAccountException;
import pl.edu.uj.tcs.quizhub.models.DTO.UserDTO;
import pl.edu.uj.tcs.quizhub.models.Database.UserModel;
import pl.edu.uj.tcs.quizhub.repositories.UserRepository;
import pl.edu.uj.tcs.quizhub.services.interfaces.*;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserGameService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper<UserDTO, UserModel> userModelMapper;
    @Autowired
    EmailValidation emailValidation;
    @Autowired
    Security security;

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream().map(x -> userModelMapper.from(x)).toList();
    }

    @Override
    public UserDTO save(UserDTO newUser) {
        if (!emailValidation.isEmailValid(newUser.getEmail())) {
            throw new RuntimeException("Email address not valid");
        }

        String encryptedPassword = security.encode(newUser.getPassword());
        newUser.setPassword(encryptedPassword);

        return userModelMapper.from(userRepository.save(userModelMapper.onto(newUser)));
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO findById(int id) {
        UserModel response = userRepository.findById(id);
        if (response == null) {
            throw new UserAccountException("User with id: " + id + " doesn't exist");
        }
        return userModelMapper.from(response);
    }

    @Override
    public String getLoginToken(UserDTO loginData, String encryptedPassword) {
        UserModel account = userRepository.findByUsernameAndPassword(loginData.getUsername(), encryptedPassword);
        return TokenUtils.generateToken(account.getId(), account.getEmail(), account.getUsername(), account.getUserPermissions().name());
    }

    @Override
    public String getLoginToken(UserDTO loginData) {
        String encryptedPassword = security.encode(loginData.getPassword());
        loginData.setPassword(encryptedPassword);
        return getLoginToken(loginData, encryptedPassword);
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return !userRepository.findByUsername(username).isEmpty();
    }

    @Override
    public List<UserDTO> findByEmail(String email) {
        List<UserModel> response = userRepository.findByEmail(email);
        return response.stream().map(x -> userModelMapper.from(x)).toList();
    }

    @Override
    public UserDTO findByUsername(String username) {
        List<UserModel> response = userRepository.findByUsername(username);
        if (response.isEmpty()) {
            throw new UserAccountException("User with username: '" + username + "' doesn't exist");
        }
        return userModelMapper.from(response.getFirst());
    }

    @Override
    public UserDTO findByUsernameAndPassword(String username, String password) {
        UserModel response = userRepository.findByUsernameAndPassword(username, password);
        if (response == null) {
            throw new UserAccountException("Username or password is wrong");
        }
        return userModelMapper.from(response);
    }
}
