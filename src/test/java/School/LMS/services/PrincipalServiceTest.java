package School.LMS.services;

import School.LMS.dto.PrincipalRegistrationDTO;
import School.LMS.models.Principal;
import School.LMS.models.Users;
import School.LMS.repos.PrincipalRepo;
import School.LMS.repos.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrincipalServiceTest {

    @Mock
    private PrincipalRepo principalRepository;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private PrincipalService principalService;

    @Test
    void registerProfile_Success() {
        PrincipalRegistrationDTO dto = new PrincipalRegistrationDTO();
        dto.setName("Principal Skinner");
        
        Users user = new Users();
        user.setUsername("admin");
        
        when(userRepo.findByUsername("admin")).thenReturn(user);
        when(principalRepository.findByUserUsername("admin")).thenReturn(Optional.empty());

        principalService.registerProfile(dto, "admin");

        verify(principalRepository).save(any(Principal.class));
    }

    @Test
    void registerProfile_AlreadyExists() {
        when(userRepo.findByUsername("admin")).thenReturn(new Users());
        when(principalRepository.findByUserUsername("admin")).thenReturn(Optional.of(new Principal()));

        assertThrows(RuntimeException.class, () -> 
            principalService.registerProfile(new PrincipalRegistrationDTO(), "admin")
        );
    }
}
