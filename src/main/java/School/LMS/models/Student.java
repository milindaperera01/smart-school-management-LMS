package School.LMS.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
public class Student {
    @Id
    private Long id;

    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String status;
    @NotBlank
    private String home_contact1;
    private String home_contact2;
    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassRoom classRoom; 

    

}
