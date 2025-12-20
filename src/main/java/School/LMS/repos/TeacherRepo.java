package School.LMS.repos;
import School.LMS.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface TeacherRepo extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUserUsername(String username);
    Optional<Teacher> findByUserId(Long userId);
    
}
