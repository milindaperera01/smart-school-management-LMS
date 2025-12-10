package School.LMS.repos;

import School.LMS.models.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRoomRepo extends JpaRepository<ClassRoom, Long> {
}
