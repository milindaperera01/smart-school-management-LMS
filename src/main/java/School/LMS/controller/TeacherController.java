package School.LMS.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.security.Principal;
import School.LMS.dto.TeacherRegistrationDTO;
import School.LMS.dto.TeacherClassStatusDTO;
import School.LMS.services.TeacherService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
import School.LMS.models.TeachingAssignment;
import School.LMS.models.Teacher;
import School.LMS.dto.TeacherDTO;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
@CrossOrigin
public class TeacherController {
    
    @Autowired
    private final TeacherService teacherService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<?> registerTeacherProfile(@Valid @RequestBody TeacherRegistrationDTO dto, Principal principal) {
        String username = principal.getName();
        teacherService.registerProfile(dto, username);
        return ResponseEntity.ok("Teacher profile registered successfully.");
    }

    @GetMapping("/class-status")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<?> chechTeacherClassStatus(Principal principal) {
        String username = principal.getName();
        TeacherClassStatusDTO statusDTO = teacherService.getTeacherClassStatus(username);
        return ResponseEntity.ok(statusDTO);
    }

    @GetMapping("/assignments")
    public ResponseEntity<?> getTeacherAssignments(
        Principal principal) {
    return ResponseEntity.ok(
        teacherService.getTeacherAssignments(principal.getName())
    );
    }

    @GetMapping("/all-teachers")
    public List<TeacherDTO> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

}

