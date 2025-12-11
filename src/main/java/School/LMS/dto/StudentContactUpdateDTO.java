package School.LMS.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
@Data
public class StudentContactUpdateDTO {
    @NotBlank(message = "home contact is required.")
    private String home_contact1;

    private String home_contact2;
}