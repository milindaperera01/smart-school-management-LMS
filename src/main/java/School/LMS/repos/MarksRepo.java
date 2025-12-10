package School.LMS.repos;

import School.LMS.models.Marks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarksRepo extends JpaRepository<Marks, Long> {
}
