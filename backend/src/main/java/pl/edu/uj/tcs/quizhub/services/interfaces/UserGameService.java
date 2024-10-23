package pl.edu.uj.tcs.quizhub.services.interfaces;

import pl.edu.uj.tcs.quizhub.models.DTO.UserDTO;

public interface UserGameService {
    UserDTO findById(int id);
}
