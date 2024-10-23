package pl.edu.uj.tcs.quizhub.models.DTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
    private int id;
    private String email;
    private String username;
    private String password;
}
