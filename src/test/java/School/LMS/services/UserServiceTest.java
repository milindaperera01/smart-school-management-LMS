package School.LMS.services;

import School.LMS.dto.UserRegReq;
import School.LMS.dto.UserRes;
import School.LMS.models.Users;
import School.LMS.repos.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private AuthenticationProvider authManager;

    @Mock
    private JWTservice jwtservice;

    @InjectMocks
    private UserService userService;

    @Test
    void register_Success() {
        UserRegReq req = new UserRegReq();
        req.setUsername("testuser");
        req.setPassword("password");
        req.setRole("STUDENT");

        when(userRepo.findByUsername("testuser")).thenReturn(null);
        when(encoder.encode("password")).thenReturn("encodedPassword");
        
        when(userRepo.save(any(Users.class))).thenAnswer(invocation -> {
            Users u = invocation.getArgument(0);
            u.setId(1L);
            return u;
        });

        UserRes res = userService.register(req);

        assertNotNull(res);
        assertEquals("testuser", res.getUsername());
        assertEquals("STUDENT", res.getRole());
        assertEquals(1L, res.getId());
        
        verify(userRepo).save(any(Users.class));
    }

    @Test
    void register_Fail_UsernameExists() {
        UserRegReq req = new UserRegReq();
        req.setUsername("existing");
        
        when(userRepo.findByUsername("existing")).thenReturn(new Users());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.register(req);
        });

        assertEquals("Username already exists", exception.getMessage());
        verify(userRepo, never()).save(any(Users.class));
    }

    @Test
    void verify_Success() {
        Users user = new Users();
        user.setUsername("testuser");
        user.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtservice.generator("testuser")).thenReturn("mock-token");

        String token = userService.verify(user);

        assertEquals("mock-token", token);
    }

    @Test
    void verify_Fail() {
        Users user = new Users();
        user.setUsername("testuser");
        user.setPassword("wrongpassword");

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        String result = userService.verify(user);

        assertEquals("User not verified", result);
    }
}
