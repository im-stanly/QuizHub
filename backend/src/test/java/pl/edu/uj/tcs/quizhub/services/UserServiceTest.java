package pl.edu.uj.tcs.quizhub.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.uj.tcs.quizhub.models.DTO.UserDTO;
import pl.edu.uj.tcs.quizhub.models.Database.UserModel;
import pl.edu.uj.tcs.quizhub.repositories.UserRepository;
import pl.edu.uj.tcs.quizhub.services.implementations.UserServiceImpl;
import pl.edu.uj.tcs.quizhub.services.interfaces.EmailValidation;
import pl.edu.uj.tcs.quizhub.services.interfaces.ModelMapper;
import pl.edu.uj.tcs.quizhub.services.interfaces.Security;
import pl.edu.uj.tcs.quizhub.services.interfaces.UserService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper<UserDTO, UserModel> userModelMapper;
    @Mock
    private EmailValidation emailValidation;
    @Mock
    private Security security;
    private UserDTO sampleUserDTO;
    private UserModel sampleUserModel;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
        MockitoAnnotations.openMocks(this);

        sampleUserDTO = UserDTO.builder()
                .id(1)
                .username("testUser")
                .email("test@example.com")
                .password("password")
                .build();

        sampleUserModel = UserModel.builder()
                .id(1)
                .username("testUser")
                .email("test@example.com")
                .password("password")
                .build();
    }

    @Test
    void testFindById_success() {
        when(userRepository.findById(1)).thenReturn(sampleUserModel);
        when(userModelMapper.from(sampleUserModel)).thenReturn(sampleUserDTO);

        UserDTO result = userService.findById(1);

        assertNotNull(result);
        assertEquals(sampleUserDTO, result);
    }

    @Test
    void testFindByUsername() {
        when(userRepository.findByUsername("testUser")).thenReturn(Collections.singletonList(sampleUserModel));
        when(userModelMapper.from(sampleUserModel)).thenReturn(sampleUserDTO);

        UserDTO result = userService.findByUsername("testUser");

        assertNotNull(result);
        assertEquals(sampleUserDTO, result);
    }

    @Test
    void testFindByEmail() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Collections.singletonList(sampleUserModel));
        when(userModelMapper.from(sampleUserModel)).thenReturn(sampleUserDTO);

        List<UserDTO> result = userService.findByEmail("test@example.com");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleUserDTO, result.get(0));
    }

    @Test
    void testFindByUsernameAndPassword() {
        when(userRepository.findByUsernameAndPassword("testUser", "password")).thenReturn(sampleUserModel);
        when(userModelMapper.from(sampleUserModel)).thenReturn(sampleUserDTO);

        UserDTO result = userService.findByUsernameAndPassword("testUser", "password");

        assertNotNull(result);
        assertEquals(sampleUserDTO, result);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1);

        userService.delete(1);

        verify(userRepository, times(1)).deleteById(1);
    }
}