package School.LMS.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;

@Data
public class ClassRoomUpdateDTO {
    private int gradeLevel;

    private Long teacherusername; 
}