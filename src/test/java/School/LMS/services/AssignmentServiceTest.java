package School.LMS.services;

import School.LMS.dto.CreateAssignmentDTO;
import School.LMS.models.Assignment;
import School.LMS.models.ClassRoom;
import School.LMS.models.Subject;
import School.LMS.models.Teacher;
import School.LMS.repos.AssignmentRepo;
import School.LMS.repos.ClassRoomRepo;
import School.LMS.repos.StudentAssignmentRepo;
import School.LMS.repos.StudentRepo;
import School.LMS.repos.SubjectRepo;
import School.LMS.repos.TeacherRepo;
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
public class AssignmentServiceTest {

    @Mock
    private AssignmentRepo assignmentRepo;
    @Mock
    private ClassRoomRepo classRoomRepo;
    @Mock
    private SubjectRepo subjectRepo;
    @Mock
    private TeacherRepo teacherRepo;
    @Mock
    private StudentRepo studentRepo;
    @Mock
    private StudentAssignmentRepo studentAssignmentRepo;

    @InjectMocks
    private AssignmentService assignmentService;

    @Test
    void createAssignmentEntity_Success() {
        CreateAssignmentDTO dto = new CreateAssignmentDTO();
        dto.setClassId(1L);
        dto.setSubjectId(1L);
        dto.setTeacherId(1L);
        dto.setTitle("Test Assignment");
        
        when(classRoomRepo.findById(1L)).thenReturn(Optional.of(new ClassRoom()));
        when(subjectRepo.findById(1L)).thenReturn(Optional.of(new Subject()));
        when(teacherRepo.findById(1L)).thenReturn(Optional.of(new Teacher()));
        
        when(assignmentRepo.save(any(Assignment.class))).thenAnswer(i -> i.getArgument(0));

        Assignment result = assignmentService.createAssignmentEntity(dto);

        assertNotNull(result);
        assertEquals("Test Assignment", result.getTitle());
    }

    @Test
    void createAssignmentEntity_ClassNotFound() {
        CreateAssignmentDTO dto = new CreateAssignmentDTO();
        dto.setClassId(1L);
        
        when(classRoomRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> assignmentService.createAssignmentEntity(dto));
    }
}
