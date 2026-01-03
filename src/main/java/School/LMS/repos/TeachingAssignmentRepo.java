package School.LMS.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import School.LMS.models.*;

public interface TeachingAssignmentRepo extends JpaRepository<TeachingAssignment, Long> {
    List<TeachingAssignment> findByClassroom(ClassRoom classroom);
    List<TeachingAssignment> findByTeacher(Teacher teacher);
    boolean existsByClassroomAndSubject(ClassRoom classroom, Subject subject);
}