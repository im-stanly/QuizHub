package pl.edu.uj.tcs.quizhub.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.uj.tcs.quizhub.models.DTO.UserDTO;
import pl.edu.uj.tcs.quizhub.models.Database.UserModel;
import pl.edu.uj.tcs.quizhub.models.UserPermission;
import pl.edu.uj.tcs.quizhub.services.interfaces.ModelMapper;
import pl.edu.uj.tcs.quizhub.services.mappers.UserModelMapper;

import static org.junit.jupiter.api.Assertions.*;

public class UserModelMapperTest {

    private ModelMapper<UserDTO, UserModel> userModelMapper;

    @BeforeEach
    void setUp() {
        userModelMapper = new UserModelMapper();
    }

    @Test
    void testOnto() {
        UserDTO userDTO = UserDTO.builder()
                .id(1)
                .email("user@example.com")
                .username("testuser")
                .password("password123")
                .build();

        UserModel userModel = userModelMapper.onto(userDTO);

        assertNotNull(userModel);
        assertEquals(1, userModel.getId());
        assertEquals("user@example.com", userModel.getEmail());
        assertEquals("testuser", userModel.getUsername());
        assertEquals("password123", userModel.getPassword());
        assertEquals(UserPermission.USER, userModel.getUserPermissions());
    }

    @Test
    void testOnto_Null() {
        UserModel userModel = userModelMapper.onto(null);

        assertNull(userModel);
    }

    @Test
    void testFrom() {
        UserModel userModel = UserModel.builder()
                .id(1)
                .email("user@example.com")
                .username("testuser")
                .password("password123")
                .build();

        UserDTO userDTO = userModelMapper.from(userModel);

        assertNotNull(userDTO);
        assertEquals(1, userDTO.getId());
        assertEquals("user@example.com", userDTO.getEmail());
        assertEquals("testuser", userDTO.getUsername());
        assertNull(userDTO.getPassword());
    }

    @Test
    void testFrom_Null() {
        UserDTO userDTO = userModelMapper.from(null);

        assertNull(userDTO);
    }

    @Test
    void testOnto_MissingFields() {
        UserDTO userDTO = UserDTO.builder()
                .email("user@example.com")
                .build();

        UserModel userModel = userModelMapper.onto(userDTO);

        assertNotNull(userModel);
        assertEquals("user@example.com", userModel.getEmail());
        assertNull(userModel.getUsername());
        assertNull(userModel.getPassword());
    }

    @Test
    void testFrom_MissingFields() {
        UserModel userModel = UserModel.builder()
                .username("testuser")
                .build();

        UserDTO userDTO = userModelMapper.from(userModel);

        assertNotNull(userDTO);
        assertEquals("testuser", userDTO.getUsername());
        assertEquals(0, userDTO.getId());
        assertNull(userDTO.getEmail());
    }
}

