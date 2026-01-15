package School.LMS.services;

import School.LMS.dto.StudentRegistrationDTO;
import School.LMS.models.ClassRoom;
import School.LMS.models.Student;
import School.LMS.models.Users;
import School.LMS.repos.ClassRoomRepo;
import School.LMS.repos.StudentRepo;
import School.LMS.repos.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private UserRepo userRepository;

    @Mock
    private StudentRepo studentRepository;

    @Mock
    private ClassRoomRepo classroomRepo;

    @InjectMocks
    private StudentService studentService;

    @Test
    void registerStudent_Success() {
        String username = "student1";
        StudentRegistrationDTO dto = new StudentRegistrationDTO();
        dto.setName("John Doe");
        dto.setGradeLevel(10);
        dto.setClassName("A");
        dto.setDateOfBirth("2010-01-01");
        
        Users user = new Users();
        user.setId(100L);
        user.setUsername(username);
        
        ClassRoom classroom = new ClassRoom();
        classroom.setId(1L);

        when(userRepository.findByUsername(username)).thenReturn(user);
        when(classroomRepo.findByGradeLevelAndClassName(10, "A")).thenReturn(Optional.of(classroom));
        when(studentRepository.findByUserUsername(username)).thenReturn(Optional.empty());
        when(studentRepository.save(any(Student.class))).thenAnswer(i -> i.getArgument(0));

        Student result = studentService.registerStudent(dto, username);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals(user, result.getUser());
        assertEquals(classroom, result.getClassRoom());
    }

    @Test
    void registerStudent_UserNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(null);
        
        assertThrows(RuntimeException.class, () -> 
            studentService.registerStudent(new StudentRegistrationDTO(), "unknown")
        );
    }
}
