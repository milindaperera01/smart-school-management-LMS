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
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private LocalDateTime dueDate;

    private String filePath; // optional: file path

    @ManyToOne
    private ClassRoom classRoom;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Teacher createdBy;

    private boolean isActive = true;

    // getters & setters
}

