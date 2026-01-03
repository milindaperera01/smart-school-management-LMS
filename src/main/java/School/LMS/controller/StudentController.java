package School.LMS.controller;

import School.LMS.dto.StudentContactUpdateDTO;
import School.LMS.dto.StudentRegistrationDTO;
import School.LMS.models.Student;
import School.LMS.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.security.Principal;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@CrossOrigin
public class StudentController {
    @Autowired
    private final StudentService studentService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody StudentRegistrationDTO studentDTO, Principal principal) {
        studentService.registerStudent(studentDTO, principal.getName());
        return ResponseEntity.ok("Student registered successfully");
    }

    @PutMapping("/update-contact")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<?> updateHomeContact(@Valid @RequestBody StudentContactUpdateDTO contactDTO, Principal principal) {
        studentService.updateHomeContact(contactDTO, principal.getName());
        return ResponseEntity.ok("Home contact updated successfully");
    }

    @GetMapping("/my-subjects")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<?> getMySubjects(Principal principal) {
        return ResponseEntity.ok(studentService.getMySubjects(principal.getName()));
    }

    @GetMapping("/status")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<?> getStudentStatus(Principal principal) {
        return ResponseEntity.ok(studentService.getStudentStatus(principal.getName()));
    }
}