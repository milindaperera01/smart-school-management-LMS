package School.LMS.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import School.LMS.dto.SubjectDTO;
import java.util.List;
@Data
@AllArgsConstructor
public class StudentSubjectDTO {
    private int gradeLevel;
    private String classname;
    private List<SubjectDTO> subjects;



}