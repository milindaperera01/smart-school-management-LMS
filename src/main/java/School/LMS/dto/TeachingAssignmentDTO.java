package School.LMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeachingAssignmentDTO {
    private Long id;          // ID of the assignment
    private Long classId;     // ID of the class
    private String className; // Optional: name of the class
    private Long subjectId;   // ID of the subject
    private String subjectName; // Optional: name of the subject
    private Long teacherId;   // ID of the teacher
    private String teacherName; // Optional: teacher's name
}
