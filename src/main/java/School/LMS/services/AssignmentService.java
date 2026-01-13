package School.LMS.services;

import School.LMS.dto.*;
import School.LMS.models.*;
import School.LMS.repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
@RequiredArgsConstructor
public class AssignmentService {

    @Autowired
    private final AssignmentRepo assignmentRepo;

    @Autowired
    private final ClassRoomRepo classRoomRepo;

    @Autowired
    private final SubjectRepo subjectRepo;

    @Autowired
    private final TeacherRepo teacherRepo;

    @Autowired
    private final StudentRepo studentRepo;

    @Autowired
    private final StudentAssignmentRepo studentAssignmentRepo;

    public Assignment createAssignmentEntity(CreateAssignmentDTO dto) {

        ClassRoom classRoom = classRoomRepo.findById(dto.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        Subject subject = subjectRepo.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Teacher teacher = teacherRepo.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Assignment assignment = new Assignment();
        assignment.setTitle(dto.getTitle());
        assignment.setDescription(dto.getDescription());
        assignment.setDueDate(dto.getDueDate());
        assignment.setClassRoom(classRoom);
        assignment.setSubject(subject);
        assignment.setCreatedBy(teacher);

        return assignmentRepo.save(assignment);
    }

    public Assignment saveAssignment(Assignment assignment) {
        return assignmentRepo.save(assignment);
    }

    public AssignmentDTO toAssignmentDTO(Assignment a) {
        return new AssignmentDTO(
                a.getId(),
                a.getTitle(),
                a.getDescription(),
                a.getDueDate(),
                a.getClassRoom().getId(),
                a.getClassRoom().getClassName(),
                a.getSubject().getId(),
                a.getSubject().getName(),
                a.getCreatedBy().getId(),
                a.getCreatedBy().getName()
        );
    }

    public List<AssignmentDTO> getAssignmentsForClassSubject(Long classId, Long subjectId) {
        return assignmentRepo.findByClassRoomIdAndSubjectId(classId, subjectId)
                .stream()
                .map(this::toAssignmentDTO)
                .toList();
    }


    public StudentAssignment submitAssignmentEntity(SubmitAssignmentDTO dto, String username) {

        Student student = studentRepo.findByUserUsername(username)
            .orElseThrow(() -> new RuntimeException("Student not found"));

        Assignment assignment = assignmentRepo.findById(dto.getAssignmentId())
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        StudentAssignment sa = new StudentAssignment();
        sa.setStudent(student);
        sa.setAssignment(assignment);
        sa.setAnswerText(dto.getAnswerText()); // optional
        sa.setSubmittedAt(LocalDateTime.now());
        sa.setStatus("SUBMITTED");

        return studentAssignmentRepo.save(sa);
    }

    public StudentAssignment saveStudentAssignment(StudentAssignment sa) {
        return studentAssignmentRepo.save(sa);
    }

    public StudentAssignmentDTO toStudentAssignmentDTO(StudentAssignment s) {
        return new StudentAssignmentDTO(
                s.getId(),
                s.getStudent().getId(),
                s.getAssignment().getTitle(),
                s.getAssignment().getId(),
                s.getSubmissionFile(),
                s.getSubmittedAt(),
                s.getStatus(),
                s.getGrade()
        );
    }

    public List<StudentAssignmentDTO> getSubmissionsForStudent(Long studentId) {
        return studentAssignmentRepo.findByStudentId(studentId)
                .stream()
                .map(this::toStudentAssignmentDTO)
                .toList();
    }
    public ResponseEntity<Resource> downloadAssignmentPdf(Long assignmentId) {

    Assignment assignment = assignmentRepo.findById(assignmentId)
            .orElseThrow(() -> new RuntimeException("Assignment not found"));

    if (assignment.getFilePath() == null) {
        throw new RuntimeException("No PDF attached to this assignment");
    }

    try {
        Path filePath = Paths.get("uploads/assignments/")
                .resolve(assignment.getFilePath())
                .normalize();

        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("File not found");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + assignment.getFilePath() + "\""
                )
                .body(resource);

    } catch (Exception e) {
        throw new RuntimeException("Failed to download assignment PDF", e);
    }
}
    public AssignmentDetailsDTO getAssignmentDTOById(Long id) {
    Assignment a = assignmentRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Assignment not found"));
     return new AssignmentDetailsDTO(
            a.getId(),
            a.getTitle(),
            a.getDescription(),
            a.getDueDate(),
            a.getFilePath(),        // ✅ exposed only here
            a.getClassRoom().getId(),
            a.getSubject().getId(),
            a.getCreatedBy().getId()
    );
}

public List<StudentAssignmentDTO> getMySubmissionsForSubject(Long subjectId, String username) {

    Student student = studentRepo.findByUserUsername(username)
            .orElseThrow(() -> new RuntimeException("Student not found"));

    return studentAssignmentRepo
            .findByStudentIdAndAssignmentSubjectId(student.getId(), subjectId)
            .stream()
            .map(this::toStudentAssignmentDTO)
            .toList();
}
public List<StudentAssignmentDTO> getSubmissionsForAssignment(Long assignmentId) {
    return studentAssignmentRepo.findByAssignmentId(assignmentId)
            .stream()
            .map(this::toStudentAssignmentDTO)
            .toList();
}

public StudentAssignmentDTO getMySubmissionForAssignment(
        Long assignmentId,
        String username
) {
    StudentAssignment submission = studentAssignmentRepo
            .findByAssignmentIdAndStudentUserUsername(assignmentId, username)
            .orElseThrow(() -> new RuntimeException("No submission found"));

    return toStudentAssignmentDTO(submission);
}


public ResponseEntity<Resource> downloadSubmissionPdf(Long submissionId) {
    StudentAssignment submission =
            studentAssignmentRepo.findById(submissionId)
                    .orElseThrow(() ->
                            new RuntimeException("Submission not found"));

    if (submission.getSubmissionFile() == null) {
        throw new RuntimeException("No file uploaded");
    }

    try {
        Path filePath = Paths.get("uploads/submissions/")
                .resolve(submission.getSubmissionFile())
                .normalize();

        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("File not found");
        }

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + submission.getSubmissionFile() + "\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);

    } catch (Exception e) {
        throw new RuntimeException("Failed to download submission", e);
    }
}




}
