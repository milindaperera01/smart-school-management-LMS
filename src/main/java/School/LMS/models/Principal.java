package School.LMS.models;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "principal")
public class Principal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private LocalDate dateOfBirth;
    private LocalDate enterDate;
    private String status;
    private String contact;


    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
