package School.LMS.models;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "classroom")
public class ClassRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String className;
    private int gradeLevel;

    @OneToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher; 


    @OneToMany(mappedBy = "classRoom") 
    private List<Student> students;

    @OneToMany(mappedBy = "classroom")
    private List<TeachingAssignment> teachingAssignments;
}
