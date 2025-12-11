package School.LMS.dto;
import lombok.Data;
import java.util.List;
import jakarta.validation.constraints.*;

@Data
public class TeacherRegistrationDTO {
    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Date of Birth is required.")
    private String dateOfBirth;

    @NotBlank(message = "Gender is required.")
    private String gender;

    @NotBlank(message = "Status is required.")
    private String status;

    @NotEmpty(message = "At least one subject must be selected.")
    private List<Long> subjectIds; 
}
