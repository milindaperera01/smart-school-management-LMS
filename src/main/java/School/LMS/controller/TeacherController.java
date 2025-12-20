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
}
