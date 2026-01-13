package School.LMS.dto;
import lombok.*;

import java.time.LocalDate;import java.time.LocalDateTime;


@Data
public class CreateAssignmentDTO {
    private Long classId;
    private Long subjectId;
    private Long teacherId;
    private String title;
    private String description;
    private LocalDateTime dueDate;
}
