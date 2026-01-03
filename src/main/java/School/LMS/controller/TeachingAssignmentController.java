package School.LMS.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import School.LMS.services.TeachingAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.access.prepost.PreAuthorize;
import School.LMS.models.ClassRoom;
import School.LMS.models.Subject;
import School.LMS.models.Teacher;
import School.LMS.services.ClassRoomService;
import School.LMS.services.SubjectService;
import School.LMS.services.TeacherService;
import School.LMS.dto.SubjectDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import School.LMS.models.TeachingAssignment;
//import School.LMS.dto.TeachingAssignmentDTO;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin
@RequestMapping("/class-teacher")
public class TeachingAssignmentController {

    private final TeachingAssignmentService service;
    private final SubjectService subjectService;

    public TeachingAssignmentController(
            TeachingAssignmentService service,
            SubjectService subjectService
    ) {
        this.service = service;
        this.subjectService = subjectService;
    }

    // Load subjects automatically for class
    @GetMapping("/class/{classId}/subjects")
    public List<SubjectDTO> getSubjects(@PathVariable Long classId) {
        return subjectService.getSubjectsForClass(classId);
    }

    // Assign teacher
    @PostMapping("/assign")
    public TeachingAssignment assignTeacher(
            @RequestParam Long classId,
            @RequestParam Long subjectId,
            @RequestParam Long teacherId
    ) {

        return service.assignTeacher(classId, subjectId, teacherId);
    }
}
