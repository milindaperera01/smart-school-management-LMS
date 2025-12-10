package School.LMS.dto;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarksAdd {

    @NotBlank(message = "Exam name is required.")
    private String examName;

    @NotNull(message = "Score is required.")
    private Double score;

    @NotNull(message = "Student ID is required.")
    private Long studentId;

    private Long subjectId;

    @NotNull(message = "Class ID is required.")
    private Long classId;
}
