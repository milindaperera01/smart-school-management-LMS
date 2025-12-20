package School.LMS.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
public class PrincipalRegistrationDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotNull(message = "Enter date is required")
    private LocalDate enterDate;

    @NotBlank(message = "Status is required")
    private String status;

    @NotBlank(message = "Contact is required")
    private String contact;
}
