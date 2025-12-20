package School.LMS.repos;

import School.LMS.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
 

public interface SubjectRepo extends JpaRepository<Subject, Long> {
    boolean existsByName(String name);
    Optional<Subject> findByName(String name);
}
