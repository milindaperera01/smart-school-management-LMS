package School.LMS.services;

import School.LMS.dto.TeacherRegistrationDTO;
import School.LMS.models.Subject;
import School.LMS.models.Teacher;
import School.LMS.models.Users;
import School.LMS.repos.SubjectRepo;
import School.LMS.repos.TeacherRepo;
import School.LMS.repos.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherRepo teacherRepository;
    
    @Mock
    private SubjectRepo subjectRepository;

    @Mock
    private UserRepo usersRepo;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void registerProfile_Success() {
        TeacherRegistrationDTO dto = new TeacherRegistrationDTO();
        dto.setName("Mr. Smith");
        dto.setSubjectNames(Collections.singletonList("Math"));
        
        Users user = new Users();
        user.setUsername("teacher1");
        user.setRole("TEACHER");
        
        Subject subject = new Subject();
        subject.setName("Math");

        when(usersRepo.findByUsername("teacher1")).thenReturn(user);
        when(subjectRepository.findByName("Math")).thenReturn(Optional.of(subject));
        
        teacherService.registerProfile(dto, "teacher1");

        verify(teacherRepository).save(any(Teacher.class));
    }

    @Test
    void registerProfile_Fail_UserNotFound() {
        when(usersRepo.findByUsername("unknown")).thenReturn(null);
        
        assertThrows(RuntimeException.class, () -> 
            teacherService.registerProfile(new TeacherRegistrationDTO(), "unknown")
        );
    }
}
