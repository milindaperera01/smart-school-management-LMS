package School.LMS.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "grade_subject")
public class GradeSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int gradeLevel;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    
}
