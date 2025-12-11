package School.LMS.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Data
public class ClassRoomDTO {
    @NotBlank(message = "Class name is required.")
    private String className;

    @Min(value = 1, message = "Grade level must be at least 1.")
    private Integer gradeLevel;

    private  String teacherusername; 
}