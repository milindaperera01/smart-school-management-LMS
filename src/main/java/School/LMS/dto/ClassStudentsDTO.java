package School.LMS.dto;

import lombok.*;
import java.util.List;
import School.LMS.models.Student;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassStudentsDTO {
    private String className;
    private int gradeLevel;
    private List<StudentDTO> students;
}
