package School.LMS.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Data
public class StudentRegistrationDTO {
    
    @NotBlank(message = "Student name is required.")
    private String Name;

    @NotBlank(message = "date of birth is required.")
    private String dateOfBirth;

    @NotBlank(message = "gender is required.")
    private String gender;

    @NotBlank(message = "status is required.")
    private String status;

    @NotBlank(message = "home contact is required.")
    private String home_contact1;

    private String home_contact2;


    @Min(value = 1, message = "Grade level must be valid")
    private Integer gradeLevel;

    @NotBlank(message = "Class name is required.")
    private String className;

}