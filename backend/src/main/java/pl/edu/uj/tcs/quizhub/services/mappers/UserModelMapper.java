package pl.edu.uj.tcs.quizhub.services.mappers;

import org.springframework.stereotype.Service;
import pl.edu.uj.tcs.quizhub.models.DTO.UserDTO;
import pl.edu.uj.tcs.quizhub.models.Database.UserModel;
import pl.edu.uj.tcs.quizhub.services.interfaces.ModelMapper;

@Service
public class UserModelMapper implements ModelMapper<UserDTO, UserModel> {
    public UserModel onto(UserDTO userDTO) {
        if (userDTO == null) return null;
        return UserModel.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .build();
    }

    public UserDTO from(UserModel userModel) {
        if (userModel == null) return null;
        return UserDTO.builder()
                .id(userModel.getId())
                .email(userModel.getEmail())
                .username(userModel.getUsername())
                .build();
    }
}
