package School.LMS.controller;
import School.LMS.dto.SubjectDTO;
import School.LMS.models.Subject;
import School.LMS.services.SubjectService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
@CrossOrigin
public class SubjectController {

    @Autowired
    private final SubjectService subjectService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('PRINCIPAL')")
    public ResponseEntity<Subject> createSubject(@RequestBody SubjectDTO subjectDTO) {
        Subject createdSubject = subjectService.createSubect(subjectDTO);
        return ResponseEntity.ok(createdSubject);
    }
    
}
