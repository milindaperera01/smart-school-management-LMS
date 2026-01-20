package School.LMS.services;

import School.LMS.dto.BatchMarksAdd;
import School.LMS.dto.MarksAdd;
import School.LMS.dto.StudentExamMarkDTO;
import School.LMS.models.ClassRoom;
import School.LMS.models.Marks;
import School.LMS.models.Student;
import School.LMS.models.Subject;
import School.LMS.repos.ClassRoomRepo;
import School.LMS.repos.MarksRepo;
import School.LMS.repos.StudentRepo;
import School.LMS.repos.SubjectRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MarksServiceTest {

    @Mock
    private MarksRepo marksRepo;

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private SubjectRepo subjectRepo;

    @Mock
    private ClassRoomRepo classRoomRepo;

    @InjectMocks
    private MarksService marksService;

    @Test
    void addMark_Success() {
        MarksAdd req = new MarksAdd();
        req.setStudentId(1L);
        req.setClassId(1L);
        req.setSubjectId(1L);
        req.setScore(85.0);

        when(studentRepo.findById(1L)).thenReturn(Optional.of(new Student()));
        when(classRoomRepo.findById(1L)).thenReturn(Optional.of(new ClassRoom()));
        when(subjectRepo.findById(1L)).thenReturn(Optional.of(new Subject()));

        String result = marksService.addMark(req);

        assertEquals("Marks added successfully", result);
        verify(marksRepo).save(any(Marks.class));
    }

    @Test
    void addBatchMarks_Success() {
        BatchMarksAdd req = new BatchMarksAdd();
        req.setClassroom("10-A");
        req.setSubject("Math");
        req.setExam_name("MidTerm");
        Map<String, Double> marksMap = new HashMap<>();
        marksMap.put("1", 90.0);
        marksMap.put("2", 80.0);
        req.setMarks(marksMap);

        ClassRoom classroom = new ClassRoom();
        classroom.setGradeLevel(10);
        
        Subject subject = new Subject();
        subject.setName("Math");
        
        Student s1 = new Student(); s1.setId(1L);
        Student s2 = new Student(); s2.setId(2L);

        when(classRoomRepo.findByGradeLevelAndClassName(10, "A")).thenReturn(Optional.of(classroom));
        when(subjectRepo.findByName("Math")).thenReturn(Optional.of(subject));
        when(studentRepo.findById(1L)).thenReturn(Optional.of(s1));
        when(studentRepo.findById(2L)).thenReturn(Optional.of(s2));

        String result = marksService.addBatchMarks(req);

        assertTrue(result.contains("Successfully added marks for 2 students"));
        verify(marksRepo).saveAll(anyList());
    }

    @Test
    void getMarksForStudentExam_ReturnsList() {
        Long studentId = 5L;
        String examName = "MidTerm";

        Student student = new Student();
        student.setId(studentId);

        Subject subject = new Subject();
        subject.setId(9L);

        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(3L);

        Marks mark = new Marks();
        mark.setId(11L);
        mark.setExam_name(examName);
        mark.setScore(78.5);
        mark.setStudent(student);
        mark.setSubject(subject);
        mark.setClassroom(classRoom);

        when(studentRepo.existsById(studentId)).thenReturn(true);
        when(marksRepo.findByStudentIdAndExamName(studentId, examName)).thenReturn(List.of(mark));

        List<StudentExamMarkDTO> result = marksService.getMarksForStudentExam(studentId, examName);

        assertEquals(1, result.size());
        assertEquals(11L, result.get(0).id());
        assertEquals(examName, result.get(0).examName());
        assertEquals(78.5, result.get(0).score());
        assertEquals(9L, result.get(0).subjectId());
        assertEquals(3L, result.get(0).classId());
    }
}
