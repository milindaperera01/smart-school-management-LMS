package School.LMS.dto;
import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StudentAssignmentDTO {
    private Long id;
    private Long studentId;
    private String assignmentTitle;
    private Long assignmentId;
    private String submissionFile;
    private LocalDateTime submittedAt;
    private String status;
    private String grade;
}
