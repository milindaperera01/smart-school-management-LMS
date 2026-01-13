package School.LMS.dto;
import lombok.*;

@Data
@AllArgsConstructor
public class SubmitAssignmentDTO {
    private Long studentId;
    private Long assignmentId;
    private String answerText;
}
