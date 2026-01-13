package School.LMS.controller;

import School.LMS.dto.*;
import School.LMS.models.Assignment;
import School.LMS.models.StudentAssignment;
import School.LMS.services.AssignmentService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.security.Principal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;


@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
@CrossOrigin
public class AssignmentController {

    private final AssignmentService assignmentService;

    private static final String ASSIGNMENT_DIR = "uploads/assignments/";
    private static final String SUBMISSION_DIR = "uploads/submissions/";

    @PostMapping(value = "/create", consumes = "multipart/form-data")
public AssignmentDTO createAssignment(
        @RequestPart("data") CreateAssignmentDTO dto,
        @RequestPart(value = "file", required = false) MultipartFile file,
        @RequestHeader(value = "Authorization", required = false) String authHeader
) throws IOException {
    System.out.println("💡 Received /create request");
    System.out.println("💡 Authorization header: " + authHeader);
    System.out.println("💡 Assignment title: " + dto.getTitle());
    System.out.println("💡 File present: " + (file != null && !file.isEmpty()));

    Assignment assignment = assignmentService.createAssignmentEntity(dto);

    if (file != null && !file.isEmpty()) {
        Files.createDirectories(Paths.get(ASSIGNMENT_DIR));

        String fileName = "assignment_" + assignment.getId() + "_" + file.getOriginalFilename();
        Path path = Paths.get(ASSIGNMENT_DIR + fileName);
        Files.write(path, file.getBytes());

        assignment.setFilePath(fileName);
        assignmentService.saveAssignment(assignment);
    }

    return assignmentService.toAssignmentDTO(assignment);
}



    @GetMapping("/class/{classId}/subject/{subjectId}")
    public List<AssignmentDTO> getAssignments(
            @PathVariable Long classId,
            @PathVariable Long subjectId
    ) {
        return assignmentService.getAssignmentsForClassSubject(classId, subjectId);
    }

    @PostMapping(value = "/submit", consumes = "multipart/form-data")
    public StudentAssignmentDTO submitAssignment(
            @RequestPart("data") SubmitAssignmentDTO dto,
            @RequestPart(value = "file", required = false) MultipartFile file, Principal principal
    ) throws IOException {
        String username = principal.getName();

        StudentAssignment submission = assignmentService.submitAssignmentEntity(dto, username);

        if (file != null && !file.isEmpty()) {
            Files.createDirectories(Paths.get(SUBMISSION_DIR));

            String fileName = "submission_" + submission.getId() + "_" + file.getOriginalFilename();
            Path path = Paths.get(SUBMISSION_DIR + fileName);
            Files.write(path, file.getBytes());

            submission.setSubmissionFile(fileName);
            assignmentService.saveStudentAssignment(submission);
        }

        return assignmentService.toStudentAssignmentDTO(submission);
    }

    @GetMapping("/my")
    public List<StudentAssignmentDTO> getMySubmissions(@RequestParam Long studentId) {
        return assignmentService.getSubmissionsForStudent(studentId);
    }

   @GetMapping("/download/{assignmentId}")
    public ResponseEntity<Resource> downloadAssignment(
            @PathVariable Long assignmentId
    ) throws IOException {
        return assignmentService.downloadAssignmentPdf(assignmentId);
    }
    @GetMapping("/{id}")
    public AssignmentDetailsDTO getAssignmentById(@PathVariable Long id) {
        return assignmentService.getAssignmentDTOById(id);
    }

    @GetMapping("/my/subject/{subjectId}")
    public List<StudentAssignmentDTO> getMySubmissionsForSubject(
            @PathVariable Long subjectId,
            Principal principal
    ) {
        return assignmentService.getMySubmissionsForSubject(subjectId, principal.getName());
    }

    @GetMapping("/{assignmentId}/submissions")
    public List<StudentAssignmentDTO> getSubmissionsForAssignment(
            @PathVariable Long assignmentId
    ) {
        return assignmentService.getSubmissionsForAssignment(assignmentId);
    }

    @GetMapping("/{assignmentId}/my-submission")
    public StudentAssignmentDTO getMySubmissionForAssignment(
            @PathVariable Long assignmentId,
            Principal principal
    ) {
        return assignmentService.getMySubmissionForAssignment(
                assignmentId,
                principal.getName()
        );
    }

    @GetMapping("/submissions/download/{submissionId}")
    public ResponseEntity<Resource> downloadSubmission(@PathVariable Long submissionId) {
        return assignmentService.downloadSubmissionPdf(submissionId);
    }





}
