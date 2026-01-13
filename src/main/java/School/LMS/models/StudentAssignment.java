package School.LMS.models;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Assignment assignment;

    private String submissionFile; // file path or URL

    private String answerText;

    private LocalDateTime submittedAt;

    private String status; // e.g., "Submitted", "Pending", "Graded"

    private String grade; // optional

    // getters & setters
}