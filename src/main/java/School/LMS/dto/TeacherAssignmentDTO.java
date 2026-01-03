package School.LMS.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherAssignmentDTO {

    private Long assignmentId;

    private Long classId;
    private int gradeLevel;
    private String className;

    private Long subjectId;
    private String subjectName;
}
