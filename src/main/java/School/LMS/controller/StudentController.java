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
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody StudentRegistrationDTO studentDTO, Principal principal) {
        studentService.registerStudent(studentDTO, principal.getName());
        return ResponseEntity.ok("Student registered successfully");
    }

    @PutMapping("/update-contact")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> updateHomeContact(@Valid @RequestBody StudentContactUpdateDTO contactDTO, Principal principal) {
        studentService.updateHomeContact(contactDTO, principal.getName());
        return ResponseEntity.ok("Home contact updated successfully");
    }
}